package com.jmm.brsap.scrap24x7.model.network_models

data class PickupRequestItem(
    val created_at: String? = null,
    val created_by: Any? = null,
    val id: Int? = null,
    val is_cancel: Int? = 0,
    val item_id: Int? = null,
    val pickup_request_id: String? = null,
    val price: Double? = null,
    var quantity: Double? = null,
    var total_price: Double? = null,
    val updated_at: String? = null,
    val updated_by: Any? = null,

    //custom
    val category_item: CategoryItem?=null
)