package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateSelectedScrapItemsBinding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class SelectedScrapItemsAdapter(private val mListener: SelectedScrapItemsInterface) :
    RecyclerView.Adapter<SelectedScrapItemsAdapter.SelectedScrapItemsViewHolder>() {


    private val mList = mutableListOf<CategoryItem>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectedScrapItemsViewHolder {
        return SelectedScrapItemsViewHolder(
            TemplateSelectedScrapItemsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: SelectedScrapItemsViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCategoryItemList(mList: List<CategoryItem>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class SelectedScrapItemsViewHolder(
        val binding: TemplateSelectedScrapItemsBinding,
        private val mListener: SelectedScrapItemsInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }


        }

        fun bind(item: CategoryItem) {
            binding.apply {
                tvText.text = item.item_name
            }
        }
    }

    interface SelectedScrapItemsInterface {

    }


}