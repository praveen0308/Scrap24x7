package com.jmm.brsap.scrap24x7.model

import com.jmm.brsap.scrap24x7.util.AdminEnum

/*

Author : Praveen A. Yadav
Created On : 08:01 06-06-2021

*/

data class ModelAssignmentComponent(
    val componentId:Int,
    val type:AdminEnum,
    val title:String?=null,
    val secondaryTitle:String?=null,
    val subTitle:String?=null,
    val secondarySubTitle:String?=null,
    var isSelected:Boolean =false,
    val imageUrl:Int=0

)
