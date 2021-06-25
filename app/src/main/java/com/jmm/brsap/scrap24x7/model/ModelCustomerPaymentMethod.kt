package com.jmm.brsap.scrap24x7.model

import android.view.Menu
import com.jmm.brsap.scrap24x7.util.MenuEnum

data class ModelCustomerPaymentMethod(
        val id: MenuEnum,
        val title: String? = null,
        val subTitle: String? = null,
        val imageUrl: Int,
        val havingDivider: Boolean = false,
        var isDefault:Boolean=false
)
