package com.example.justforpractice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.LocalDate
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.OffsetTime
import java.util.*

@Entity(tableName = "tasks")
class Task(
    var name: String? = null,
    var additionalInfo: String? = null,
    var date: LocalDate? = null,
    var time: OffsetTime? = null,
    var isDone: Boolean = false
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
}
