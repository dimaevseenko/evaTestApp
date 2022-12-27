package ua.dimaevseenko.evatestapp.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ua.dimaevseenko.evatestapp.db.PhotoDB
import ua.dimaevseenko.evatestapp.db.PhotoDBTypeConverter
import ua.dimaevseenko.evatestapp.network.request.RPhoto

@Module(includes = [RetrofitModule::class, NetworkModule::class, DBModule::class])
object AppModule

@Module
object DBModule{

    @Provides
    fun providePhotoDB(context: Context): PhotoDB{
        return Room.databaseBuilder(context, PhotoDB::class.java, "photoDB")
            .addTypeConverter(PhotoDBTypeConverter()).build()
    }
}

@Module
object NetworkModule{

    @Provides
    fun provideRPhoto(retrofit: Retrofit): RPhoto{
        return retrofit
            .create(RPhoto::class.java)
    }
}

@Module
object RetrofitModule{

    @Provides
    fun provideRetrofit(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}