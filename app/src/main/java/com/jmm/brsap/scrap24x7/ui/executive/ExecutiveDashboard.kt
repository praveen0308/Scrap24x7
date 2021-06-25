package com.jmm.brsap.scrap24x7.ui.executive

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentExecutiveDashboardBinding
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.util.*
import com.jmm.brsap.scrap24x7.viewmodel.executive.HomeExecutiveViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExecutiveDashboard : BaseFragment<FragmentExecutiveDashboardBinding>(FragmentExecutiveDashboardBinding::inflate) {

    private val viewModel by viewModels<HomeExecutiveViewModel>()
    private var pStatus = 0
    private var executiveId = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cdTodayCollection.setOnClickListener {
            startActivity(Intent(requireActivity(),TodayCollection::class.java))
        }


    }
    
    override fun subscribeObservers() {
        viewModel.userName.observe(this,{
            binding.apply {
                "${getGreetings()}, $it".also { tvGreeting.text = it }
            }
        })
        viewModel.userId.observe(this,{
            executiveId = it
            viewModel.getExecutivePickupSummary(getDateRange(FilterEnum.TODAY), getTodayDate(),executiveId)
        })

        binding.tvDate.text = convertYMD2EMDY(getTodayDate())
        viewModel.pickupSummary.observe(this, { _result ->
            when (_result.status) {
                Status.SUCCESS -> {
                    _result._data?.let {
                        binding.apply {
                            tvData.text = it.data.pickup_count.toString()
                            if(it.data.pickup_count!=0) setCollectionProgress(it.data.collected_pickup/it.data.pickup_count*100)
                            else setCollectionProgress(0)
                        }
                    }
                    displayLoading(false)
                }
                Status.LOADING -> {
                    displayLoading(true)
                }
                Status.ERROR -> {
                    displayLoading(false)
                    _result.message?.let {
                        displayError(it)
                    }
                }
            }
        })
    }


    private fun setCollectionProgress(count:Int){

        Thread {

            while (pStatus < count) {
                pStatus += 1
                Handler(Looper.getMainLooper()).post {
                    binding.progressTodayCollection.progress = pStatus.toFloat()
                    binding.tvCollectionProgress.text = pStatus.toString() + "%"
                }
                try {
                    Thread.sleep(16) //thread will take approx 3 seconds to finish,change its value according to your needs
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }.start()


    }


}