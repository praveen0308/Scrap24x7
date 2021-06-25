package com.jmm.brsap.scrap24x7.pagingDataAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.VehicleMasterListAdapter
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.VehicleMaster

class VehicleMasterPagingAdapter(private val mListener: VehicleMasterListAdapter.VehicleMasterListInterface) :
    PagingDataAdapter<VehicleMaster, VehicleMasterPagingAdapter.VehicleMasterViewHolder>(VehicleMasterComparator) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): VehicleMasterViewHolder {
        return VehicleMasterViewHolder(
            TemplateMasterRvActionRowType1Binding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),mListener
        )
    }


    override fun onBindViewHolder(holder: VehicleMasterViewHolder, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class VehicleMasterViewHolder(
        val binding: TemplateMasterRvActionRowType1Binding,
        private val mListener: VehicleMasterListAdapter.VehicleMasterListInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.frameData.setOnClickListener {
                mListener.onItemClick(getItem(absoluteAdapterPosition)!!)
            }
            binding.btnAction2.setOnClickListener {
                mListener.onUpdateClick(getItem(absoluteAdapterPosition)!!)
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
    companion object {
        private val VehicleMasterComparator = object : DiffUtil.ItemCallback<VehicleMaster>() {
            override fun areItemsTheSame(oldItem: VehicleMaster, newItem: VehicleMaster): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: VehicleMaster, newItem: VehicleMaster): Boolean {
                return oldItem == newItem
            }
        }
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
////            override fun performFiltering(constraint: CharSequence?): FilterResults {
////                val charSearch = constraint.toString()
////                val filteredList = listOf<VehicleMaster>()
////                if (charSearch.isEmpty()) {
////                    filteredList = curren
////
////                } else {
////                    val resultList = ArrayList<String>()
////                    for (row in countryList) {
////                        if (row.toLowerCase(Locale.ROOT).contains(charSearch.toLowerCase(Locale.ROOT))) {
////                            resultList.add(row)
////                        }
////                    }
////                    filteredList = resultList
////                }
////                val filterResults = FilterResults()
////                filterResults.values = filteredList
////                return filterResults
////            }
////
////            @Suppress("UNCHECKED_CAST")
////            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
////                filteredList = results?.values as ArrayList<String>
////                notifyDataSetChanged()
////            }
//
//        }
//    }
}