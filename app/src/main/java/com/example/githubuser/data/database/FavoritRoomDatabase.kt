package com.example.githubuser.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoritRoomDatabase : RoomDatabase() {

    abstract fun FavoriteDAO(): FavoriteDAO

    companion object {
        @Volatile
        private var INSTANCE: FavoritRoomDatabase? = null

        fun getInstance(context: Context): FavoritRoomDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, FavoritRoomDatabase::class.java, "fav_database"
                ).build()
            }
    }
}
