package ua.dimaevseenko.evatestapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ua.dimaevseenko.evatestapp.db.dao.PhotoDao
import ua.dimaevseenko.evatestapp.model.Photo

@Database(entities = [Photo::class], version = 2)
@TypeConverters(PhotoDBTypeConverter::class)
abstract class PhotoDB: RoomDatabase() {
    abstract fun getPhotoDao(): PhotoDao
}