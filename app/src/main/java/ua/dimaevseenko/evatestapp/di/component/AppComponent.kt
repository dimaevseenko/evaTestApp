package ua.dimaevseenko.evatestapp.di.component

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import ua.dimaevseenko.evatestapp.MainActivity
import ua.dimaevseenko.evatestapp.di.module.AppModule
import ua.dimaevseenko.evatestapp.fragment.FavoriteFragment
import ua.dimaevseenko.evatestapp.fragment.InformationFragment
import ua.dimaevseenko.evatestapp.fragment.MainFragment
import ua.dimaevseenko.evatestapp.fragment.PhotosFragment

@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(mainFragment: MainFragment)
    fun inject(photosFragment: PhotosFragment)
    fun inject(informationFragment: InformationFragment)
    fun inject(favoriteFragment: FavoriteFragment)

    @Component.Builder
    interface Builder{
        @BindsInstance
        fun bindContext(context: Context): Builder
        fun build(): AppComponent
    }
}