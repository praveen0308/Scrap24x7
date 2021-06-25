package com.jmm.brsap.scrap_24_x_7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplateSectionedRvBinding
import com.jmm.brsap.scrap24x7.model.network_models.Category

class ScrapCategoryAdapter(
    private val scrapCategoryItemAdapterListener: ScrapCategoryItemAdapter.ScrapCategoryItemAdapterListener?=null
):RecyclerView.Adapter<ScrapCategoryAdapter.ScrapCategoryViewHolder>() {

    private val categoryList = mutableListOf<Category>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapCategoryAdapter.ScrapCategoryViewHolder {
        val v = TemplateSectionedRvBinding.inflate(LayoutInflater.from(parent.context),parent,false)

        return ScrapCategoryViewHolder(v)
    }

    override fun onBindViewHolder(holder: ScrapCategoryViewHolder, position: Int) {
        holder.createCategory(categoryList[position])
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    fun setCategoryList(categoryList: List<Category>){
        this.categoryList.clear()
        this.categoryList.addAll(categoryList)
        notifyDataSetChanged()
    }

    inner class ScrapCategoryViewHolder(private val binding: TemplateSectionedRvBinding):RecyclerView.ViewHolder(binding.root) {

        fun createCategory(category: Category){
            binding.templateSectionedRvTitle.text = category.type
            binding.templateSectionedRvRecyclerview.apply {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(binding.root.context,LinearLayoutManager.HORIZONTAL,false)
                val mAdapter = category.categoryItemList?.let { ScrapCategoryItemAdapter(it) }
                adapter = mAdapter
                scrapCategoryItemAdapterListener?.let {
                    mAdapter?.setScrapCategoryItemAdapterListener(it)
                }
            }
        }

    }

}