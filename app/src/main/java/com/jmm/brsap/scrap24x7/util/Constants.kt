package com.jmm.brsap.scrap24x7.util

import java.text.SimpleDateFormat
import java.util.*

object Constants {
    const val BASE_URL = "http://webapi.scrap24x7.in/api/"
//    const val BASE_URL = "http://10.0.2.2:8000/api/"

    // Date Formats
    const val SDF_EDMY = "EEEE, dd MMM yyyy"

    // Pickup Status
    const val REQUESTED = 1
    const val ACCEPTED = 2
    const val REJECTED = 3
    const val PROCESSING = 4
    const val OUT_FOR_PICKUP = 5
    const val COLLECTED = 6
    const val CANCELLED = 7
}