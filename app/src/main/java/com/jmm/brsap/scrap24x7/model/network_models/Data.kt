
package com.jmm.brsap.scrap24x7.model.network_models

data class Data(
    val customer: Customer,
    val customers: List<Customer>,

    val categories: List<Category>,
    val category: Category,

    val category_items: List<CategoryItem>,
    val category_item: CategoryItem,

    val units: List<UnitModel>,
    val unit: UnitModel,

    val customer_addresses: List<CustomerAddress>,
    val customer_address: CustomerAddress,

    val address_types: List<AddressType>,
    val address_type: AddressType,

    val driver_masters: List<DriverMaster>,
    val driver_master: DriverMaster,

    val executive_masters: List<ExecutiveMaster>,
    val executive_master: ExecutiveMaster,



    val pickup_requests: List<PickupRequest>,
    val pickup_request: PickupRequest,

    val pickup_request_items: List<PickupRequestItem>,
    val pickup_request_item: PickupRequestItem,

    val location_masters: List<LocationMaster>,
    val location_master: LocationMaster,

    val warehouses: List<Warehouse>,
    val warehouse: Warehouse,

    val user_masters: List<UserMaster>,
    val user_master: UserMaster,

    val vehicle_masters: List<VehicleMaster>,
    val vehicle_master: VehicleMaster,

    val pickup_trackings: List<PickupTracking>,
    val pickup_tracking: PickupTracking,


    // Admin Dashboard Models
    val location_count:Int,
    val vehicle_count:Int,
    val customer_count:Int,
    val warehouse_count :Int,

    val raised_requests_count:Int,
    val requested_pickups:Int,
    val accepted_pickups:Int,
    val rejected_pickups:Int,

    //Executive
    val pickup_count:Int,
    val collected_pickup:Int,

    //Customer
    val customer_requests_count:Int,
    val customer_earning:Double,

    // Pagination
    val total_pages : Int

    )