package com.deepcoder.pomofocus.domain.usecase

import com.deepcoder.pomofocus.domain.Repository
import com.deepcoder.pomofocus.domain.model.Task
import javax.inject.Inject

class TaskManagerUseCase @Inject constructor(
    private val repository: Repository
) {
    suspend fun getTasks() = repository.getTasks().sortedByDescending { it.id }

    suspend fun upsertTask(task: Task){
        if (task.title.isNotBlank() && task.roundCount > 0){
            repository.upsertTask(task = task)
        }
    }

    suspend fun removeTask(taskId: Long) = repository.removeTask(taskId)

    suspend fun getFirstUnDoTask() = getTasks().lastOrNull() { !it.isFinished }

}