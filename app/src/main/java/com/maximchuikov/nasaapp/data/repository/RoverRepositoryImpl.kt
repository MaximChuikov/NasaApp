package com.maximchuikov.nasaapp.data.repository

import com.maximchuikov.nasaapp.data.datasource.RemoteRoverDataSource
import com.maximchuikov.nasaapp.data.model.RoverModel
import com.maximchuikov.nasaapp.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RoverRepositoryImpl @Inject constructor(private val remoteRoverDataSource: RemoteRoverDataSource) :
    RoverRepository {
    override suspend fun fetchCuriosity(): Flow<NetworkResult<RoverModel>> =
        remoteRoverDataSource.fetchCuriosity()


    override suspend fun fetchOpportunity(): Flow<NetworkResult<RoverModel>> =
        remoteRoverDataSource.fetchOpportunity()

    override suspend fun fetchSpirit(): Flow<NetworkResult<RoverModel>> =
        remoteRoverDataSource.fetchSpirit()
}
