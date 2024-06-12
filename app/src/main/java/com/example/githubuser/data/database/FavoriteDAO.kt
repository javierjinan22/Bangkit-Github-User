package com.example.githubuser.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: FavoriteUser)
    @Update
    fun update(user: FavoriteUser)
    @Delete
    fun delete(user: FavoriteUser)

    @Query("SELECT * from favoriteuser  ORDER BY id ASC")
    fun getAllFavorites(): LiveData<List<FavoriteUser>>

    @Query("SELECT * from favoriteuser  WHERE login = :username")
    fun getAllFavoritesUser(username: String): LiveData<FavoriteUser>

}