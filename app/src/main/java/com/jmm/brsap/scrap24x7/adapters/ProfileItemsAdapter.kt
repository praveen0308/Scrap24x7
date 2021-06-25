package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.databinding.TemplateProfileItemBinding
import com.jmm.brsap.scrap24x7.model.ModelProfileItem

class ProfileItemsAdapter():RecyclerView.Adapter<ProfileItemsAdapter.ProfileItemsViewHolder>() {

    private val itemList = mutableListOf<ModelProfileItem>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileItemsAdapter.ProfileItemsViewHolder{
        val v = TemplateProfileItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ProfileItemsViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProfileItemsAdapter.ProfileItemsViewHolder, position: Int) {
        holder.createCategoryItem(itemList[position])
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    fun  setItemList(mList:List<ModelProfileItem>){
        itemList.clear()
        itemList.addAll(mList)
        notifyDataSetChanged()

    }
    inner class ProfileItemsViewHolder(private val binding: TemplateProfileItemBinding):RecyclerView.ViewHolder(binding.root) {

        fun createCategoryItem(item:ModelProfileItem){
            binding.apply {
                tvProfileItemTitle.text = item.title
                tvProfileItemDetail.text = item.subTitle
                ivIcon.setImageResource(item.imageUrl)
            }

        }

    }
}