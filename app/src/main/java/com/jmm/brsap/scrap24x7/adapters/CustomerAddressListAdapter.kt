package com.jmm.brsap.scrap24x7.adapters

import android.os.Build
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateCustomerAddressItemBinding
import com.jmm.brsap.scrap24x7.databinding.TemplateCustomerAddressType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.CustomerAddress
import com.jmm.brsap.scrap24x7.util.OtherEnum


class CustomerAddressListAdapter(val type: OtherEnum) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val addressList = mutableListOf<CustomerAddress>()
    private var customerAddressListInterface: CustomerAddressListInterface? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            1 -> return CustomerAddressListViewHolder(
                TemplateCustomerAddressItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), customerAddressListInterface
            )
            2 -> return CustomerManageAddressViewHolder(
                TemplateCustomerAddressType1Binding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), customerAddressListInterface
            )
            else -> return CustomerAddressListViewHolder(
                TemplateCustomerAddressItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                ), customerAddressListInterface
            )
        }

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (type) {
            OtherEnum.NON_EDITABLE -> (holder as CustomerAddressListViewHolder).createItem(
                addressList[position]
            )
            OtherEnum.EDITABLE -> (holder as CustomerManageAddressViewHolder).createItem(
                addressList[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return addressList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            OtherEnum.NON_EDITABLE -> 1
            OtherEnum.EDITABLE -> 2
            else -> 3
        }
    }

    fun setListener(customerAddressListInterface: CustomerAddressListInterface) {
        this.customerAddressListInterface = customerAddressListInterface
    }

    fun setAddressList(addressList: List<CustomerAddress>) {
        this.addressList.clear()
        this.addressList.addAll(addressList)
        notifyDataSetChanged()
    }

    inner class CustomerAddressListViewHolder(
        private val binding: TemplateCustomerAddressItemBinding,
        private val mListener: CustomerAddressListInterface?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            if (mListener != null) {
                itemView.setOnClickListener {
                    for (i in 0 until addressList.size) {
                        addressList[i].isSelected = i == absoluteAdapterPosition
                    }
                    mListener.onAddressClick(addressList[absoluteAdapterPosition])
//                categoryItemList[adapterPosition].isSelected = !categoryItemList[adapterPosition].isSelected
                    notifyDataSetChanged()
                }
            }
        }

        fun createItem(address: CustomerAddress) {
            binding.apply {
                tvTitle.text = address.address_type
                tvText.text = address.address1.toString()

                if (address.isSelected) {
                    root.strokeColor =
                        ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark)
                    root.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.green_20
                        )
                    )


                } else {
                    root.strokeColor =
                        ContextCompat.getColor(itemView.context, R.color.material_on_surface_stroke)
                    root.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )

                }
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.M)
    inner class CustomerManageAddressViewHolder(
        private val binding: TemplateCustomerAddressType1Binding,
        private val mListener: CustomerAddressListInterface?
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            if (mListener != null) {

                binding.ivOption.setOnClickListener {
                    //creating a popup menu
                    //creating a popup menu
                    val popup = PopupMenu(itemView.context, binding.ivOption)
                    popup.inflate(R.menu.menu_crud)
                    popup.gravity = Gravity.END;
                    popup.setOnMenuItemClickListener { item ->
                        when (item.itemId) {
                            R.id.delete_operation -> {
                                mListener.onAddressDelete(addressList[absoluteAdapterPosition])
                                true
                            }                       //handle menu1 click

                            else -> false
                        }
                    }
                    //displaying the popup
                    //displaying the popup
                    popup.show()
                }

            }
        }

        fun createItem(address: CustomerAddress) {
            binding.apply {
                tvTitle.text = address.address_type
                tvText.text = address.address1.toString()

            }
        }
    }

    interface CustomerAddressListInterface {
        fun onAddressClick(customerAddress: CustomerAddress)
        fun onAddressDelete(customerAddress: CustomerAddress)
    }


}