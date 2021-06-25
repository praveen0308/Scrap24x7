package com.jmm.brsap.scrap24x7.ui.admin

import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.utils.ColorTemplate
import com.jmm.brsap.scrap24x7.databinding.FragmentScrapReportBinding
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.RoundedSlicesPieChartRenderer


class ScrapReport : BaseFragment<FragmentScrapReportBinding>(FragmentScrapReportBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setRaisedRequestChartData()
        setScrapCollectedChartData()
    }

    override fun subscribeObservers() {

    }

    private fun setRaisedRequestChartData() {
        val time = floatArrayOf(55f, 95f, 30f, 30f,(360 - (55 + 95 + 30+30)).toFloat())
        val activity = arrayOf("Plastic", "Metal", "Paper", "E-waste","Others")
        //pupulating list of PieEntries
        val pieEntries: MutableList<PieEntry> = ArrayList()
        for (i in time.indices) {
            pieEntries.add(PieEntry(time[i], activity[i]))
        }
        val dataSet = PieDataSet(pieEntries, "")
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        val data = PieData(dataSet)
        //pie slices text value
        data.setValueTextSize(0F)

        //Get the chart
        binding.ctRaisedRequest.data = data
        binding.ctRaisedRequest.invalidate()
        binding.ctRaisedRequest.centerText = "June"
        binding.ctRaisedRequest.setDrawEntryLabels(false)
        binding.ctRaisedRequest.contentDescription = ""

        binding.ctRaisedRequest.setEntryLabelTextSize(8F)
        binding.ctRaisedRequest.holeRadius = 55F

        binding.ctRaisedRequest.description.isEnabled = false
        //legend attributes

        //legend attributes
        val legend: Legend = binding.ctRaisedRequest.getLegend()
        legend.form = Legend.LegendForm.CIRCLE
        legend.textSize = 12F

        legend.formSize = 20F
        legend.formToTextSpace = 2F

        legend.isWordWrapEnabled = true

//        binding.chart.renderer = RoundedSlicesPieChartRenderer(
//            binding.chart,
//            binding.chart.animator,
//            binding.chart.viewPortHandler
//        )
    }



    private fun setScrapCollectedChartData() {
        val time = floatArrayOf(55f, 95f, 30f, 30f,(360 - (55 + 95 + 30+30)).toFloat())
        val activity = arrayOf("Plastic", "Metal", "Paper", "E-waste","Others")
        //pupulating list of PieEntries
        val pieEntries: MutableList<PieEntry> = ArrayList()
        for (i in time.indices) {
            pieEntries.add(PieEntry(time[i], activity[i]))
        }
        val dataSet = PieDataSet(pieEntries, "")
        dataSet.setColors(*ColorTemplate.MATERIAL_COLORS)
        val data = PieData(dataSet)
        //pie slices text value
        data.setValueTextSize(0F)

        //Get the chart
        binding.ctScrapCollection.data = data
        binding.ctScrapCollection.invalidate()
        binding.ctScrapCollection.centerText = "June"
        binding.ctScrapCollection.setDrawEntryLabels(false)
        binding.ctScrapCollection.contentDescription = ""

        binding.ctScrapCollection.setEntryLabelTextSize(8F)
        binding.ctScrapCollection.holeRadius = 55F

        binding.ctScrapCollection.description.isEnabled = false
        //legend attributes

        //legend attributes
        val legend: Legend = binding.ctScrapCollection.legend
        legend.form = Legend.LegendForm.CIRCLE
        legend.textSize = 12F

        legend.formSize = 20F
        legend.formToTextSpace = 2F

        legend.isWordWrapEnabled = true

//        binding.chart.renderer = RoundedSlicesPieChartRenderer(
//            binding.chart,
//            binding.chart.animator,
//            binding.chart.viewPortHandler
//        )
    }
}


