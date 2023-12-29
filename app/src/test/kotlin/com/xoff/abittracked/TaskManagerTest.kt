package com.xoff.abittracked

import com.google.gson.Gson
import com.xoff.abittracked.model.TaskItem
import com.xoff.abittracked.model.Tasks
import junit.framework.TestCase.assertEquals
import org.junit.Test
class TaskManagerTestKt {
    @Test
    fun serializeTasksListTest() {
        val tasks = listOf(
            TaskItem(1, "Technical Author",5,5,"desc 1",1000,5,true,false),
            TaskItem(2, "Technical Author",5,5,"desc 2",1000,5,true,false),
            TaskItem(3, "Technical Editor",5,5,"desc 3",1000,5,true,false)
        )
        val serialized = Gson().toJson(tasks)

        val json =
            """[{"id":1,"name":"Technical Author"},{"id":2,"name":"Technical Author"},{"id":3,"name":"Technical Editor"}]"""
        assertEquals(serialized, json)
    }
    @Test
    fun unserialiseTasks() {
        val json =
            """{"title":"toto","tasks":[{"id":1,"name":"Technical Author"},{"id":2,"name":"Technical Author"},{"id":3,"name":"Technical Editor"}]}"""
        val t=Gson().fromJson<Tasks>(json,Tasks::class.java)
        assertEquals(t.title, "toto")
    }
    @Test
    fun serialiseTasks() {
        val tasks = listOf(
            TaskItem(1, "Technical Author",5,5,"desc 1",1000,5,true,false),
            TaskItem(2, "Technical Author",5,5,"desc 2",1000,5,true,false),
            TaskItem(3, "Technical Editor",5,5,"desc 3",1000,5,true,false)
        )
        val allTasks=Tasks("toto",tasks)

        val serialized = Gson().toJson(allTasks)

        val json =
            """{"title":"toto","tasks":[{"id":1,"name":"Technical Author"},{"id":2,"name":"Technical Author"},{"id":3,"name":"Technical Editor"}]}"""
        assertEquals(serialized, json)
    }
}