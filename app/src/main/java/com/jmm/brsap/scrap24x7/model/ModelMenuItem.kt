package com.jmm.brsap.scrap24x7.model

import com.jmm.brsap.scrap24x7.util.MenuEnum

data class ModelMenuItem(
        val id:MenuEnum,
        val title:String,
        val description:String,
        val iconImage:Int
)
