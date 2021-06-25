package com.jmm.brsap.scrap24x7.model.network_models

data class Category(
        val created_at: String?=null,
        val created_by: Any?=null,
        val description: String?=null,
        val id: Int?=null,
        val image_path: Any?=null,
        val image_url: Any?=null,
        val is_active: Int?=null,
        val type: String?=null,
        val updated_at: String?=null,
        val updated_by: Any?=null,
 // custom
        var categoryItemList:MutableList<CategoryItem>?=null,
        var isSelected:Boolean = false,
        var drawableUrl:Int?=null
){
        override fun toString(): String {
                return type.toString()
        }
}