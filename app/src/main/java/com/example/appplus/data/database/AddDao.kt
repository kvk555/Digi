package com.example.appplus.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AddDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(items: List<ItemDb>)

    @Query("SELECT * FROM ItemDb")
    suspend fun fetchItems(): List<ItemDb>
}
