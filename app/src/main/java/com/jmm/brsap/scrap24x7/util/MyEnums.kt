package com.jmm.brsap.scrap24x7.util

enum class MenuEnum{
    PROFILE,PAYMENT_METHOD,ADDRESS_SETTINGS,NOTIFICATION,FAQ,ABOUT,SHARE,LOGOUT,HELP_CENTRE,LANGUAGE,
    HOW_IT_WORKS,PICKUP_DETAIL,
    PAYTM,CASH,
    EXECUTIVE_DASHBOARD,
    EXECUTIVE_PROFILE,
    EXECUTIVE_PICKUP_HISTORY,
}

enum class AdminEnum{
    // type
    NAV,STAT,ADD,EDIT,

    // id
     CUSTOMER,LOCATION,PICKUP_REQUEST,MANAGE_SCRAP, WAREHOUSE,VEHICLES,
    MANAGE_USERS,SCRAP_REPORT,SETTINGS,LOG_OUT,

    ADD_SCRAP_CATEGORY,ADD_SCRAP_ITEM,ADD_NEW_UNIT,
    MANAGE_SCRAP_CATEGORY,MANAGE_SCRAP_ITEMS,MANAGE_UNIT,
    CATEGORY,CATEGORY_ITEM,UNIT,

    EXECUTIVE,DRIVER,
    MANAGE_CATEGORIES
}

enum class UserEnum{
    ADMIN,CUSTOMER,EXECUTIVE,DRIVER
}
enum class PagingEnum{
    CUSTOMER,VEHICLE_MASTER,DRIVER_MASTER,EXECUTIVE_MASTER,LOCATION_MASTER,
    PICKUP_REQUEST,CATEGORY_ITEM,
}

enum class OtherEnum{
    EDITABLE,NON_EDITABLE,VERTICAL,HORIZONTAL
}

enum class FilterEnum{
    // TIME
    TODAY,YESTERDAY,LAST_WEEK,LAST_MONTH,CUSTOM,
    TOMORROW,THIS_WEEK,THIS_MONTH,

    //Pickup request type
    PICKUP,RAISED,

    //Other
    ALL,TIME_FILTER,LOCATION_FILTER
}

enum class PickupStatus(private val status: Int)
{
    Pending(1),
    Processing(2),
    Complete(3),
    Cancelled(4),
    CancelRequested(5),
    CancelRejected(6),
    CancelApproved(7),
    ReturnRequested(8),
    ReturnRejected(9),
    ReturnApproved(1),
    Returned(1);

    companion object {
        fun getStatus(status: Int) = values().find{ it.status == status }
    }

}
