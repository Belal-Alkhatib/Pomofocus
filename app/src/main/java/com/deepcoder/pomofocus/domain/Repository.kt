package com.deepcoder.pomofocus.domain

import com.deepcoder.pomofocus.domain.model.Task

interface Repository {
    suspend fun upsertTask(task: Task)

    suspend fun getTasks(): List<Task>

    suspend fun getTaskById(id: Long): Task

    suspend fun removeTask(id: Long)
}