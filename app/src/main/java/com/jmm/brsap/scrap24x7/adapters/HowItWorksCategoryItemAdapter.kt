package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplateHowItWorksItemBinding
import com.jmm.brsap.scrap24x7.model.ModelHowItWorksCategoryItem

class HowItWorksCategoryItemAdapter (private val categoryItemList: MutableList<ModelHowItWorksCategoryItem>) : RecyclerView.Adapter<HowItWorksCategoryItemAdapter.HowItWorksCategoryItem>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HowItWorksCategoryItem {
        val v = TemplateHowItWorksItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return HowItWorksCategoryItem(v)
    }

    override fun onBindViewHolder(holder: HowItWorksCategoryItem, position: Int){
        holder.createCategoryItem(categoryItemList[position])
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    inner class HowItWorksCategoryItem(private val binding: TemplateHowItWorksItemBinding):RecyclerView.ViewHolder(binding.root){

        fun createCategoryItem(categoryItem : ModelHowItWorksCategoryItem){
            binding.apply {
                tvWorksNumber.text = categoryItem.number
                tvWorksTitle.text = categoryItem.title
                tvWorksSubtitle.text = categoryItem.subTitle
                ivWorks.setImageResource(categoryItem.imageUrl)
            }

        }
    }

}