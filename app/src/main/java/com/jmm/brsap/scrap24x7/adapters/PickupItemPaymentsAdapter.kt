package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplatePickupSummaryItemsBinding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class PickupItemPaymentsAdapter(private val mListener: PickupItemPaymentsInterface) :
    RecyclerView.Adapter<PickupItemPaymentsAdapter.PickupItemPaymentsViewHolder>() {


    private val mList = mutableListOf<PickupRequestItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PickupItemPaymentsViewHolder {
        return PickupItemPaymentsViewHolder(
            TemplatePickupSummaryItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: PickupItemPaymentsViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setPickupRequestItemList(mList: List<PickupRequestItem>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class PickupItemPaymentsViewHolder(
        val binding: TemplatePickupSummaryItemsBinding,
        private val mListener: PickupItemPaymentsInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }


        }

        fun bind(item: PickupRequestItem) {
            binding.apply {
                tvItemName.text = item.category_item!!.item_name
                tvItemRate.text = "₹${item.price}/${item.category_item.unit_name}"
                tvItemQuantity.text = "x${item.quantity}"
                tvItemAmount.text = "₹${item.total_price}"
            }
        }
    }

    interface PickupItemPaymentsInterface {

    }


}