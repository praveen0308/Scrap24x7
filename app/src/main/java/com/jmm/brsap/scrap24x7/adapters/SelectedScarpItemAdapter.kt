package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplateVrScrapCategoryItemViewBinding
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem

class SelectedScarpItemAdapter:RecyclerView.Adapter<SelectedScarpItemAdapter.SelectedScarpItemViewHolder>() {

    private val itemList = mutableListOf<CategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectedScarpItemViewHolder {
        return SelectedScarpItemViewHolder(TemplateVrScrapCategoryItemViewBinding.inflate(
            LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: SelectedScarpItemViewHolder, position: Int) {
        holder.createItem(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(mList:List<CategoryItem>){
        itemList.clear()
        itemList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class SelectedScarpItemViewHolder(val binding: TemplateVrScrapCategoryItemViewBinding):RecyclerView.ViewHolder(binding.root){

        fun createItem(item:CategoryItem){

            binding.apply {
                tvItemName.text = item.item_name
                tvItemRate.text = "₹${item.unit_price}/${item.unit_name}"

            }
        }
    }


}