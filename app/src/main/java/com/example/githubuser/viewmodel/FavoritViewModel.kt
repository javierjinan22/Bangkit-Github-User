package com.example.githubuser.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.githubuser.data.database.FavoritRoomDatabase

import com.example.githubuser.data.database.FavoriteUser
import com.example.githubuser.data.repository.FavoriteRepository

class FavoritViewModel(application: Application) : AndroidViewModel(application) {
    private val favoriteRepository: FavoriteRepository =
        FavoriteRepository(application)

    val favoriteUsers: LiveData<List<FavoriteUser>> = favoriteRepository.getAllFavorites()

    fun insert(user: FavoriteUser) {
        favoriteRepository.insert(user, true)
    }

    fun delete(user: FavoriteUser) {
        favoriteRepository.delete(user, true)
    }

    fun getFavoriteUser(username: String): LiveData<FavoriteUser> {
        return favoriteRepository.getFavoriteUser(username)
    }
}
