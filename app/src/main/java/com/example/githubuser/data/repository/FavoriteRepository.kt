package com.example.githubuser.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.githubuser.data.database.FavoritRoomDatabase
import com.example.githubuser.data.database.FavoriteDAO
import com.example.githubuser.data.database.FavoriteUser
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(context: Application) {
    private val mfavoritesDao: FavoriteDAO
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = FavoritRoomDatabase.getInstance(context.applicationContext)
        mfavoritesDao = db.FavoriteDAO()
    }

    fun getAllFavorites(): LiveData<List<FavoriteUser>> {
        return mfavoritesDao.getAllFavorites()
    }

    fun insert(user: FavoriteUser, favorite: Boolean) {
        executorService.execute {
            user.favorite = favorite
            mfavoritesDao.insert(user)
        }
    }

    fun delete(user: FavoriteUser, favorite: Boolean) {
        executorService.execute {
            user.favorite = favorite
            mfavoritesDao.delete(user)
            Log.d("Repository", "Delete: ${user.login}")
        }
    }

    fun getFavoriteUser(username: String): LiveData<FavoriteUser> {
        return mfavoritesDao.getAllFavoritesUser(username)
    }
}
