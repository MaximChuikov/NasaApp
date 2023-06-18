package com.maximchuikov.nasaapp.di

import com.maximchuikov.nasaapp.data.datasource.RemoteRoverDataSource
import com.maximchuikov.nasaapp.data.datasource.RemoteRoverDataSourceImpl
import com.maximchuikov.nasaapp.data.remote.NasaApiService
import com.maximchuikov.nasaapp.data.repository.RoverRepository
import com.maximchuikov.nasaapp.data.repository.RoverRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataModule {

    @Provides
    fun providesApiRemoteDataSource(nasaApiService: NasaApiService): RemoteRoverDataSource {
        return RemoteRoverDataSourceImpl(nasaApiService)
    }

    @Provides
    fun providesApiRemoteRepository(remoteRoverDataSource: RemoteRoverDataSource): RoverRepository {
        return RoverRepositoryImpl(remoteRoverDataSource)
    }
}