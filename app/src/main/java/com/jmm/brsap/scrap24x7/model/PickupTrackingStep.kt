package com.jmm.brsap.scrap24x7.model

/*

Author : Praveen A. Yadav
Created On : 03:33 22-06-2021

*/

data class PickupTrackingStep(
    val title:String,
    val subtitle:String?=null,
    var isActive:Boolean=false,
    var timestamp:String
)
