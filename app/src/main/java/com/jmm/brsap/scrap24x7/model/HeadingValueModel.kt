package com.jmm.brsap.scrap24x7.model

import android.os.Parcelable
import com.jmm.brsap.scrap24x7.util.OtherEnum

/*

Author : Praveen A. Yadav
Created On : 07:49 18-06-2021

*/


//@Parcelize
data class HeadingValueModel(
    val title:String,
    val value:String,
    val type:OtherEnum = OtherEnum.VERTICAL
)
