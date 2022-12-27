package ua.dimaevseenko.evatestapp.db

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.google.gson.Gson
import ua.dimaevseenko.evatestapp.model.Photo
import ua.dimaevseenko.evatestapp.model.User

@ProvidedTypeConverter
class PhotoDBTypeConverter {

    @TypeConverter
    fun urlsToString(urls: Photo.Urls): String{
        return Gson().toJson(urls)
    }

    @TypeConverter
    fun userToString(user: User): String{
        return Gson().toJson(user)
    }

    @TypeConverter
    fun stringToUrls(string: String): Photo.Urls{
        return Gson().fromJson(string, Photo.Urls::class.java)
    }

    @TypeConverter
    fun stringToUser(string: String): User{
        return Gson().fromJson(string, User::class.java)
    }
}