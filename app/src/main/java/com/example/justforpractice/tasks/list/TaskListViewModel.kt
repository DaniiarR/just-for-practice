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
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.OffsetTime
import java.lang.Exception

class TaskListViewModel(private val repository: AppRepository) : ViewModel() {

    private val tasks = MutableLiveData<Resource<List<Task>>>()

    private val taskToAdd = MutableLiveData<Task?>()
    val timeToAdd = MutableLiveData<OffsetTime?>()

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
    }

    fun createTask() {
        taskToAdd.value = Task()
    }

    fun setDate(date: LocalDate) {
        taskToAdd.value?.date = date
    }

    fun getTasks(): LiveData<Resource<List<Task>>> = tasks

    fun deleteNewTask() {
        taskToAdd.value = null
        timeToAdd.value = null
    }

    fun setTime(time: OffsetTime) {
        timeToAdd.value = time
    }

    fun addTimeToTask(time: OffsetTime) {
        taskToAdd.value?.time = time
    }
}