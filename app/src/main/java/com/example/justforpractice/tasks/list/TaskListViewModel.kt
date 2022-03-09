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
import org.threeten.bp.format.DateTimeFormatter
import java.lang.Exception

class TaskListViewModel(private val repository: AppRepository) : ViewModel() {

    private val tasks = MutableLiveData<Resource<List<Task>>>()

    private val taskToAdd = MutableLiveData<Task?>()
    val dateToAdd = MutableLiveData<LocalDate?>()
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

    fun setDate(year: Int, month: Int, dayOfMonth: Int) {
        dateToAdd.value = LocalDate.of(year, month + 1, dayOfMonth)
    }

    fun setDate(date: LocalDate?) {
        dateToAdd.value = date
    }

    fun getDate(): Long? = taskToAdd.value?.date?.atStartOfDay()?.toInstant(OffsetDateTime.now().offset)?.toEpochMilli()

    fun getFormattedDateTime(): String? {
        val formatter = DateTimeFormatter.ofPattern("EEE, MMM dd")
        var dateTime = formatter.format(taskToAdd.value?.date)
        timeToAdd.value?.let {
            dateTime += ", " + DateTimeFormatter.ofPattern("HH:mm").format(it)
        }
        return dateTime
    }

    fun getHours(): Int? = taskToAdd.value?.time?.hour

    fun getMinutes(): Int? = taskToAdd.value?.time?.minute

    fun getTasks(): LiveData<Resource<List<Task>>> = tasks

    fun deleteNewTask() {
        taskToAdd.value = null
    }

    fun deleteDateTime() {
        taskToAdd.value?.date = null
        taskToAdd.value?.time = null
        timeToAdd.value = null
        dateToAdd.value = null
    }

    fun setTime(time: OffsetTime) {
        timeToAdd.value = time
    }

    fun addTimeToTask(time: OffsetTime) {
        taskToAdd.value?.time = time
    }

    fun addDateToTask(date: LocalDate) {
        taskToAdd.value?.date = date
    }

    fun setTaskName(name: String) {
        taskToAdd.value?.name = name
    }

    fun setTaskDescription(description: String) {
        taskToAdd.value?.additionalInfo = description
    }



}