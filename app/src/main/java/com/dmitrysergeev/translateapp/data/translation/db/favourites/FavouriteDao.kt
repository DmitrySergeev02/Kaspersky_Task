package com.dmitrysergeev.translateapp.data.translation.db.favourites

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteDao {

    @Query("SELECT * FROM favourites")
    fun getFavourites(): Flow<List<FavouriteDbEntity>>

    @Insert
    suspend fun addFavourite(favouriteDbEntity: FavouriteDbEntity)

    @Delete
    suspend fun deleteFavourite(favouriteDbEntity: FavouriteDbEntity)
}