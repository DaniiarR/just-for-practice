package com.example.justforpractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var additionalInfo: String?,
    var isDone: Boolean
)
