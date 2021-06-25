package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.*

class SingleTitleRowAdapter(private val singleTitleRowInterface: SingleTitleRowInterface) :
    RecyclerView.Adapter<SingleTitleRowAdapter.SingleTitleRowViewHolder>() {


    private val mList = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SingleTitleRowViewHolder {
        return SingleTitleRowViewHolder(
            TemplateMasterRvActionRowType1Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), singleTitleRowInterface
        )
    }

    override fun onBindViewHolder(holder: SingleTitleRowViewHolder, position: Int) {
        holder.createItem(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setItemList(list: List<Any>) {
        mList.clear()
        mList.addAll(list)
        notifyDataSetChanged()
    }

    inner class SingleTitleRowViewHolder(
        val binding: TemplateMasterRvActionRowType1Binding,
        private val mListener: SingleTitleRowInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.frameData.setOnClickListener {
                mListener.onItemClick(mList[absoluteAdapterPosition])
            }

//            binding.ivOption.setOnClickListener {
//                val popup = PopupMenu(itemView.context, binding.ivOption)
//                popup.inflate(R.menu.menu_crud)
//                popup.setOnMenuItemClickListener { item ->
//                    when (item.itemId) {
//                        R.id.edit_operation -> {
//                            mListener.onEditClick(itemList[absoluteAdapterPosition])
//                            true
//                        }
//                        R.id.delete_operation -> {
//                            mListener.onDeleteClick(itemList[absoluteAdapterPosition])
//                            true
//                        }
//
//                        else -> false
//                    }
//                }
//
//                popup.show()
//            }

            binding.btnAction2.setOnClickListener {
                mListener.onEditClick(mList[absoluteAdapterPosition])
                binding.swipeRevealLayout.close(true)
            }


        }

        fun createItem(item: Any) {
            binding.apply {
                tvSubTitle.visibility = View.GONE
                btnAction2.visibility = View.VISIBLE
                btnAction2.setIcon(R.drawable.ic_baseline_edit_24)
                btnAction2.setTitle("Edit")

                when(item){
                    is Warehouse->{
                        tvMainTitle.text = item.warehouse_name
                        tvSymbol.text = item.warehouse_name?.take(1)

                    }
                    is LocationMaster->{
                        tvMainTitle.text = item.location_name
                        tvSymbol.text = item.location_name?.take(1)
                    }
                    is Category->{
                        btnAction2.isVisible=false
                        tvMainTitle.text = item.type
                        tvSymbol.text = item.type?.take(1) ?: "A"
                    }
                    is CategoryItem->{
                        tvMainTitle.text = item.item_name
                        tvSymbol.text = item.item_name!!.take(1)
                    }

                    is UnitModel->{
                        tvMainTitle.text = item.unit_name
                        tvSymbol.text = item.unit_name?.take(1)
                    }

                }
            }


        }
    }

    interface SingleTitleRowInterface {
        fun onItemClick(item: Any)
        fun onEditClick(item: Any)
        fun onDeleteClick(item: Any)
    }


}