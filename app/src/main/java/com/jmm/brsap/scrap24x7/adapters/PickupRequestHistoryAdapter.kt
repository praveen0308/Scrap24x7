package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplatePickupRequestType1Binding
import com.jmm.brsap.scrap24x7.databinding.TemplatePickupRequestType2Binding
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.convertMillisecondsToDate
import com.jmm.brsap.scrap24x7.util.convertYMD2MDY

class PickupRequestHistoryAdapter(
            val type: AdminEnum,
            private val mListener: PickupRequestHistoryInterface
        ) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val itemList = mutableListOf<PickupRequest>()
    override fun onCreateViewHolder(
            parent: ViewGroup,
            viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            1 -> CustomerPickupHistoryViewHolder(
                    TemplatePickupRequestType2Binding.inflate(LayoutInflater.from(parent.context), parent, false),mListener)
            2 -> ExecutivePickupHistoryViewHolder(
                    TemplatePickupRequestType1Binding.inflate(LayoutInflater.from(parent.context), parent, false),mListener)
            else -> CustomerPickupHistoryViewHolder(
                    TemplatePickupRequestType2Binding.inflate(LayoutInflater.from(parent.context), parent, false),mListener)

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as CustomerPickupHistoryViewHolder).createItem(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return when (type) {
            AdminEnum.CUSTOMER -> 1
            AdminEnum.EXECUTIVE -> 2
            else -> 0
        }
    }

    fun setItemList(mList: List<PickupRequest>) {
        this.itemList.clear()
        this.itemList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class CustomerPickupHistoryViewHolder(
            val binding: TemplatePickupRequestType2Binding,
            private val mListener: PickupRequestHistoryInterface

    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            itemView.setOnClickListener {
                mListener.onItemClick(itemList[absoluteAdapterPosition])
            }
        }

        fun createItem(item: PickupRequest) {
            binding.apply {
                if (item.customer_address?.locality != null) {
                    tvPickupAddress.text = item.customer_address.locality.toString()
                } else {
                    tvPickupAddress.text = item.customer_address?.city.toString()
                }

                tvPickupId.text = item.pickup_id.toString()
                tvPickupStatus.text = item.status_name.toString()
                tvPickupDate.text = convertYMD2MDY(item.pickup_requested_date.toString())
            }
        }
    }

    inner class ExecutivePickupHistoryViewHolder(
            val binding: TemplatePickupRequestType1Binding,
            private val mListener: PickupRequestHistoryInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            itemView.setOnClickListener {
                mListener.onItemClick(itemList[absoluteAdapterPosition])
            }
        }

        fun createItem(item: PickupRequest) {
            binding.apply {
                if (item.customer_address?.locality != null) {
                    tvPickupAddress.text = item.customer_address.locality.toString()
                } else {
                    tvPickupAddress.text = item.customer_address?.city.toString()
                }

                tvPickupId.text = item.pickup_id.toString()

            }
        }
    }

    interface PickupRequestHistoryInterface {
        fun onItemClick(pickupRequest: PickupRequest)
    }
}