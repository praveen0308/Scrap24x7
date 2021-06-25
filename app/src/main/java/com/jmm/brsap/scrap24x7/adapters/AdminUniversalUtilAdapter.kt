package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateAdminMenuItemBinding
import com.jmm.brsap.scrap24x7.databinding.TemplateDashoboardItemType1Binding
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType2Binding
import com.jmm.brsap.scrap24x7.model.ModelAdminUniversalUtil
import com.jmm.brsap.scrap24x7.util.AdminEnum
import com.jmm.brsap.scrap24x7.util.animateTextView

class AdminUniversalUtilAdapter(
    private val adminUniversalUtilInterface:AdminUniversalUtilInterface
):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

private val itemList = mutableListOf<ModelAdminUniversalUtil>()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when(viewType){
            1->AdminNavViewHolder(
                TemplateAdminMenuItemBinding.inflate(
                    LayoutInflater.from(parent.context),parent,false),
                adminUniversalUtilInterface)
            2->AdminManageCategoriesViewHolder(
                TemplateMasterRvActionRowType2Binding.inflate(
                    LayoutInflater.from(parent.context),parent,false),
                adminUniversalUtilInterface)

            else->AdminStatisticsViewHolder(
                TemplateDashoboardItemType1Binding.inflate(
                    LayoutInflater.from(parent.context),parent,false),
                adminUniversalUtilInterface)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            1->(holder as AdminNavViewHolder).createItem(itemList[position])
            2->(holder as AdminManageCategoriesViewHolder).createItem(itemList[position])
            else->(holder as AdminStatisticsViewHolder).createItem(itemList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {

        return when(itemList[position].type){
            AdminEnum.NAV->1
            AdminEnum.MANAGE_CATEGORIES->2
            else->3

        }

    }
    override fun getItemCount(): Int {
        return itemList.size
    }

    fun setItemList(mList:List<ModelAdminUniversalUtil>){
        itemList.clear()
        itemList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class AdminStatisticsViewHolder(
        val binding:TemplateDashoboardItemType1Binding,
        private val mListener:AdminUniversalUtilInterface
        )
        :RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
             mListener.onItemClick(itemList[absoluteAdapterPosition])
            }

        }

        fun createItem(item: ModelAdminUniversalUtil){
            binding.apply {
                tvHeading.text = item.heading
                animateTextView(
                    0,
                    item.data.toInt(),
                    tvData
                )
//                tvData.text = item.data
                ivIcon.setImageResource(item.imageUrl)
            }
        }
    }


    inner class AdminManageCategoriesViewHolder(
        val binding:TemplateMasterRvActionRowType2Binding,
        private val mListener:AdminUniversalUtilInterface
    )
        :RecyclerView.ViewHolder(binding.root){
        init {
            binding.frameData.setOnClickListener {
                mListener.onItemClick(itemList[absoluteAdapterPosition])
            }

            binding.btnAction3.setOnClickListener {
                mListener.onAddClick(itemList[absoluteAdapterPosition])
                binding.root.close(true)
            }
            binding.root.addOnLayoutChangeListener { v, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom ->
                if (left>0){
                    binding.tvSecondaryTitle.text = "left swiped : $left"
                }
            }
        }

        fun createItem(item: ModelAdminUniversalUtil){
            binding.apply {
                tvMainTitle.text = item.heading
                tvSubTitle.visibility =View.GONE
                ivIcon.setImageResource(item.imageUrl)

                if (item.id!=AdminEnum.CUSTOMER){
                    btnAction3.visibility = View.VISIBLE
                    btnAction3.setTitle(root.context.getString(R.string.add))
                    btnAction3.setIcon(R.drawable.ic_round_add_24)
                }

            }
        }
    }



    inner class AdminNavViewHolder(
        val binding:TemplateAdminMenuItemBinding,
        private val mListener:AdminUniversalUtilInterface
    )
        :RecyclerView.ViewHolder(binding.root){
        init {
            itemView.setOnClickListener {
                mListener.onItemClick(itemList[absoluteAdapterPosition])
            }

        }

        fun createItem(item: ModelAdminUniversalUtil){
            binding.apply {
                tvTitle.text = item.heading
                ivIcon.setImageResource(item.imageUrl)
            }
        }
    }

    interface AdminUniversalUtilInterface{
        fun onItemClick(item:ModelAdminUniversalUtil)
        fun onAddClick(item:ModelAdminUniversalUtil)
    }
}