package com.example.justforpractice.tasks.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.justforpractice.data.AppDatabase
import com.example.justforpractice.model.Task
import com.example.justforpractice.repository.AppRepository
import com.example.justforpractice.utils.Resource
import kotlinx.coroutines.launch
import java.lang.Exception

class TaskListViewModel(val repository: AppRepository) : ViewModel() {

    private val tasks = MutableLiveData<Resource<List<Task>>>()

    private val taskToAdd = MutableLiveData<Task>()

    init {
        fetchTasks()
    }

    private fun fetchTasks() {
        viewModelScope.launch {
            tasks.postValue(Resource.loading(null))
            try {
                val tasksFromDb = repository.getUsers()
                tasks.postValue(Resource.success(tasksFromDb))
            } catch (e: Exception) {
                tasks.postValue(Resource.error("Could not fetch users from database", null))
            }
        }
        tasks.value = Resource.success(arrayListOf(Task(1, "abc", null, false)))
    }

    fun setDate() {

    }
    fun getTasks(): LiveData<Resource<List<Task>>> = tasks
}