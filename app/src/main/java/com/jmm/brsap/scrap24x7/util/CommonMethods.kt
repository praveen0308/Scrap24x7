package com.jmm.brsap.scrap24x7.util

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.CalendarConstraints.DateValidator
import com.jmm.brsap.scrap24x7.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


private val SDF_YMD_WITH_DASH = SimpleDateFormat("yyyy-MM-dd", Locale.US)
private val SDF_EDMY_WITH_NORMAL = SimpleDateFormat("EEEE, dd MM yyyy", Locale.US)
val SDF_EdMyhmaa = SimpleDateFormat("EEE,dd MMM yyyy | hh:mm aa", Locale.US)
val SDF_dM = SimpleDateFormat("dd MMM", Locale.US)
fun convertISOTimeToDateTime(isoTime: String): String? {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    var convertedDate: Date? = null
    var formattedDate: String? = null
    try {
        convertedDate = sdf.parse(isoTime)
        formattedDate = SimpleDateFormat("MMM dd,yyyy | HH:mm").format(convertedDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return formattedDate
}

fun convertISOTimeToAny(isoTime: String,myFormatter: SimpleDateFormat): String? {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
    var convertedDate: Date? = null
    var formattedDate: String? = null
    try {
        convertedDate = sdf.parse(isoTime)
        formattedDate = myFormatter.format(convertedDate)
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    return formattedDate
}


fun convertMillisecondsToDate(milliSeconds: Long?, dateFormat: String?): String {
    // Create a DateFormatter object for displaying date in specified format.
    val formatter = SimpleDateFormat(dateFormat)

    // Create a calendar object that will convert the date and time value in milliseconds to date.
    val calendar = Calendar.getInstance()
    if (milliSeconds != null) {
        calendar.timeInMillis = milliSeconds
    }
    return formatter.format(calendar.time)
}


fun getDateRange(type: FilterEnum):String{
    return when(type){

        FilterEnum.LAST_MONTH ->
            getDaysAgo(-30)
        FilterEnum.LAST_WEEK ->
            getDaysAgo(-7)
        FilterEnum.YESTERDAY->
            getDaysAgo(-1)
        FilterEnum.TODAY->
            getDaysAgo(0)
        FilterEnum.TOMORROW->
            getDaysAgo(1)
        FilterEnum.THIS_WEEK ->
            getDaysAgo(7)
        FilterEnum.THIS_MONTH ->
            getDaysAgo(30)

        else-> getDaysAgo(0)
    }

}


fun getDateLabelAcToFilter(type: FilterEnum):String{
    return when(type){

        FilterEnum.LAST_MONTH ->
            "Last Month"
        FilterEnum.LAST_WEEK ->
            "Last Week"
        FilterEnum.YESTERDAY->
            "Yesterday"
        FilterEnum.TODAY->
            "Today"
        FilterEnum.TOMORROW->
            "Tomorrow"
        FilterEnum.THIS_WEEK ->
            "This Week"
        FilterEnum.THIS_MONTH ->
            "This Month"

        else-> ""
    }

}

fun getDatesList(startDate:String,endDate:String):List<String>{
    val dates: MutableList<Date> = ArrayList()
    val formattedDates = mutableListOf<String>()

    val formatter = SimpleDateFormat("yyyy-MM-dd")
    try {
        val interval = (24 * 1000 * 60 * 60).toLong() // 1 hour in millis
        val endTime = formatter.parse(endDate)!!.time // create your endtime here, possibly using Calendar or Date
        var curTime = formatter.parse(startDate)!!.time
        while (curTime < endTime) {
            dates.add(Date(curTime))
            curTime += interval
        }

        for (i in dates.indices) {
            val lDate = dates[i]
            formattedDates.add(formatter.format(lDate))

        }
    } catch (e: Exception) {
        Log.e("ERROR",e.toString())
    }finally {
        return formattedDates
    }
}
fun getTodayDate():String{
    return getDaysAgo(0)
}


fun getDaysAgo(daysAgo: Int): String {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, daysAgo)
    return SDF_YMD_WITH_DASH.format(calendar.time)
}


fun getColoredSpanned(text: String, color: String): String {
    return "<font color=$color>$text</font>"
}

fun getColoredSpanned(text: String, color: Int): String {

    val colorCode = Color.parseColor("#" + Integer.toHexString(color))

    return "<font color=$colorCode>$text</font>"
}

fun convertYMD2MDY(date:String):String{
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val date = formatter.parse(date)
    val desiredFormat = SimpleDateFormat("MMM dd,yyyy").format(date)
   return desiredFormat
}


fun convertYMD2EMDY(date:String):String{
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val date = formatter.parse(date)
    val desiredFormat = SimpleDateFormat("EE,MMM dd yyyy").format(date)
    return desiredFormat
}

fun convertYMD2Any(date:String,format:String):String{
    val formatter = SimpleDateFormat("yyyy-MM-dd")
    val date = formatter.parse(date)
    val desiredFormat = SimpleDateFormat(format).format(date)
    return desiredFormat
}
/*
       Limit selectable Date range
     */
fun limitRange(): CalendarConstraints.Builder {
    val constraintsBuilderRange = CalendarConstraints.Builder()
    val calendarStart = Calendar.getInstance()
    val calendarEnd = Calendar.getInstance()
    calendarEnd.add(Calendar.DAY_OF_YEAR, 20)
//    val year = 2021
//    val startMonth = 2
//    val startDate = 15
//    val endMonth = 5
//    val endDate = 20
////    calendarStart[year, startMonth - 1] = startDate - 1

    val minDate = calendarStart.timeInMillis
    val maxDate = calendarEnd.timeInMillis
    constraintsBuilderRange.setStart(minDate)
    constraintsBuilderRange.setEnd(maxDate)
    constraintsBuilderRange.setValidator(RangeValidator(minDate, maxDate))
    return constraintsBuilderRange
}


internal class RangeValidator : DateValidator {
    var minDate: Long
    var maxDate: Long

    constructor(minDate: Long, maxDate: Long) {
        this.minDate = minDate
        this.maxDate = maxDate
    }

    constructor(parcel: Parcel) {
        minDate = parcel.readLong()
        maxDate = parcel.readLong()
    }

    override fun isValid(date: Long): Boolean {
        return !(minDate > date || maxDate < date)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel, flags: Int) {
        dest.writeLong(minDate)
        dest.writeLong(maxDate)
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<RangeValidator?> =
            object : Parcelable.Creator<RangeValidator?> {
                override fun createFromParcel(parcel: Parcel): RangeValidator {
                    return RangeValidator(parcel)
                }

                override fun newArray(size: Int): Array<RangeValidator?> {
                    return arrayOfNulls(size)
                }
            }
    }
}


// This method is for increasing number animation
fun animateTextView(initialValue: Int, finalValue: Int, textview: TextView) {
    val valueAnimator = ValueAnimator.ofInt(initialValue, finalValue)
    valueAnimator.duration = 1500
    valueAnimator.addUpdateListener {
        textview.text = valueAnimator.animatedValue.toString()+"+"
    }
    valueAnimator.start()
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.colorFilter(color :Int){
    setColorFilter(ContextCompat.getColor(context,color))
}

fun getColorAcStatus(status:Int):Int{
    return when(status){
        1->R.color.colorPrimary
        2->R.color.Green
        3->R.color.Red
        4->R.color.Yellow
        5->R.color.Orange
        6->R.color.Olive
        else->R.color.Green

    }

}

fun getGreetings():String{
    val date = Date()
    val cal: Calendar = Calendar.getInstance()
    cal.time = date
    val hour: Int = cal.get(Calendar.HOUR_OF_DAY)

    //Set greeting

    //Set greeting
    var greeting: String? = null
    if(hour>= 12 && hour < 17){
        greeting = "Good Afternoon";
    } else if(hour >= 17 && hour < 21){
        greeting = "Good Evening";
    } else if(hour >= 21 && hour < 24){
        greeting = "Good Night";
    } else {
        greeting = "Good Morning";
    }
    return greeting
}