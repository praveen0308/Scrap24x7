package com.jmm.brsap.scrap24x7.adapters

import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.model.PickupTrackingStep
import com.transferwise.sequencelayout.SequenceAdapter
import com.transferwise.sequencelayout.SequenceStep

/*

Author : Praveen A. Yadav
Created On : 03:29 22-06-2021

*/

class PickupTrackingAdapter(private val items: List<PickupTrackingStep>) : SequenceAdapter<PickupTrackingStep>() {

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): PickupTrackingStep {
        return items[position]
    }

    override fun bindView(sequenceStep: SequenceStep, item: PickupTrackingStep) {
        with(sequenceStep) {
            
            setActive(item.isActive)
            setAnchor(item.timestamp)
            setAnchorTextAppearance(R.style.Base_TextAppearance_AppCompat_Small)
            setTitle(item.title)
            setTitleTextAppearance(R.style.Base_TextAppearance_AppCompat_Body1)
            setSubtitle(item.subtitle)
            setSubtitleTextAppearance(R.style.Base_TextAppearance_AppCompat_Small)
        }
    }


}