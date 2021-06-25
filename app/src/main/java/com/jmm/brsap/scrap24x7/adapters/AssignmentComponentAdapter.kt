package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType2Binding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class AssignmentComponentAdapter(private val mListener: AssignmentComponentInterface) :
    RecyclerView.Adapter<AssignmentComponentAdapter.AssignmentComponentViewHolder>() {


    private val mList = mutableListOf<ModelAssignmentComponent>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AssignmentComponentViewHolder {
        return AssignmentComponentViewHolder(
            TemplateMasterRvActionRowType2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: AssignmentComponentViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setModelAssignmentComponentList(mList: List<ModelAssignmentComponent>) {
//        val diffResult = DiffUtil.calculateDiff(MyDiffCallback(this.mList, mList))
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
//        diffResult.dispatchUpdatesTo(this)
    }

    inner class AssignmentComponentViewHolder(
        val binding: TemplateMasterRvActionRowType2Binding,
        private val mListener: AssignmentComponentInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.frameData.setOnClickListener {
                for (item in mList) {
                    item.isSelected = false
                }
                mList[absoluteAdapterPosition].isSelected = true
                mListener.onItemClick(mList[absoluteAdapterPosition])
                notifyDataSetChanged()
            }


        }

        fun bind(item: ModelAssignmentComponent) {
            binding.apply {
                tvMainTitle.text = item.title
                tvSubTitle.text = item.subTitle
                tvSecondaryTitle.text = item.secondaryTitle
                tvSecondarySubtitle.text = item.secondarySubTitle

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
                    ivIcon.setImageResource(item.imageUrl)
                    ivIcon.setColorFilter(ContextCompat.getColor(root.context, R.color.colorPrimary))
                }

            }
        }
    }

    interface AssignmentComponentInterface {
        fun onItemClick(item: ModelAssignmentComponent)
    }


    inner class MyDiffCallback(
        private val oldList: List<ModelAssignmentComponent>,
        private val newList: List<ModelAssignmentComponent>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].componentId == newList[newItemPosition].componentId
        }

        override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
            val (_, value, name) = oldList[oldPosition]
            val (_, value1, name1) = newList[newPosition]

            return name == name1 && value == value1
        }

        @Nullable
        override fun getChangePayload(oldPosition: Int, newPosition: Int): Any? {
            return super.getChangePayload(oldPosition, newPosition)
        }
    }


}