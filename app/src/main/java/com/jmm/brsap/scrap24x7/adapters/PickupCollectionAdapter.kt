package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.core.widget.doOnTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplatePickupCollectionRowBinding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class PickupCollectionAdapter(private val mListener: PickupCollectionInterface) :
    RecyclerView.Adapter<PickupCollectionAdapter.PickupCollectionViewHolder>() {


    private val mList = mutableListOf<CategoryItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickupCollectionViewHolder {
        return PickupCollectionViewHolder(
            TemplatePickupCollectionRowBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: PickupCollectionViewHolder, position: Int) {
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

    inner class PickupCollectionViewHolder(
        val binding: TemplatePickupCollectionRowBinding,
        private val mListener: PickupCollectionInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }

            binding.etScrapQuantity.doOnTextChanged { text, start, before, count ->
                mListener.onQuantityChange(mList[absoluteAdapterPosition].id!!,text.toString().toDouble())
            }

        }

        fun bind(item: CategoryItem) {
            binding.apply {
                tvScrapName.text = item.item_name
//                tvUnitName.text = item.unit_name
                tilScarpQuantity.suffixText = item.unit_name
            }
        }
    }

    interface PickupCollectionInterface {
        fun onQuantityChange(itemId:Int,quantity:Double)
    }


}