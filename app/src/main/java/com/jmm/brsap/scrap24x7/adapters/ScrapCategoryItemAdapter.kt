package com.jmm.brsap.scrap_24_x_7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateScrapItemPricePerUnitBinding
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem

class ScrapCategoryItemAdapter(
    private val categoryItemPriceList: MutableList<CategoryItem>

) : RecyclerView.Adapter<ScrapCategoryItemAdapter.ScrapCategoryItemPriceViewHolder>() {

    private var scrapCategoryItemAdapterListener: ScrapCategoryItemAdapterListener ?=null
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScrapCategoryItemAdapter.ScrapCategoryItemPriceViewHolder {
        val v = TemplateScrapItemPricePerUnitBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return ScrapCategoryItemPriceViewHolder(v, scrapCategoryItemAdapterListener)
    }

    override fun onBindViewHolder(
        holder: ScrapCategoryItemAdapter.ScrapCategoryItemPriceViewHolder,
        position: Int
    ) {
        holder.createCategoryItemPrice(categoryItemPriceList[position])
    }

    override fun getItemCount(): Int {
        return categoryItemPriceList.size
    }

    fun setScrapCategoryItemAdapterListener(scrapCategoryItemAdapterListener: ScrapCategoryItemAdapterListener) {
        this.scrapCategoryItemAdapterListener = scrapCategoryItemAdapterListener
    }

    inner class ScrapCategoryItemPriceViewHolder(
        private val binding: TemplateScrapItemPricePerUnitBinding,
        private val mListener: ScrapCategoryItemAdapterListener?

    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            if (mListener != null) {
                itemView.setOnClickListener {
                    val item = categoryItemPriceList[absoluteAdapterPosition]

                    if (item.isSelected) {
                        item.isSelected = false
                        mListener.onDeselected(item)
                    } else {
                        item.isSelected = true
                        mListener.onSelected(item)
                    }
                    notifyDataSetChanged()
                }
            }
            setIsRecyclable(false)
        }

        fun createCategoryItemPrice(item: CategoryItem) {
            binding.apply {
                tvCategoryItemTitle.text = item.item_name
                tvCategoryItemSubtitle.text = "₹${item.unit_price!!.toInt()}/${item.unit_name}"

                if (item.isSelected) {
                    root.strokeColor =
                        ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark)
                    root.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.green_20
                        )
                    )


                } else {
                    root.strokeColor =
                        ContextCompat.getColor(itemView.context, R.color.material_on_surface_stroke)
                    root.setCardBackgroundColor(
                        ContextCompat.getColor(
                            itemView.context,
                            R.color.white
                        )
                    )

                }
            }

        }
    }

    interface ScrapCategoryItemAdapterListener {
        fun onSelected(item: CategoryItem)
        fun onDeselected(item: CategoryItem)
    }
}