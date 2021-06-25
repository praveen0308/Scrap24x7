package com.jmm.brsap.scrap24x7.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.adapters.OnBoardingItemsAdapter
import com.jmm.brsap.scrap24x7.databinding.FragmentOnBoardingScreenBinding
import com.jmm.brsap.scrap24x7.model.OnBoardingItem
import com.jmm.brsap.scrap24x7.repository.UserPreferencesRepository.Companion.ON_BOARDING_DONE
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import com.jmm.brsap.scrap24x7.viewmodel.SplashScreenViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OnBoardingScreen : BaseFragment<FragmentOnBoardingScreenBinding>(FragmentOnBoardingScreenBinding::inflate) {

    private val viewModel by viewModels<SplashScreenViewModel>()
    private lateinit var onBoardingItemsAdapter: OnBoardingItemsAdapter
    private val onBoardingItemList : MutableList<OnBoardingItem> = ArrayList()

    private var position : Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prepareOnBoardingItemList()
        setOnBoardingItems()

        position = binding.vpOnboarding.currentItem
        binding.btnClickNext.setOnClickListener {
            if (position<onBoardingItemList.size){
                position++
                binding.vpOnboarding.currentItem = position
            }
            if (position==onBoardingItemList.size){
                viewModel.updateWelcomeStatus(ON_BOARDING_DONE)
                findNavController().navigate(OnBoardingScreenDirections.actionOnBoardingScreenToLoginSignupScreen())
            }
        }

        binding.tlIndicator.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                position = tab!!.position

                binding.btnClickNext.text = if (position==onBoardingItemList.size-1) "Get Started" else "Next"
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })
    }

    private fun prepareOnBoardingItemList(){
        onBoardingItemList.addAll(listOf(
            OnBoardingItem(
                 R.drawable.ic_select_option,
                 getString(R.string.select_for_selling),
                 getString(R.string.msg_onboarding_1)
            ),
            OnBoardingItem(
                 R.drawable.ic_select_date,
                 getString(R.string.choose_for_scrap_pickup),
                getString(R.string.msg_onboarding_2)
            ),
            OnBoardingItem(
                 R.drawable.ic_pickup,
                getString(R.string.pickup_boys_arrive),
                getString(R.string.msg_onboarding_3)
            ),
            OnBoardingItem(
                 R.drawable.ic_scrap_sold,
                 getString(R.string.scrap_sold),
                getString(R.string.msg_onboarding_4)
            )

        ))
    }
    private fun setOnBoardingItems(){

        onBoardingItemsAdapter = OnBoardingItemsAdapter(onBoardingItemList)

        binding.vpOnboarding.apply {
            adapter = onBoardingItemsAdapter
            overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }


        TabLayoutMediator(binding.tlIndicator, binding.vpOnboarding,{ tab, position -> }).attach()
    }

    override fun subscribeObservers() {

    }
}