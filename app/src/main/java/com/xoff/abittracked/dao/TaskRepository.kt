package com.xoff.abittracked.dao

import androidx.annotation.WorkerThread


class TaskRepository(private val taskDao: TaskDao) {

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(task: TaskItem) {
        taskDao.inserttaskItem(task)
    }
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun getAllTasks():List<TaskItem> {
        return taskDao.getAllTask()
    }
}