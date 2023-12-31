package com.maximchuikov.nasaapp.data.remote

import com.maximchuikov.nasaapp.data.model.RoverModel
import com.maximchuikov.nasaapp.util.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NasaApiService {

    @GET("rovers/curiosity/photos?sol=2000")
    suspend fun fetchCuriosity(
        @Query("api_key") api_key: String = API_KEY
    ): Response<RoverModel>

    @GET("rovers/opportunity/photos?sol=2000")
    suspend fun fetchOpportunity(
        @Query("api_key") api_key: String = API_KEY
    ): Response<RoverModel>

    @GET("rovers/spirit/photos?sol=1")
    suspend fun fetchSpirit(
        @Query("api_key") api_key: String = API_KEY
    ): Response<RoverModel>
}