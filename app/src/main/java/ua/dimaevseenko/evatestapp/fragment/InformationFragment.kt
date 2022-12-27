package ua.dimaevseenko.evatestapp.fragment

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ua.dimaevseenko.evatestapp.R
import ua.dimaevseenko.evatestapp.appComponent
import ua.dimaevseenko.evatestapp.databinding.FragmentInfoBinding
import ua.dimaevseenko.evatestapp.db.PhotoDB
import ua.dimaevseenko.evatestapp.model.Photo
import java.util.Date
import javax.inject.Inject

class InformationFragment @Inject constructor(): Fragment() {

    companion object{
        const val TAG = "InformationFragment"
    }

    private lateinit var photo: Photo

    private lateinit var binding: FragmentInfoBinding

    @Inject
    lateinit var photoDB: PhotoDB

    private var favorite = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInfoBinding.bind(inflater.inflate(R.layout.fragment_info, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<Photo>("photo")?.let { photo = it }

        initView()
    }

    private fun initView(){
        Picasso.get().load(photo.urls!!.regular).into(binding.infoImageView)
        binding.textViewAuthor.text = "${getString(R.string.author)} ${photo.user!!.name}"
        binding.textViewCreatedDate.text = "${getString(R.string.created_ad)} ${photo.createdAt}"
        binding.textViewLikes.text = "${getString(R.string.likes)} ${photo.likes}"

        binding.buttonShare.setOnClickListener { sharePhoto() }
        binding.buttonFavorite.setOnClickListener {
            CoroutineScope(Dispatchers.Default).launch {
                if(favorite)
                    photoDB.getPhotoDao().deletePhoto(photo.id!!)
                else
                    photoDB.getPhotoDao().insertPhoto(photo)
                checkFavorite()
            }
        }

        checkFavorite()
    }

    private fun checkFavorite(){
        CoroutineScope(Dispatchers.Default).launch {
            if(photoDB.getPhotoDao().getPhotoById(photo.id!!) == null){
                binding.buttonFavorite.text = getString(R.string.add_to_favorite)
                favorite = false
            }else{
                binding.buttonFavorite.text = getString(R.string.delete_from_favorite)
                favorite = true
            }
        }
    }

    private fun sharePhoto(){
        val path = MediaStore.Images.Media.insertImage(requireContext().contentResolver, binding.infoImageView.drawToBitmap(), photo.createdAt ?:Date().toString(), photo.descriptions?:Date().toString())
        val i = Intent(Intent.ACTION_SEND)
        i.type = "image/*"
        i.putExtra(Intent.EXTRA_STREAM, Uri.parse(path))
        startActivity(Intent.createChooser(i, "Send Photo"))
    }

    override fun onDestroy() {
        super.onDestroy()
        photoDB.close()
    }
}