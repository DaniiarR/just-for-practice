package com.example.justforpractice.repository

import com.example.justforpractice.data.AppDatabase

class AppRepository(private val database: AppDatabase) {

    private val dao = database.taskDao()

    suspend fun getUsers() = dao.getAllTasks()

}