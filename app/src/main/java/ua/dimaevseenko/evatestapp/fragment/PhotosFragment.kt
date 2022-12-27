package ua.dimaevseenko.evatestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import ua.dimaevseenko.evatestapp.R
import ua.dimaevseenko.evatestapp.appComponent
import ua.dimaevseenko.evatestapp.databinding.FragmentPhotosBinding
import ua.dimaevseenko.evatestapp.model.Photo
import ua.dimaevseenko.evatestapp.model.Photos
import ua.dimaevseenko.evatestapp.viewmodel.PhotosViewModel
import javax.inject.Inject

class PhotosFragment @Inject constructor(): Fragment(), PhotosViewModel.Listener, PhotosRecyclerAdapter.Listener {

    companion object{
        const val TAG = "PhotosFragment"
    }

    private lateinit var binding: FragmentPhotosBinding

    @Inject
    lateinit var adapterFactory: PhotosRecyclerAdapter.Factory

    @Inject
    lateinit var photosViewModelFactory: PhotosViewModel.Factory

    private lateinit var photosViewModel: PhotosViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPhotosBinding.bind(inflater.inflate(R.layout.fragment_photos, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        photosViewModel = ViewModelProvider(requireParentFragment(), photosViewModelFactory)[PhotosViewModel::class.java]
        photosViewModel.getPhotos()?.let { initRecycler(it) }
        binding.searchEditText.addTextChangedListener { it ->
            it?.toString()?.let { photosViewModel.searchPhotos(it) }
        }
    }

    private fun initRecycler(photos: Photos){
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = adapterFactory.createAdapter(photos).apply {
            listener = this@PhotosFragment
        }
    }

    override fun photosLoaded(photos: Photos) {
        initRecycler(photos)
    }

    override fun photosSearched(photos: Photos) {
        (binding.recyclerView.adapter as PhotosRecyclerAdapter).apply {
            this.photos = photos
            notifyDataSetChanged()
        }
    }

    override fun onPhotoClick(photo: Photo) {
        (parentFragment as MainFragment).openPhoto(photo)
    }

    override fun onResume() {
        super.onResume()
        photosViewModel.listener = this
    }

    override fun onPause() {
        super.onPause()
        photosViewModel.listener = null
    }
}