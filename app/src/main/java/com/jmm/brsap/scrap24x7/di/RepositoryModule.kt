package com.jmm.brsap.scrap24x7.di

import android.content.Context
import com.jmm.brsap.scrap24x7.network.ApiService
import com.jmm.brsap.scrap24x7.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideAuthRepository(apiService: ApiService): AuthRepository {
        return AuthRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideCustomerAddressRepository(apiService: ApiService): CustomerAddressRepository {
        return CustomerAddressRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideCustomerRepository(apiService: ApiService): CustomerRepository {
        return CustomerRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideDashboardRepository(apiService: ApiService): DashboardRepository {
        return DashboardRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideExecutiveRepository(apiService: ApiService): ExecutiveRepository {
        return ExecutiveRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideFilterRepository(): FilterRepository {
        return FilterRepository()
    }

    @Singleton
    @Provides
    fun provideLocationRepository(apiService: ApiService): LocationRepository {
        return LocationRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideManageScrapRepository(apiService: ApiService): ManageScrapRepository {
        return ManageScrapRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideManageUserRepository(apiService: ApiService): ManageUserRepository {
        return ManageUserRepository(apiService)
    }

    @Singleton
    @Provides
    fun providePickupRequestRepository(apiService: ApiService): PickupRequestRepository {
        return PickupRequestRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideUserPreferencesRepository(@ApplicationContext context: Context): UserPreferencesRepository {
        return UserPreferencesRepository(context)
    }

    @Singleton
    @Provides
    fun provideVehicleRepository(apiService: ApiService): VehicleRepository {
        return VehicleRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideWarehouseRepository(apiService: ApiService): WarehouseRepository {
        return WarehouseRepository(apiService)
    }


}