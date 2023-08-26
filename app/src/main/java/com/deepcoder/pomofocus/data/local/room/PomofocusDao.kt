package com.deepcoder.pomofocus.data.local.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.deepcoder.pomofocus.data.local.room.entity.TaskEntity

@Dao
interface PomofocusDao {

    @Upsert
    suspend fun upsertTask(taskEntity: TaskEntity)

    @Query("SELECT * FROM task_table")
    suspend fun getAllTasks(): List<TaskEntity>

    @Query("SELECT * FROM task_table WHERE id = :id")
    suspend fun getTaskById(id: Long): TaskEntity

    @Query("DELETE FROM task_table WHERE id = :id")
    suspend fun deleteTaskById(id: Long)
}