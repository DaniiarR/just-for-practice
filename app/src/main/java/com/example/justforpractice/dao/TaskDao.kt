package com.example.justforpractice.dao

import androidx.room.Dao
import androidx.room.Query

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")

}