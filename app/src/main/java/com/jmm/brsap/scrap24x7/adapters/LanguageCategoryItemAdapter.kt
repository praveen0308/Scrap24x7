package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateLanguageItemBinding
import com.jmm.brsap.scrap24x7.model.ModelLanguageCategoryItem

class LanguageCategoryItemAdapter(private val categoryItemList :MutableList<ModelLanguageCategoryItem>):RecyclerView.Adapter<LanguageCategoryItemAdapter.LanguageCategoryItemViewHolder>() {
    


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LanguageCategoryItemAdapter.LanguageCategoryItemViewHolder {
        val v = TemplateLanguageItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return LanguageCategoryItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    override fun onBindViewHolder(holder: LanguageCategoryItemAdapter.LanguageCategoryItemViewHolder, position: Int) {
        holder.createCategoryItem(categoryItemList[position])
    }

    inner class LanguageCategoryItemViewHolder(private val binding:TemplateLanguageItemBinding):RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                for (i in 0 until categoryItemList.size){
                    categoryItemList[i].isSelected = i==absoluteAdapterPosition
                }
//                categoryItemList[adapterPosition].isSelected = !categoryItemList[adapterPosition].isSelected
                notifyDataSetChanged()
            }

        }
        fun createCategoryItem(categoryItem: ModelLanguageCategoryItem){
            binding.apply {
                tvLanguageTitle.text = categoryItem.title
                tvLanguageDetail.text = categoryItem.subTitle


                if (categoryItem.isSelected) {
                    ivIndicator.setColorFilter(ContextCompat.getColor(itemView.context, R.color.colorPrimary))

                }else{

                    ivIndicator.setColorFilter(null)
                }

                }
            }
        }

    }