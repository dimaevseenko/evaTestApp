package ua.dimaevseenko.evatestapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.evatestapp.model.Photos
import ua.dimaevseenko.evatestapp.model.PhotosRequest
import ua.dimaevseenko.evatestapp.network.request.RPhoto
import javax.inject.Inject

class PhotosViewModel @Inject constructor(): ViewModel(){

    @Inject
    lateinit var rPhoto: RPhoto

    private var photos: Photos? = null

    var listener: Listener? = null

    fun getPhotos(): Photos?{
        return if(photos == null) {
            loadPhotos()
            null
        }else
            photos!!
    }

    fun searchPhotos(query: String){
        rPhoto.getPhotosByKeyword(query).enqueue(PhotosRequestCallback())
    }

    private fun loadPhotos(){
        rPhoto.getRandomPhotos(30).enqueue(PhotosCallback())
    }

    private inner class PhotosRequestCallback: Callback<PhotosRequest>{
        override fun onResponse(call: Call<PhotosRequest>, response: Response<PhotosRequest>) {
            response.body()?.results?.let { listener?.photosSearched(it) }
        }

        override fun onFailure(call: Call<PhotosRequest>, t: Throwable) {
            t.printStackTrace()
        }
    }

    private inner class PhotosCallback: Callback<Photos>{
        override fun onResponse(call: Call<Photos>, response: Response<Photos>) {
            response.body()?.let {
                this@PhotosViewModel.photos = it
                listener?.photosLoaded(it)
            }
        }

        override fun onFailure(call: Call<Photos>, t: Throwable) {
            t.printStackTrace()
        }
    }

    interface Listener{
        fun photosLoaded(photos: Photos)
        fun photosSearched(photos: Photos)
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory{
        @Inject
        lateinit var photosViewModel: PhotosViewModel

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return photosViewModel as T
        }
    }
}