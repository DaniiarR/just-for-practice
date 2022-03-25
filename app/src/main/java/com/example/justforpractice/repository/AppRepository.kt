package com.example.justforpractice.repository

import com.example.justforpractice.data.AppDatabase
import com.example.justforpractice.model.Task

class AppRepository(private val database: AppDatabase) {

    private val dao = database.taskDao()

    suspend fun getUsers() = dao.getAllTasks()

    suspend fun insertTask(task: Task) = dao.insertTask(task)

    suspend fun updateTask(task: Task) = dao.updateTask(task)

}