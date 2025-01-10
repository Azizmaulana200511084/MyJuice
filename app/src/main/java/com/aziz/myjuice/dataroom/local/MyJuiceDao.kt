package com.aziz.myjuice.dataroom.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MyJuiceDao {
    @Query("SELECT * FROM myjuice")
    fun getAllMyJuice(): Flow<List<MyJuiceEntity>>

    @Query("SELECT * FROM myjuice WHERE isFavorite = 1")
    fun getAllFavoriteMyJuice(): Flow<List<MyJuiceEntity>>

    @Query("SELECT * FROM myjuice WHERE id = :id")
    fun getMyJuice(id: Int): Flow<MyJuiceEntity>

    @Query("SELECT * FROM myjuice WHERE name LIKE '%' || :query || '%'")
    fun searchMyJuice(query: String): Flow<List<MyJuiceEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMyJuice(myjuiceList: List<MyJuiceEntity>)

    @Query("UPDATE myjuice SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun updateFavoriteMyJuice(id: Int, isFavorite: Boolean)
}