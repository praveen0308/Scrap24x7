package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType1Binding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class ManageUserAdapter(private val mListener: ManageUserInterface) :
    RecyclerView.Adapter<ManageUserAdapter.ManageUserViewHolder>() {


    private val mList = mutableListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ManageUserViewHolder {
        return ManageUserViewHolder(
            TemplateMasterRvActionRowType1Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ManageUserViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setAnyList(mList: List<Any>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class ManageUserViewHolder(
        val binding: TemplateMasterRvActionRowType1Binding,
        private val mListener: ManageUserInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.frameData.setOnClickListener {
                mListener.onItemClick(mList[absoluteAdapterPosition])
            }

            binding.btnAction3.setOnClickListener {
                mListener.onCallClick(mList[absoluteAdapterPosition])
            }
        }

        fun bind(item: Any) {
            binding.apply {
                btnAction3.visibility = View.VISIBLE
                btnAction3.setIcon(R.drawable.ic_round_phone_24)
                btnAction3.setTitle("Call")
                when(item){

                    is Customer->{
                        tvMainTitle.text = "${item.first_name} ${item.last_name}"
                        tvSymbol.text = item.first_name.take(1)
                        tvSubTitle.text = "ID: ${item.customer_id}"
                        btnAction1.visibility = View.VISIBLE
                        btnAction1.setIcon(R.drawable.ic_baseline_block_24)
                        btnAction1.setTitle("Block")
                    }
                }
            }
        }
    }

    interface ManageUserInterface {
        fun onItemClick(item:Any)
        fun onCallClick(item: Any)
        fun onCustomerBlockClick(item: Any)
    }
}