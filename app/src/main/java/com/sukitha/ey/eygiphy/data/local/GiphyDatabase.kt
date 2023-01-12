package com.sukitha.ey.eygiphy.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sukitha.ey.eygiphy.domain.data.Giphy

@Database(
    entities = [Giphy::class],
    version = 1
)
abstract class GiphyDatabase: RoomDatabase() {

    abstract val giphyDao: GiphyDao

    companion object {
        const val DATABASE_NAME = "giphy_db"
    }
}