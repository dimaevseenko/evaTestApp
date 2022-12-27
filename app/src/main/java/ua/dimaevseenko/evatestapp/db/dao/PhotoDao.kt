package ua.dimaevseenko.evatestapp.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ua.dimaevseenko.evatestapp.model.Photo

@Dao
interface PhotoDao {

    @Insert
    fun insertPhoto(photo: Photo)

    @Query("select * from photos")
    fun getPhotos(): List<Photo>

    @Query("delete from photos where id = :photoId")
    fun deletePhoto(photoId: String): Int

    @Query("select * from photos where id = :photoId")
    fun getPhotoById(photoId: String): Photo?
}