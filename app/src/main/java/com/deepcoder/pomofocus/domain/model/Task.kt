package com.deepcoder.pomofocus.domain.model

import com.deepcoder.pomofocus.data.local.room.entity.TaskEntity

data class Task(
    val id: Long,
    val title: String,
    val roundCount: Int,
    val currentRoundCount: Int,
    val note: String?,
    val isFinished: Boolean
)

fun Task.toTaskEntity() = TaskEntity(
    id = this.id,
    title = this.title,
    roundCount = this.roundCount,
    note = this.note ?: "",
    currentRoundCount = this.currentRoundCount,
    isFinished = this.isFinished
)