package com.example.justforpractice.data

import androidx.room.*
import androidx.room.migration.AutoMigrationSpec
import com.example.justforpractice.dao.TaskDao
import com.example.justforpractice.model.Task
import com.example.justforpractice.utils.DateTypeConverter
import com.example.justforpractice.utils.TimeTypeConverter

@Database(entities = [Task::class], version = 2)
@TypeConverters(DateTypeConverter::class, TimeTypeConverter::class)
abstract class AppDatabase() : RoomDatabase() {

    abstract fun taskDao() : TaskDao

}