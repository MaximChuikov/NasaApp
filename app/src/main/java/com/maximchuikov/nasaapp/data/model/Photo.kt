package com.maximchuikov.nasaapp.data.model


import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Photo(
    @SerializedName("camera")
    val camera: Camera,
    @SerializedName("earth_date")
    val earthDate: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("img_src")
    val imgSrc: String,
    @SerializedName("rover")
    val rover: Rover,
    @SerializedName("sol")
    val sol: Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readParcelable(Camera::class.java.classLoader) ?: Camera("ошибка", 0, "0", 0),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readParcelable(Rover::class.java.classLoader) ?: Rover(
            0,
            "ошибка",
            "0",
            "О",
            "плохой"
        ),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(camera, flags)
        parcel.writeString(earthDate)
        parcel.writeInt(id)
        parcel.writeString(imgSrc)
        parcel.writeParcelable(rover, flags)
        parcel.writeInt(sol)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel)
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }
}