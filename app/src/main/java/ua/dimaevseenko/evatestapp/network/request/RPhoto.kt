package ua.dimaevseenko.evatestapp.network.request

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ua.dimaevseenko.evatestapp.Config
import ua.dimaevseenko.evatestapp.model.Photos
import ua.dimaevseenko.evatestapp.model.PhotosRequest

interface RPhoto {

    @GET("/photos/random")
    fun getRandomPhotos(
        @Query("count") count: Int,
        @Query("client_id") clientId: String = Config.accessKey
    ): Call<Photos>

    @GET("/search/photos")
    fun getPhotosByKeyword(
        @Query("query") query: String,
        @Query("client_id") clientId: String = Config.accessKey
    ): Call<PhotosRequest>
}