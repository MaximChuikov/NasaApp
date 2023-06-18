package com.maximchuikov.nasaapp.data.model


import com.google.gson.annotations.SerializedName

data class RoverModel(
    @SerializedName("photos")
    val photos: List<Photo>
)