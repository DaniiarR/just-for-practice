package com.example.justforpractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.OffsetDateTime
import java.util.*

@Entity(tableName = "tasks")
class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var name: String,
    var additionalInfo: String? = null,
    var dateTime: OffsetDateTime? = null,
    var isDone: Boolean = false
)
