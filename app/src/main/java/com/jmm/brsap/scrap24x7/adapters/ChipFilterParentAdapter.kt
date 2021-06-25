package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager.VERTICAL
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateSectionedRvBinding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class ChipFilterParentAdapter(private val mListener: ChipFilterChildAdapter.ChipFilterChildInterface) :
    RecyclerView.Adapter<ChipFilterParentAdapter.ChipFilterParentViewHolder>() {


    private val mList = mutableListOf<ParentFilterModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipFilterParentViewHolder {
        return ChipFilterParentViewHolder(
            TemplateSectionedRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ChipFilterParentViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setParentFilterModelList(mList: List<ParentFilterModel>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class ChipFilterParentViewHolder(
        val binding: TemplateSectionedRvBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }


        }

        fun bind(item: ParentFilterModel) {
            binding.apply {
                templateSectionedRvTitle.text = item.heading
                templateSectionedRvRecyclerview.apply {
                    setHasFixedSize(true)
                    layoutManager = StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL)
                    adapter = ChipFilterChildAdapter(mListener,item.filterItems.toMutableList(),item.singleSelection)
                }
            }
        }
    }

//    interface ChipFilterParentInterface {
//
//    }
//

}