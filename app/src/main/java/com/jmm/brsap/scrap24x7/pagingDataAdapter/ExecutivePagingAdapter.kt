package com.jmm.brsap.scrap24x7.pagingDataAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.ExecutiveMaster

class ExecutivePagingAdapter(private val mListener: ManageUserInterface) :
    PagingDataAdapter<ExecutiveMaster, ExecutivePagingAdapter.ExecutiveMasterViewHolder>(ExecutiveMasterComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ExecutiveMasterViewHolder {
        return ExecutiveMasterViewHolder(
            TemplateMasterRvActionRowType1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),mListener
        )
    }


    override fun onBindViewHolder(holder: ExecutiveMasterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bindExecutiveMaster(it) }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }
    inner class ExecutiveMasterViewHolder(
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
        fun bindExecutiveMaster(item: ExecutiveMaster) = with(binding) {
            btnAction3.visibility = View.VISIBLE
            btnAction3.setIcon(R.drawable.ic_round_phone_24)
            btnAction3.setTitle("Call")

            tvMainTitle.text = "${item.name}"
            tvSymbol.text = item.name!!.take(1)
            tvSubTitle.text = "ID: ${item.id}"
            btnAction1.visibility = View.VISIBLE
            btnAction1.setIcon(R.drawable.ic_baseline_block_24)
            btnAction1.setTitle("Block")


        }
    }

    companion object {
        private val ExecutiveMasterComparator = object : DiffUtil.ItemCallback<ExecutiveMaster>() {
            override fun areItemsTheSame(oldItem: ExecutiveMaster, newItem: ExecutiveMaster): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ExecutiveMaster, newItem: ExecutiveMaster): Boolean {
                return oldItem == newItem
            }
        }
    }


}