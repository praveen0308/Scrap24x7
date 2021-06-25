package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType2Binding
import com.jmm.brsap.scrap24x7.model.network_models.CategoryItem

class ChooseScrapItemAdapter(private val mListener: ScrapItemInterface) :
    RecyclerView.Adapter<ChooseScrapItemAdapter.ScrapItemViewHolder>() {


    private val mList = mutableListOf<CategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScrapItemViewHolder {
        return ScrapItemViewHolder(
            TemplateMasterRvActionRowType2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ScrapItemViewHolder, position: Int) {
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

    inner class ScrapItemViewHolder(
        val binding: TemplateMasterRvActionRowType2Binding,
        private val mListener: ScrapItemInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.frameData.setOnClickListener {

                mList[absoluteAdapterPosition].isSelected = !mList[absoluteAdapterPosition].isSelected
                mListener.onItemClick(mList[absoluteAdapterPosition])
                notifyDataSetChanged()
            }

        }

        fun bind(item: CategoryItem) {
            binding.apply {
                tvMainTitle.text = item.item_name

                if (item.isSelected) {
                    cvIcon.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.colorPrimary
                        )
                    )
                    ivIcon.setImageResource(R.drawable.ic_round_check_24)
                    ivIcon.setColorFilter(ContextCompat.getColor(root.context, R.color.white))
                } else {
                    cvIcon.setCardBackgroundColor(
                        ContextCompat.getColor(
                            root.context,
                            R.color.green_20
                        )
                    )
                    ivIcon.setImageResource(R.drawable.ic_select_plastic)
                    ivIcon.setColorFilter(ContextCompat.getColor(root.context, R.color.colorPrimary))
                }
            }
        }
    }

    interface ScrapItemInterface {
        fun onItemClick(item: CategoryItem)
    }


}