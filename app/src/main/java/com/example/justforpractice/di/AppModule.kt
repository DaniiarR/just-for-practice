package com.example.justforpractice.di

import com.example.justforpractice.data.AppDatabase
import com.example.justforpractice.data.DatabaseBuilder
import com.example.justforpractice.repository.AppRepository
import com.example.justforpractice.tasks.list.TaskListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {

    fun createModule() : Module = module {
        single { DatabaseBuilder.getInstance(androidContext()) }
        single { AppRepository(get()) }
        viewModel { TaskListViewModel(get()) }
    }
    }