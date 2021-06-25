package com.jmm.brsap.scrap24x7.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.isVisible
import com.jmm.brsap.scrap24x7.R
import com.jmm.brsap.scrap24x7.databinding.ActivityUserNotificationsBinding
import com.jmm.brsap.scrap24x7.ui.BaseActivity
import com.jmm.brsap.scrap24x7.util.ApplicationToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserNotifications : BaseActivity1<ActivityUserNotificationsBinding>(ActivityUserNotificationsBinding::inflate),
    ApplicationToolbar.ApplicationToolbarListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.toolbarUserNotifications.setApplicationToolbarListener(this)
        binding.myEmptyView.isVisible = true
    }

    override fun onToolbarNavClick() {
        finish()
    }

    override fun onMenuClick() {

    }

    override fun subscribeObservers() {

    }


}