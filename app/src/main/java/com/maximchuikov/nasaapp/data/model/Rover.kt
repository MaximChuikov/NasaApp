package com.maximchuikov.nasaapp.data.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Rover(
    @SerializedName("id")
    val id: Int,
    @SerializedName("landing_date")
    val landingDate: String,
    @SerializedName("launch_date")
    val launchDate: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("status")
    val status: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(landingDate)
        parcel.writeString(launchDate)
        parcel.writeString(name)
        parcel.writeString(status)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Rover> {
        override fun createFromParcel(parcel: Parcel): Rover {
            return Rover(parcel)
        }

        override fun newArray(size: Int): Array<Rover?> {
            return arrayOfNulls(size)
        }
    }
}