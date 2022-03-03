package com.example.justforpractice.data

import android.content.Context
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.justforpractice.dao.TaskDao
import com.example.justforpractice.model.Task

@Database(entities = [Task::class], version = 1)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun taskDao() : TaskDao
}