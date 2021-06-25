package com.jmm.brsap.scrap24x7.adapters

import android.R
import android.R.attr.data
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.ListPopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplateCustomerPaymentMethodBinding
import com.jmm.brsap.scrap24x7.model.ModelCustomerPaymentMethod
import com.jmm.brsap.scrap24x7.util.MenuEnum


class CustomerPaymentMethodAdapter()
    : RecyclerView.Adapter<CustomerPaymentMethodAdapter.CustomerPaymentMethodViewHolder>() {

    private lateinit var mContext:Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerPaymentMethodViewHolder {
        mContext = parent.context
        val v = TemplateCustomerPaymentMethodBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerPaymentMethodViewHolder(v)
    }

    private val itemList = mutableListOf<ModelCustomerPaymentMethod>()
    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: CustomerPaymentMethodViewHolder, position: Int) {
        holder.createCategoryItem(itemList[position])

    }

    fun setItemList(mList: List<ModelCustomerPaymentMethod>) {
        itemList.clear()
        itemList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class CustomerPaymentMethodViewHolder(private val binding: TemplateCustomerPaymentMethodBinding) : RecyclerView.ViewHolder(binding.root) {
        init {
//            val window = ListPopupWindow(mContext)
//            binding.ivOption.setOnClickListener {
//                val item =itemList[absoluteAdapterPosition]
//                val data  = arrayListOf<String>()
//                when(item.id){
//                    MenuEnum.PAYTM->{
//                        data.add("Set as default")
//                        if (item.subTitle!=null){
//                            data.add("Change")
//                        }else {
//
//                            data.add("Link")
//                        }
//                    }
//                    MenuEnum.CASH->{
//                        data.add("Set as default")
//                    }
//                }
//
//
//                val adapter: ArrayAdapter<String> = ArrayAdapter<String>(mContext,
//                        R.layout.simple_dropdown_item_1line, data)
//                window.setAdapter(adapter)
//
//                window.show()
//
//                window.setOnItemClickListener { parent, view, position, id ->
//
//                }
//            }
        }

        fun createCategoryItem(categoryItem: ModelCustomerPaymentMethod) {
            binding.apply {
                tvPaymentTitle.text = categoryItem.title

                if (categoryItem.subTitle != null) {
                    tvPaymentSubTitle.visibility = View.VISIBLE
                    tvPaymentSubTitle.text = categoryItem.subTitle
                } else {
                    tvPaymentSubTitle.visibility = View.GONE
                }

                ivPayment.setImageResource(categoryItem.imageUrl)

                if (categoryItem.havingDivider) {
                    lastDivider.root.visibility = View.VISIBLE
                } else {
                    lastDivider.root.visibility = View.GONE
                }
            }
        }

    }
}