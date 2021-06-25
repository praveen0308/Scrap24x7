package com.jmm.brsap.scrap24x7.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplateScrapImpactItemBinding
import com.jmm.brsap.scrap24x7.model.ModelScrapImpact

class ScrapImpactAdapter(private val categoryItemList:MutableList<ModelScrapImpact>):RecyclerView.Adapter<ScrapImpactAdapter.ScrapImpactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapImpactAdapter.ScrapImpactViewHolder {

        return ScrapImpactViewHolder(TemplateScrapImpactItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    override fun onBindViewHolder(holder: ScrapImpactAdapter.ScrapImpactViewHolder, position: Int) {
        holder.createCategoryItem(categoryItemList[position])
    }

    inner class ScrapImpactViewHolder(val binding: TemplateScrapImpactItemBinding):RecyclerView.ViewHolder(binding.root){

       fun createCategoryItem(categoryItem: ModelScrapImpact){

           binding.apply {

               tvScrapImpactTitle.text = categoryItem.title
               tvScrapImpactSubtitle.text = categoryItem.subTitle
               ivScrapImpact.setImageResource(categoryItem.imageUrl)

               tvScrapImpactTitle.setTextColor(ContextCompat.getColor(root.context,categoryItem.color))
               tvScrapImpactSubtitle.setTextColor(ContextCompat.getColor(root.context,categoryItem.color))
               ivScrapImpact.setColorFilter(ContextCompat.getColor(root.context,categoryItem.color))
//               val colorStr = root.context.resources.getString(categoryItem.color)
//               root.setCardBackgroundColor(Color.parseColor(colorStr))
               clContainer.setBackgroundColor(ContextCompat.getColor(root.context,categoryItem.color))
               clContainer.background.alpha=51



           }
       }

    }
}