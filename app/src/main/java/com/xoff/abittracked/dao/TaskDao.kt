package com.xoff.abittracked.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Insert
    suspend  fun inserttaskItem(taskItem: TaskItem)

    @Query("SELECT * FROM taskItem ORDER BY id DESC")
    suspend fun getAllTask(): List<TaskItem>
    @Delete
    suspend fun deletitem(id: TaskItem)
    @Update
    suspend fun updatetak(taskItem: TaskItem)
}