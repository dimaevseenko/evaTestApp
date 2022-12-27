package ua.dimaevseenko.evatestapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey(autoGenerate = true)
    val photoId: Int?,
    val id: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    val downloads: Int?,
    val likes: Int?,
    val descriptions: String?,
    val urls: Urls?,
    val user: User?
): Parcelable{

    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readParcelable(Urls::class.java.classLoader),
        parcel.readParcelable(User::class.java.classLoader)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(photoId)
        parcel.writeString(id)
        parcel.writeString(createdAt)
        parcel.writeValue(downloads)
        parcel.writeValue(likes)
        parcel.writeString(descriptions)
        parcel.writeParcelable(urls, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
        parcel.writeParcelable(user, Parcelable.PARCELABLE_WRITE_RETURN_VALUE)
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

    data class Urls(
        val regular: String?,
        val small: String?
    ): Parcelable {
        constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()
        )

        override fun writeToParcel(parcel: Parcel, flags: Int) {
            parcel.writeString(regular)
            parcel.writeString(small)
        }

        override fun describeContents(): Int {
            return 0
        }

        companion object CREATOR : Parcelable.Creator<Urls> {
            override fun createFromParcel(parcel: Parcel): Urls {
                return Urls(parcel)
            }

            override fun newArray(size: Int): Array<Urls?> {
                return arrayOfNulls(size)
            }
        }
    }
}

class Photos: ArrayList<Photo>()