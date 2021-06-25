package com.jmm.brsap.scrap24x7.ui.welcome

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.FragmentLoginSignupScreenBinding
import com.jmm.brsap.scrap24x7.databinding.FragmentOnBoardingScreenBinding


class LoginSignupScreen : Fragment() {

    private var _binding: FragmentLoginSignupScreenBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginSignupScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewPager()
    }

    private fun setupViewPager() {
        binding.apply {

            viewPager.adapter = ViewPagerAdapter(requireActivity())
            val tabLayoutMediator = TabLayoutMediator(
                tabLayout, viewPager
            ) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Login"
//                    tab.setIcon(R.drawable.ic_icon_income)
//                    tab.icon!!.setTintList(null)
                    }
                    1 -> {
                        tab.text = "Sign Up"

                    }
                }
            }
            tabLayoutMediator.attach()
            viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            })

        }

    }


    inner class ViewPagerAdapter(fragmentActivity: FragmentActivity) :
        FragmentStateAdapter(fragmentActivity) {
        override fun createFragment(position: Int): Fragment {
            return when (position) {
                0 -> Login()
                1 -> SignUpSelection()
                else -> Login()

            }
        }

        override fun getItemCount(): Int {
            return 2
        }
    }

}