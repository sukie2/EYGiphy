package com.sukitha.ey.eygiphy.data.local

import androidx.room.*
import com.sukitha.ey.eygiphy.domain.data.Giphy
import kotlinx.coroutines.flow.Flow

@Dao
interface GiphyDao {
    @Query("SELECT * FROM giphy")
    fun getGiphy(): Flow<List<Giphy>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGiphy(giphy: Giphy)

    @Delete
    suspend fun deleteGiphy(giphy: Giphy)
}