package com.deepcoder.pomofocus.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deepcoder.pomofocus.data.local.room.entity.TaskEntity

@Database(version = 1, entities = [TaskEntity::class])
abstract class PomofocusDatabase() : RoomDatabase() {

    abstract fun getPomofocusDao(): PomofocusDao
}
