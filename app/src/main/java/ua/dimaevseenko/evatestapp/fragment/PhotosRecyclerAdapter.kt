package ua.dimaevseenko.evatestapp.fragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import ua.dimaevseenko.evatestapp.R
import ua.dimaevseenko.evatestapp.databinding.RecyclerPhotosItemBinding
import ua.dimaevseenko.evatestapp.model.Photo
import ua.dimaevseenko.evatestapp.model.Photos

class PhotosRecyclerAdapter @AssistedInject constructor(
    private val context: Context,
    @Assisted("photos")
    var photos: Photos
): RecyclerView.Adapter<PhotosRecyclerAdapter.ViewHolder>() {

    var listener: Listener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.recycler_photos_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    override fun getItemCount(): Int {
        return photos.size
    }

    interface Listener{
        fun onPhotoClick(photo: Photo)
    }

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        private val binding = RecyclerPhotosItemBinding.bind(view)

        fun bind(photo: Photo){
            binding.photoCardView.setOnClickListener{
                listener?.onPhotoClick(photo)
            }

            photo.urls?.small?.let {
                Picasso.get().load(it).into(binding.imageView)
            }
        }
    }

    @AssistedFactory
    interface Factory{
        fun createAdapter(@Assisted("photos") photos: Photos): PhotosRecyclerAdapter
    }
}