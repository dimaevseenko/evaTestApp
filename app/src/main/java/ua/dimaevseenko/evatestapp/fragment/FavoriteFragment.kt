package ua.dimaevseenko.evatestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.dimaevseenko.evatestapp.R
import ua.dimaevseenko.evatestapp.appComponent
import ua.dimaevseenko.evatestapp.databinding.FragmentFavoriteBinding
import ua.dimaevseenko.evatestapp.db.PhotoDB
import ua.dimaevseenko.evatestapp.model.Photo
import ua.dimaevseenko.evatestapp.model.Photos
import javax.inject.Inject

class FavoriteFragment @Inject constructor(): Fragment(), PhotosRecyclerAdapter.Listener {

    companion object{
        const val TAG = "FavoriteFragment"
    }

    private lateinit var binding: FragmentFavoriteBinding

    @Inject
    lateinit var photosRecyclerAdapterFactory: PhotosRecyclerAdapter.Factory

    @Inject
    lateinit var photoDB: PhotoDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoriteBinding.bind(inflater.inflate(R.layout.fragment_favorite, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val photos = Photos()

        CoroutineScope(Dispatchers.Default).launch {
            photoDB.getPhotoDao().getPhotos().forEach {
                photos.add(it)
            }
            launch(Dispatchers.Main) {
                initRecycler(photos)
            }
        }
    }

    private fun initRecycler(photos: Photos){
        binding.favRecyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.favRecyclerView.adapter = photosRecyclerAdapterFactory.createAdapter(photos).apply {
            listener = this@FavoriteFragment
        }
    }

    override fun onPhotoClick(photo: Photo) {
        (parentFragment as MainFragment).openPhoto(photo)
    }

    override fun onDestroy() {
        super.onDestroy()
        photoDB.close()
    }
}