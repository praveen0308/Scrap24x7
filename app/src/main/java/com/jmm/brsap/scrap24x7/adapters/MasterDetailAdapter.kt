package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateDetailHrBinding
import com.jmm.brsap.scrap24x7.databinding.TemplateDetailVrBinding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*
import com.jmm.brsap.scrap24x7.util.OtherEnum

class MasterDetailAdapter(private val mListener: MasterDetailInterface) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val mList = mutableListOf<HeadingValueModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VR_TYPE -> MasterDetailVRViewHolder(
                TemplateDetailVrBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), mListener
            )
            HR_TYPE -> MasterDetailHRViewHolder(
                TemplateDetailHrBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), mListener
            )

            else -> MasterDetailVRViewHolder(
                TemplateDetailVrBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), mListener
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (mList[position].type) {
            OtherEnum.VERTICAL -> VR_TYPE
            OtherEnum.HORIZONTAL -> HR_TYPE
            else -> VR_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(getItemViewType(position)){
            VR_TYPE->(holder as MasterDetailVRViewHolder).bind(mList[position])
            HR_TYPE->(holder as MasterDetailHRViewHolder).bind(mList[position])
        }

    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setHeadingValueModelList(mList: List<HeadingValueModel>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    // For Vertical layout

    inner class MasterDetailVRViewHolder(
        val binding: TemplateDetailVrBinding,
        private val mListener: MasterDetailInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }


        }

        fun bind(item: HeadingValueModel) {
            binding.apply {
                tvSubtitle.text = item.value
                tvTitle.text = item.title
            }
        }
    }

    // For Horizontal layout
    inner class MasterDetailHRViewHolder(
        val binding: TemplateDetailHrBinding,
        private val mListener: MasterDetailInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }


        }

        fun bind(item: HeadingValueModel) {
            binding.apply {
                tvSubtitle.text = item.value
                tvTitle.text = item.title
            }
        }
    }


    interface MasterDetailInterface {

    }


    companion object {
        const val VR_TYPE = 1
        const val HR_TYPE = 2
    }
}