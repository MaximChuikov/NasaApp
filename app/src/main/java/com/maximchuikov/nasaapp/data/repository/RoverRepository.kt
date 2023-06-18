package com.maximchuikov.nasaapp.data.repository

import com.maximchuikov.nasaapp.data.model.RoverModel
import com.maximchuikov.nasaapp.data.remote.NetworkResult
import kotlinx.coroutines.flow.Flow

interface RoverRepository {
    suspend fun fetchCuriosity(): Flow<NetworkResult<RoverModel>>
    suspend fun fetchOpportunity(): Flow<NetworkResult<RoverModel>>
    suspend fun fetchSpirit(): Flow<NetworkResult<RoverModel>>
}