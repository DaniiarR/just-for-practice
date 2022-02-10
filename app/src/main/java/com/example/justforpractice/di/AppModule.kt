package com.example.justforpractice.di

import com.example.justforpractice.repository.AppRepository
import com.example.justforpractice.tasks.TaskListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModule {

    fun createModule() : Module = module {
        single { AppRepository() }
        viewModel { TaskListViewModel(get()) }
    }
    }