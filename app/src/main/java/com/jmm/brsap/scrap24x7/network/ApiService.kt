package com.jmm.brsap.scrap24x7.network

import com.jmm.brsap.scrap24x7.model.ApiRequestModel
import com.jmm.brsap.scrap24x7.model.network_models.*
import retrofit2.http.*
import java.sql.Date

interface ApiService {

    // Authentication
    @GET("check_staff_login")
    suspend fun checkStaffLogin(
        @Query("user_name") userName:String,
        @Query("password") password:String
    ): ApiResponse


    //Address Type
    @GET("address_type")
    suspend fun getAddressType(): ApiResponse

    @POST("address_type")
    suspend fun addNewAddressType(
        @Body addressType: AddressType
    ): ApiResponse

    @GET("address_type/{id}")
    suspend fun getAddressTypeById(
        @Path("id") typeId: Int
    ): ApiResponse

    @PUT("address_type/{id}")
    suspend fun updateAddressType(
        @Body addressType: AddressType
    ): ApiResponse

    @DELETE("address_type/{id}")
    suspend fun deleteAddressType(): ApiResponse


    //Category
    @GET("category_item")
    suspend fun getAllScrapCategoryItemList(): List<CategoryItem>

    @GET("category_item/{id}")
    suspend fun getScrapCategoryItems(
            @Path("id") categoryId: Int
    ): List<CategoryItem>

    //Category
    @GET("category")
    suspend fun getScrapCategoryList(): List<Category>

    //Customer
    @GET("customers")
    suspend fun getCustomerList(
        @Query("per_page") itemsPerPage:Int,
        @Query("page_number") pageNumber:Int,
    ): ApiResponse

    @POST("customer")
    suspend fun registerNewCustomer(
            @Body customer: Customer
    ): ApiResponse

    @GET("customer/{customer_id}")
    suspend fun getCustomerById(
            @Path("customer_id") customer_id: Int
    ): ApiResponse

    @PUT("customer")
    suspend fun updateCustomerInfo(
            @Body customer: Customer
    ): ApiResponse

    @GET("getCustomerByUserId/{user_id}")
    suspend fun getCustomerByUserId(
            @Path("user_id") user_id: String
    ): ApiResponse

    // Category
    @GET("category")
    suspend fun getCategories(): ApiResponse

    @POST("category")
    suspend fun addNewCategory(
        @Body category: Category
    ): ApiResponse

    @POST("new_category/{user_id}")
    suspend fun addNewCategory(
        @Path("user_id") userId:Int,
        @Body category: Category
    ): ApiResponse


    @GET("category/{id}")
    suspend fun getCategoryById(
        @Path("id") categoryId: Int
    ): ApiResponse

    @PUT("category")
    suspend fun updateCategory(
        @Body category: Category
    ): ApiResponse

    @DELETE("category/{id}")
    suspend fun deleteCategory(
        @Path("id") categoryId: Int
    ): ApiResponse


    //Category Item
    @GET("category_item")
    suspend fun getCategoryItems(): ApiResponse

    @POST("category_item")
    suspend fun addNewCategoryItem(
        @Body categoryItem: CategoryItem
    ): ApiResponse

    @GET("category_item/{id}")
    suspend fun getCategoryItemById(
        @Path("id") categoryItemId: Int
    ): ApiResponse

    @PUT("category_item/{id}")
    suspend fun updateCategoryItem(
        @Path("id") categoryItemId: Int,
        @Body categoryItem: CategoryItem
    ): ApiResponse

    @DELETE("category_item/{id}")
    suspend fun deleteCategoryItem(
        @Path("id") categoryItemId: Int
    ): ApiResponse


    // Dashboard

    @GET("dashboard/component_count")
    suspend fun getSystemComponentsStatistics(): ApiResponse

    @GET("dashboard/pickup_request_summary")
    suspend fun getPickupRequestsCount(
        @Query("pickup_date") pickupDate:Date
    ): ApiResponse

    @GET("dashboard/executive_pickup_summary")
    suspend fun getExecutivePickupSummary(
        @Query("from") from:String,
        @Query("to") to:String,
        @Query("executive_id") executiveId:Int
    ): ApiResponse


    //Customer Address
    @GET("customer_address")
    suspend fun getCustomerAddressList(): ApiResponse

    @POST("customer_address")
    suspend fun addNewCustomerAddress(
            @Body customerAddress: CustomerAddress
    ): ApiResponse

    @GET("customer_address/{id}")
    suspend fun getCustomerAddressById(
            @Path("id") customerAddressId: Int
    ): ApiResponse

    @PUT("customer_address/{id}")
    suspend fun updateCustomerAddress(
            @Body customerAddress: CustomerAddress
    ): ApiResponse

    @DELETE("customer_address/{id}")
    suspend fun deleteCustomerAddress(
            @Path("id") customerAddressId: Int
    ): ApiResponse

    @GET("address_by_customer_id/{customer_id}")
    suspend fun getAddressByCustomerId(
            @Path("customer_id") customerId: Int
    ): ApiResponse




    //Location_Master
    @GET("location_master")
    suspend fun getLocationMasters(): ApiResponse

    @POST("location_master")
    suspend fun addNewLocationMaster(
        @Body locationMaster: LocationMaster
    ): ApiResponse

    @GET("location_master/{id}")
    suspend fun getLocationMasterById(
        @Path("id") locationMasterId: Int
    ): ApiResponse

    @PUT("location_master")
    suspend fun updateLocationMaster(
        @Body locationMaster: LocationMaster
    ): ApiResponse

    @DELETE("location_master/{id}")
    suspend fun deleteLocationMaster(
        @Path("id") locationMasterId: Int
    ): ApiResponse

    //Driver Master
    @GET("driver_master")
    suspend fun getDriverMasters(
        @Query("per_page") itemsPerPage:Int,
        @Query("page_number") pageNumber:Int,
    ): ApiResponse

    @POST("driver_master")
    suspend fun addNewDriverMaster(
        @Body driverMaster: DriverMaster
    ): ApiResponse

    @GET("driver_master/{id}")
    suspend fun getDriverMasterById(
        @Path("id") driverMasterId: Int
    ): ApiResponse

    @PUT("driver_master/{id}")
    suspend fun updateDriverMaster(
        @Path("id") driverMasterId:Int,
        @Body driverMaster: DriverMaster
    ): ApiResponse

    @DELETE("driver_master/{id}")
    suspend fun deleteDriverMaster(
        @Path("id") driverMasterId: Int
    ): ApiResponse

    @GET("driver_master_by_location_id")
    suspend fun getDriverMastersByLocationId(
        @Query("location_id") location_id: Int
    ): ApiResponse


    //Executive Master
    @GET("executive_master")
    suspend fun getExecutiveMasters(
        @Query("per_page") itemsPerPage:Int,
        @Query("page_number") pageNumber:Int,
    ): ApiResponse

    @POST("executive_master")
    suspend fun addNewExecutiveMaster(
        @Body executiveMaster: ExecutiveMaster
    ): ApiResponse

    @GET("executive_master/{id}")
    suspend fun getExecutiveMasterById(
        @Path("id") executiveMasterId: Int
    ): ApiResponse

    @PUT("executive_master/{id}")
    suspend fun updateExecutiveMaster(
        @Path("id") executiveMasterId:Int,
        @Body executiveMaster: ExecutiveMaster
    ): ApiResponse

    @DELETE("executive_master/{id}")
    suspend fun deleteExecutiveMaster(
        @Path("id") executiveMasterId: Int
    ): ApiResponse

    @GET("executive_master_by_location_id")
    suspend fun getExecutiveMastersByLocationId(
        @Query("location_id") location_id: Int
    ): ApiResponse


    //Pickup Request
    @GET("pickup_request")
    suspend fun getPickupRequest(): ApiResponse

    @POST("pickup_request")
    suspend fun addNewPickupRequest(
        @Body pickupRequest: PickupRequest
    ): ApiResponse

    @GET("pickup_request/{id}")
    suspend fun getPickupRequestById(
        @Path("id") id: Int
    ): ApiResponse

    @GET("pickup_request_detail/{id}")
    suspend fun getPickupRequestDetailById(
        @Path("id") id: Int
    ): ApiResponse

    @PUT("pickup_request/{id}")
    suspend fun updatePickupRequest(
        @Body pickupRequest: PickupRequest
    ): ApiResponse

    @DELETE("pickup_request/{id}")
    suspend fun deletePickupRequest(): ApiResponse

    @POST("raise_pickup_request")
    suspend fun raisePickupRequest(
        @Body pickupRequest: PickupRequest
    ): ApiResponse

    @GET("pickup_request")
    suspend fun getPickupRequestByCustomerID(
        @Query("customer_id") customerId: Int
    ): ApiResponse

    @POST("pickup_request_list")
    suspend fun getPickupRequestList(
        @Body requestModel : ApiRequestModel
    ): ApiResponse

    @PUT("update_pickup_status")
    suspend fun updatePickupStatus(
        @Query("pickup_id") pickupId:Int,
        @Query("status") status:Int
    ): ApiResponse

    @PUT("accept_pickup_request")
    suspend fun acceptPickupRequest(
        @Query("pickup_id") pickupId:String,
        @Query("driver_id") driverId:Int,
        @Query("executive_id") executiveId:Int,
        @Query("vehicle_id") vehicleId:Int
    ): ApiResponse

    @POST("executive_pickup_list")
    suspend fun getPickupRequestAcExecutive(
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("executive_id") executiveId: Int
    ): ApiResponse

    //Pickup Request Item

    @GET("pickup_request_item")
    suspend fun getPickupRequestItem(): ApiResponse

    @POST("pickup_request_item")
    suspend fun addNewPickupRequestItem(
            @Body pickupRequestItem: PickupRequestItem
    ): ApiResponse

    @GET("pickup_request_item/{id}")
    suspend fun getPickupRequestItemById(
            @Path("id") id: Int
    ): ApiResponse

    @PUT("pickup_request_item/{id}")
    suspend fun updatePickupRequestItem(
            @Body pickupRequestItem: PickupRequestItem
    ): ApiResponse

    @DELETE("pickup_request_item/{id}")
    suspend fun deletePickupRequestItem(): ApiResponse

    @POST("collect_pickup_items/{pickup_id}")
    suspend fun collectPickupItems(
        @Path("pickup_id") pickupId:String,
        @Body pickup_request_items: List<PickupRequestItem>
    ): ApiResponse

    //Unit
    @GET("unit")
    suspend fun getUnits(): ApiResponse

    @POST("unit")
    suspend fun addNewUnit(
        @Body unitModel: UnitModel
    ): ApiResponse

    @GET("unit/{id}")
    suspend fun getUnitById(
        @Path("id") unitId: Int
    ): ApiResponse

    @PUT("unit/{id}")
    suspend fun updateUnit(
        @Path("id") unitId: Int,
        @Body unitModel: UnitModel
    ): ApiResponse

    @DELETE("unit/{id}")
    suspend fun deleteUnit(
        @Path("id") unitId: Int
    ): ApiResponse


    //Vehicle
    @GET("vehicle_master")
    suspend fun getVehicleMasters(
        @Query("per_page") itemsPerPage:Int,
        @Query("page_number") pageNumber:Int,
    ): ApiResponse


    @POST("vehicle_master")
    suspend fun addNewVehicleMaster(
        @Body vehicleMaster: VehicleMaster
    ): ApiResponse

    @GET("vehicle_master/{id}")
    suspend fun getVehicleMasterById(
        @Path("id") vehicleMasterId: Int
    ): ApiResponse

    @PUT("vehicle_master/{id}")
    suspend fun updateVehicleMaster(
        @Path("id") vehicleID:Int,
        @Body vehicleMaster: VehicleMaster
    ): ApiResponse

    @DELETE("vehicle_master/{id}")
    suspend fun deleteVehicleMaster(
        @Path("id") vehicleMasterId: Int
    ): ApiResponse

    @GET("vehicle_master_by_location_id")
    suspend fun getVehicleMastersByLocationId(
        @Query("location_id") location_id: Int
    ): ApiResponse


    //WareHouse
    @GET("warehouse")
    suspend fun getWarehouses(): ApiResponse

    @POST("warehouse")
    suspend fun addNewWarehouse(
        @Body warehouse: Warehouse
    ): ApiResponse

    @GET("warehouse/{id}")
    suspend fun getWarehouseById(
        @Path("id") warehouseId: Int
    ): ApiResponse

    @PUT("warehouse/{id}")
    suspend fun updateWarehouse(
        @Path("id") warehouseId: Int,
        @Body warehouse: Warehouse
    ): ApiResponse

    @DELETE("warehouse/{id}")
    suspend fun deleteWarehouse(
        @Path("id") warehouseId: Int
    ): ApiResponse


}
