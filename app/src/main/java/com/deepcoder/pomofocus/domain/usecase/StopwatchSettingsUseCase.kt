package com.deepcoder.pomofocus.domain.usecase

import android.util.Log
import com.deepcoder.pomofocus.domain.Repository
import com.deepcoder.pomofocus.domain.model.Task
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class StopwatchSettingsUseCase @Inject constructor(
    private val repository: Repository
) {

    suspend fun doWhenTimerFinish(currentProgressTaskId: Long){
        if(isTaskFinished(currentProgressTaskId)){
            repository.removeTask(currentProgressTaskId)
        }
    }

    private suspend fun isTaskFinished(taskId: Long): Boolean = coroutineScope {
        val currentTask = getCurrentTask(taskId)
        incrementRoundCounter(currentTask)
        val mainRoundNumber = async { getMainRoundNumberForCurrentTask(currentTask) }
        val currentRoundNumber = async { getCurrentRoundForCurrentTask(currentTask) }

        mainRoundNumber.await() == currentRoundNumber.await()+1
    }

    private suspend fun incrementRoundCounter(currentTask: Task){
        val newTask = currentTask.copy(currentRoundCount = currentTask.currentRoundCount+1)
        repository.upsertTask(newTask)
    }

    private fun getMainRoundNumberForCurrentTask(task: Task): Int = task.roundCount

    private fun getCurrentRoundForCurrentTask(task: Task): Int = task.currentRoundCount

    private suspend fun getCurrentTask(taskId: Long): Task = repository.getTaskById(taskId)

}