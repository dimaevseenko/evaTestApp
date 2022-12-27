package ua.dimaevseenko.evatestapp.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import ua.dimaevseenko.evatestapp.R
import ua.dimaevseenko.evatestapp.appComponent
import ua.dimaevseenko.evatestapp.databinding.FragmentMainBinding
import ua.dimaevseenko.evatestapp.model.Photo
import javax.inject.Inject

class MainFragment @Inject constructor(): Fragment(){

    companion object{
        const val TAG = "MainFragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
    }

    private lateinit var binding: FragmentMainBinding

    @Inject
    lateinit var photosFragment: PhotosFragment

    @Inject
    lateinit var favoriteFragment: FavoriteFragment

    @Inject
    lateinit var informationFragment: InformationFragment

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navItemSearch -> {
                    childFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, photosFragment, PhotosFragment.TAG).commit()
                    return@setOnItemSelectedListener true
                }
                R.id.navItemFavorite -> {
                    childFragmentManager.beginTransaction().replace(R.id.mainFragmentContainer, favoriteFragment, FavoriteFragment.TAG).commit()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }

        if(savedInstanceState == null)
            childFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, photosFragment, PhotosFragment.TAG).commit()
    }

    fun openPhoto(photo: Photo){
        informationFragment = InformationFragment()
        informationFragment.arguments = Bundle().apply {
            putParcelable("photo", photo)
        }
        childFragmentManager.beginTransaction().add(R.id.mainFragmentContainer, informationFragment, InformationFragment.TAG).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit()
    }

    fun onBackPressed(): Boolean{
        childFragmentManager.findFragmentByTag(InformationFragment.TAG)?.let {
            childFragmentManager.beginTransaction().remove(it).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE).commit()
            return true
        }
        return false
    }
}