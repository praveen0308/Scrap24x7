package com.jmm.brsap.scrap24x7.model

import com.jmm.brsap.scrap24x7.util.AdminEnum

data class ModelAdminUniversalUtil(
    val id:AdminEnum,
    val type:AdminEnum,
    val heading:String="",
    val data:String="",
    val imageUrl:Int=0
)

