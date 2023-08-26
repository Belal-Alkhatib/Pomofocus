package com.deepcoder.pomofocus.data.local.room.entity

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.deepcoder.pomofocus.domain.model.Task

@Entity(tableName = "task_table")
data class TaskEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val title: String,
    val roundCount: Int,
    val currentRoundCount: Int,
    val isFinished: Boolean,
    val note: String?
)

fun TaskEntity.toTask() = Task(id = id, title = title, roundCount = roundCount, note = note,
    currentRoundCount = currentRoundCount,
    isFinished = isFinished, )