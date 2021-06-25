package com.jmm.brsap.scrap24x7.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType2Binding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*
import com.jmm.brsap.scrap24x7.util.convertYMD2MDY

class TodayCollectionAdapter(private val mListener: TodayCollectionInterface) :
    RecyclerView.Adapter<TodayCollectionAdapter.TodayCollectionViewHolder>() {


    private val mList = mutableListOf<PickupRequest>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodayCollectionViewHolder {
        return TodayCollectionViewHolder(
            TemplateMasterRvActionRowType2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: TodayCollectionViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setPickupRequestList(mList: List<PickupRequest>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class TodayCollectionViewHolder(
        val binding: TemplateMasterRvActionRowType2Binding,
        private val mListener: TodayCollectionInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.frameData.setOnClickListener {
                mListener.onItemClick(mList[absoluteAdapterPosition])
            }


        }

        fun bind(item: PickupRequest) {
            binding.apply {
                if (item.customer_address?.locality != null) {
                    tvMainTitle.text = item.customer_address.locality.toString()
                } else {
                    tvMainTitle.text = item.customer_address?.city.toString()
                }

                tvSubTitle.text = "${item.pickup_id.toString()}"
                tvSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
//                tvSecondaryTitle.text = item.status_name.toString()
//                tvSecondarySubtitle.text = convertYMD2MDY(item.pickup_requested_date.toString())


            }
        }
    }

    interface TodayCollectionInterface {
        fun onItemClick(item: PickupRequest)
    }


}