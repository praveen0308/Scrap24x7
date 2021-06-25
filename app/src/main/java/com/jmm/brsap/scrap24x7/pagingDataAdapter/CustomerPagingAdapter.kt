package com.jmm.brsap.scrap24x7.pagingDataAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.Customer

class CustomerPagingAdapter(private val mListener: ManageUserInterface) :
    PagingDataAdapter<Customer, CustomerPagingAdapter.CustomerViewHolder>(CustomerComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CustomerViewHolder {
        return CustomerViewHolder(
            TemplateMasterRvActionRowType1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),mListener
        )
    }


    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindCustomer(it) }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class CustomerViewHolder(
        private val binding: TemplateMasterRvActionRowType1Binding,
        private val mListener: ManageUserInterface,
    ) :
        RecyclerView.ViewHolder(binding.root) {
        init {

            binding.frameData.setOnClickListener {
                mListener.onItemClick(getItem(absoluteAdapterPosition)!!)
            }
            binding.btnAction1.setOnClickListener {
                mListener.onBlockClick(getItem(absoluteAdapterPosition)!!)
            }
            binding.btnAction3.setOnClickListener {
                mListener.onCallClick(getItem(absoluteAdapterPosition)!!)
            }
        }
        fun bindCustomer(item: Customer) = with(binding) {
            btnAction3.visibility = View.VISIBLE
            btnAction3.setIcon(R.drawable.ic_round_phone_24)
            btnAction3.setTitle("Call")

            tvMainTitle.text = "${item.first_name} ${item.last_name}"
            tvSymbol.text = item.first_name.take(1)
            tvSubTitle.text = "ID: ${item.customer_id}"
            btnAction1.visibility = View.VISIBLE
            btnAction1.setIcon(R.drawable.ic_baseline_block_24)
            btnAction1.setTitle("Block")


        }
    }

    companion object {
        private val CustomerComparator = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem.customer_id == newItem.customer_id
            }

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean {
                return oldItem == newItem
            }
        }
    }
}