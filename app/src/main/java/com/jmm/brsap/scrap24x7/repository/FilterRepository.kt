package com.jmm.brsap.scrap24x7.repository

import com.jmm.brsap.scrap24x7.model.FilterModel
import com.jmm.brsap.scrap24x7.model.network_models.LocationMaster
import com.jmm.brsap.scrap24x7.util.FilterEnum
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow


/*

Author : Praveen A. Yadav
Created On : 10:48 31-05-2021

*/

class FilterRepository {
    val selectedLocationsID = mutableListOf<Int>()
    var selectedTimeFilter = FilterEnum.TODAY
    var timeFilter = mutableListOf<FilterModel>()

    fun setSelectedTimeFilter(id: Int){
        for (filter in timeFilter){
            if (filter.id == id){
                selectedTimeFilter = filter.filterId!!
            }
        }

    }

    fun addLocationIdFilter(id:Int){
        if (!selectedLocationsID.contains(id)){
            selectedLocationsID.add(id)
        }
    }
    fun  removeLocationFilter(id:Int){
        selectedLocationsID.remove(id)
    }

    fun getTimeFilter(): Flow<MutableList<FilterModel>> = flow {
        emit(timeFilter)
    }

    fun populateTimeFilter(){
        timeFilter.clear()
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.TODAY,1,"Today"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.YESTERDAY,2,"Yesterday"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.LAST_WEEK,3,"Last week"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.LAST_MONTH,4,"Last Month"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.CUSTOM,5,"Custom"))
    }

    fun getTimeFilters() :List<FilterModel>{
        val timeFilter = mutableListOf<FilterModel>()
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.LAST_MONTH,1,"Last Month"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.LAST_WEEK,2,"Last week"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.YESTERDAY,3,"Yesterday"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.TODAY,4,"Today"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.TOMORROW,5,"Tomorrow"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.THIS_WEEK,6,"This Week"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.THIS_MONTH,7,"This Month"))
        timeFilter.add(FilterModel(FilterEnum.TIME_FILTER,FilterEnum.CUSTOM,8,"Custom"))
        return timeFilter
    }



}