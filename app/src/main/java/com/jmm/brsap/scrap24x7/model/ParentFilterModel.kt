package com.jmm.brsap.scrap24x7.model

import com.jmm.brsap.scrap24x7.util.FilterEnum

/*

Author : Praveen A. Yadav
Created On : 03:54 11-06-2021

*/

data class ParentFilterModel(
    val id: FilterEnum,
    val heading:String,
    val filterItems:List<FilterModel>,
    val singleSelection:Boolean = false
)
