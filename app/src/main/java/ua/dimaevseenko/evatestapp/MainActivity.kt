package ua.dimaevseenko.evatestapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ua.dimaevseenko.evatestapp.databinding.ActivityMainBinding
import ua.dimaevseenko.evatestapp.fragment.MainFragment
import ua.dimaevseenko.evatestapp.model.Photos
import ua.dimaevseenko.evatestapp.network.request.RPhoto
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.bind(layoutInflater.inflate(R.layout.activity_main, null, false))
    }

    @Inject
    lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)
        setContentView(binding.root)

        if(savedInstanceState == null)
            supportFragmentManager.beginTransaction().add(R.id.mainContainer, mainFragment, MainFragment.TAG)
                .commit()
        else
            supportFragmentManager.findFragmentByTag(MainFragment.TAG)?.let { mainFragment = it as MainFragment }
    }

    override fun onBackPressed() {
        if(!mainFragment.onBackPressed())
            super.onBackPressed()
    }
}