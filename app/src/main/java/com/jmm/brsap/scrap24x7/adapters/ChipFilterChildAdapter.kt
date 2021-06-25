package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.MyFilterChipBinding
import com.jmm.brsap.scrap24x7.model.FilterModel

class ChipFilterChildAdapter(
    private val mListener: ChipFilterChildInterface,
    private val mList:MutableList<FilterModel>,
    private val singleSelection : Boolean
    ) :
    RecyclerView.Adapter<ChipFilterChildAdapter.ChipFilterChildViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChipFilterChildViewHolder {
        return ChipFilterChildViewHolder(
            MyFilterChipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ChipFilterChildViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setFilterModelList(mList: List<FilterModel>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class ChipFilterChildViewHolder(
        val binding: MyFilterChipBinding,
        private val mListener: ChipFilterChildInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                if (singleSelection){
                    for (item in mList){
                        item.isSelected = false
                    }
                    mList[absoluteAdapterPosition].isSelected = true
                    notifyDataSetChanged()
                }
                else{
                    mList[absoluteAdapterPosition].isSelected = true
                    notifyDataSetChanged()
                }
                mListener.onItemClick(mList)
            }
        }

        fun bind(item: FilterModel) {
            binding.apply {
                root.text = item.title
                root.isChecked = item.isSelected
            }
        }
    }

    interface ChipFilterChildInterface {
//        fun onItemClick(item: FilterModel)
        fun onItemClick(itemList: List<FilterModel>)

    }


}