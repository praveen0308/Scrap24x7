package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateSellItemBinding
import com.jmm.brsap.scrap24x7.model.network_models.Category


class SellCategoryItemAdapter(
    private val sellCategoryItemAdapterListener: SellCategoryItemAdapterListener
) :
    RecyclerView.Adapter<SellCategoryItemAdapter.SellCategoryItemViewHolder>() {

    private val categoryItemList= mutableListOf<Category>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SellCategoryItemAdapter.SellCategoryItemViewHolder {
        val v = TemplateSellItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SellCategoryItemViewHolder(v,sellCategoryItemAdapterListener)
    }

    override fun getItemCount(): Int {
        return categoryItemList.size
    }

    fun setCategoryList(categoryList: List<Category>){
        this.categoryItemList.clear()
        this.categoryItemList.addAll(categoryList)
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: SellCategoryItemAdapter.SellCategoryItemViewHolder,
        position: Int
    ) {
        holder.createCategoryItem(categoryItemList[position])
    }

    inner class SellCategoryItemViewHolder(
            private val binding: TemplateSellItemBinding,
            private val mListener: SellCategoryItemAdapterListener,

        ) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            itemView.setOnClickListener {
                categoryItemList[absoluteAdapterPosition].isSelected = !categoryItemList[absoluteAdapterPosition].isSelected
                mListener.onCategorySelected(categoryItemList)
                notifyDataSetChanged()
            }
        }

        fun createCategoryItem(categoryItem: Category) {

            binding.apply {
                tvScrapItemTitle.text = categoryItem.type
                val subTitle = StringBuilder()
                for (item in categoryItem.categoryItemList!!){
                    subTitle.append(item.item_name).append(",")
                }
                tvScrapItemSubTitle.text = subTitle
                categoryItem.drawableUrl?.let { ivScrapItem.setImageResource(it) }


                    if (categoryItem.isSelected) {
                        cardView.strokeColor = ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark)
                        cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.green_20))
                        ivIndicator.setColorFilter(ContextCompat.getColor(itemView.context, R.color.colorPrimary))

                    }else{
                        cardView.strokeColor = ContextCompat.getColor(itemView.context,R.color.material_on_surface_stroke)
                        cardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context,R.color.white))
                        ivIndicator.colorFilter = null
                    }

            }
        }
    }

    interface SellCategoryItemAdapterListener{
        fun onCategorySelected(categoryList:MutableList<Category>)
    }
}