package com.jmm.brsap.scrap24x7.pagingDataAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.DriverMaster

class DriverPagingAdapter(
    private val mListener: ManageUserInterface,
) :
    PagingDataAdapter<DriverMaster, DriverPagingAdapter.DriverViewHolder>(DriverComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DriverViewHolder {
        return DriverViewHolder(
            TemplateMasterRvActionRowType1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),mListener
        )
    }


    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindDriver(it) }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class DriverViewHolder(
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
        fun bindDriver(item: DriverMaster) = with(binding) {
            btnAction3.visibility = View.VISIBLE
            btnAction3.setIcon(R.drawable.ic_round_phone_24)
            btnAction3.setTitle("Call")

            tvMainTitle.text = "${item.driver_name}"
            tvSymbol.text = item.driver_name!!.take(1)
            tvSubTitle.text = "ID: ${item.id}"
            btnAction1.visibility = View.VISIBLE
            btnAction1.setIcon(R.drawable.ic_baseline_block_24)
            btnAction1.setTitle("Block")


        }
    }

    companion object {
        private val DriverComparator = object : DiffUtil.ItemCallback<DriverMaster>() {
            override fun areItemsTheSame(oldItem: DriverMaster, newItem: DriverMaster): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: DriverMaster, newItem: DriverMaster): Boolean {
                return oldItem == newItem
            }
        }
    }
}