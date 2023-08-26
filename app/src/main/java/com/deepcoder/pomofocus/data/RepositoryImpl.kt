package com.deepcoder.pomofocus.data

import android.util.Log
import com.deepcoder.pomofocus.data.local.room.PomofocusDao
import com.deepcoder.pomofocus.data.local.room.entity.toTask
import com.deepcoder.pomofocus.domain.Repository
import com.deepcoder.pomofocus.domain.model.Task
import com.deepcoder.pomofocus.domain.model.toTaskEntity
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val pomofocusDao: PomofocusDao
): Repository {

    override suspend fun upsertTask(task: Task) =
        pomofocusDao.upsertTask(taskEntity = task.toTaskEntity())

    override suspend fun getTasks(): List<Task> = pomofocusDao.getAllTasks().map { it.toTask() }

    override suspend fun getTaskById(id: Long): Task = pomofocusDao.getTaskById(id).toTask()

    override suspend fun removeTask(id: Long) = pomofocusDao.deleteTaskById(id)

}