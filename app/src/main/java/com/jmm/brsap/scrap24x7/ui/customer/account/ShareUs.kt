package com.jmm.brsap.scrap24x7.ui.customer.account

import android.content.Intent
import android.os.Bundle
import android.view.View

import com.jmm.brsap.scrap24x7.BuildConfig.APPLICATION_ID
import com.jmm.brsap.scrap24x7.databinding.FragmentShareUsBinding
import com.jmm.brsap.scrap24x7.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ShareUs : BaseFragment<FragmentShareUsBinding>(FragmentShareUsBinding::inflate){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnShare.setOnClickListener{
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(
                Intent.EXTRA_TEXT,
                "Hey check out my app at: https://play.google.com/store/apps/details?id=$APPLICATION_ID"
            )
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }

    override fun subscribeObservers() {

    }
}