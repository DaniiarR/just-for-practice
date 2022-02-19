package com.example.justforpractice.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.justforpractice.model.Task

@Dao
interface TaskDao {

    @Query("SELECT * FROM tasks")
    suspend fun getAllTasks(): List<Task>

}