package com.jmm.brsap.scrap24x7.adapters

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.TemplateMasterRvActionRowType2Binding
import com.jmm.brsap.scrap24x7.model.network_models.PickupRequest
import com.jmm.brsap.scrap24x7.util.convertYMD2MDY

class ExecutivePickupHistoryAdapter(private val mListener: ManagePickupRequestInterface) :

    RecyclerView.Adapter<ExecutivePickupHistoryAdapter.ManagePickupRequestViewHolder>() {


    private val mList = mutableListOf<PickupRequest>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ManagePickupRequestViewHolder {
        return ManagePickupRequestViewHolder(
            TemplateMasterRvActionRowType2Binding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ), mListener
        )
    }

    override fun onBindViewHolder(holder: ManagePickupRequestViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    fun setPickupRequestList(newList: List<PickupRequest>) {
        val diffCallback = PickupRequestCallback(mList, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.mList.clear()
        this.mList.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class ManagePickupRequestViewHolder(
        val binding: TemplateMasterRvActionRowType2Binding,
        private val mListener: ManagePickupRequestInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        init {

            binding.btnAction3.setOnClickListener {
                mListener.onAccepted(mList[absoluteAdapterPosition])
                binding.root.close(true)
            }
            binding.btnAction1.setOnClickListener {
                mListener.onRejected(mList[absoluteAdapterPosition])
                binding.root.close(true)
            }

            binding.btnAction2.setOnClickListener {
                mListener.onHold(mList[absoluteAdapterPosition])
                binding.root.close(true)
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
                tvSecondaryTitle.text = item.status_name.toString()
                tvSecondarySubtitle.text = convertYMD2MDY(item.pickup_requested_date.toString())


//                btnAction1.setTitle(root.context.getString(R.string.reject))
//                btnAction1.setIcon(R.drawable.ic_round_close_24)
//
//
//                btnAction2.setTitle(root.context.getString(R.string.hold))
//                btnAction2.setIcon(R.drawable.ic_round_pause_24)
//
//
//                btnAction3.setTitle(root.context.getString(R.string.accept))
//                btnAction3.setIcon(R.drawable.ic_round_check_24)
//
//                when(item.pickup_status){
//                    1->{
//                        btnAction1.isVisible = true
//                        btnAction2.isVisible = true
//                        btnAction3.isVisible = true
//                    }
//                    2->{
//                        btnAction1.isVisible = true
//                        btnAction2.isVisible = true
//                    }
//                    3->{
//                        btnAction2.isVisible = true
//                        btnAction3.isVisible = true
//                    }
//                    else->{
//
//                    }
//                }

            }
        }
    }

    interface ManagePickupRequestInterface {
        fun onRejected(request: PickupRequest)
        fun onAccepted(request: PickupRequest)
        fun onHold(request: PickupRequest)
    }
    class PickupRequestCallback(private val oldList: List<PickupRequest>, private val newList: List<PickupRequest>) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id === newList.get(newItemPosition).id
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