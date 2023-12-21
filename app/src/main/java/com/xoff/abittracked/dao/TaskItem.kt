package com.xoff.abittracked.dao

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskItem(
    @ColumnInfo(name = "title")
    var taskname: String,
    @ColumnInfo(name = "Description")
    var TaskDescription: String,
    @ColumnInfo(name = "priority")
    var priority: Int

){
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int=0
}