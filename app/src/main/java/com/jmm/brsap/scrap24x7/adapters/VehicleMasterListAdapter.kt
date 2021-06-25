package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster

class VehicleMasterListAdapter(private val mListener: VehicleMasterListInterface) :
    RecyclerView.Adapter<VehicleMasterListAdapter.VehicleMasterListViewHolder>() {


    private val mList = mutableListOf<VehicleMaster>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleMasterListViewHolder {
        return VehicleMasterListViewHolder(
            TemplateMasterRvActionRowType1Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: VehicleMasterListViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setVehicleMasterList(mList: List<VehicleMaster>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class VehicleMasterListViewHolder(
        val binding: TemplateMasterRvActionRowType1Binding,
        private val mListener: VehicleMasterListInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                mListener.onItemClick(mList[absoluteAdapterPosition])
            }
            binding.btnAction2.setOnClickListener {
                mListener.onUpdateClick(mList[absoluteAdapterPosition])
                binding.swipeRevealLayout.close(true)
            }
        }

        fun bind(item: VehicleMaster) {
            binding.apply {
                tvMainTitle.text = item.owner_name
                tvSymbol.text = item.owner_name!!.take(1)
                tvSubTitle.text = "Plate : ${item.vehicle_plate_no}"
                btnAction2.visibility = View.VISIBLE
                btnAction2.setIcon(R.drawable.ic_baseline_edit_24)
                btnAction2.setTitle("Update")

            }
        }
    }

    interface VehicleMasterListInterface {
        fun onItemClick(item : VehicleMaster)
        fun onUpdateClick(item : VehicleMaster)
    }

}