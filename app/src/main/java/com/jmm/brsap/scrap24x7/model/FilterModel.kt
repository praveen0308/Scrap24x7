package com.jmm.brsap.scrap24x7.model

import com.jmm.brsap.scrap24x7.util.FilterEnum

/*

Author : Praveen A. Yadav
Created On : 12:09 31-05-2021

*/

data class FilterModel(
    val parentId:FilterEnum,
    val filterId:FilterEnum?=null,
    val id:Int?=null,
    val title:String,
    var isSelected:Boolean = false
)
