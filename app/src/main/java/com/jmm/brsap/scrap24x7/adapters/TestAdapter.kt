package com.jmm.brsap.scrap24x7.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateSectionedRvBinding
import com.jmm.brsap.scrap24x7.model.network_models.*
import com.jmm.brsap.scrap24x7.model.*

class TestAdapter(private val mListener: TestInterface) :
    RecyclerView.Adapter<TestAdapter.TestViewHolder>() {


    private val mList = mutableListOf<Customer>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TestViewHolder {
        return TestViewHolder(
            TemplateSectionedRvBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: TestViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setCustomerList(mList: List<Customer>) {
        this.mList.clear()
        this.mList.addAll(mList)
        notifyDataSetChanged()
    }

    inner class TestViewHolder(
        val binding: TemplateSectionedRvBinding,
        private val mListener: TestInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {

            }


        }

        fun bind(item: Customer) {
            binding.apply {

            }
        }
    }

    interface TestInterface {

    }


}