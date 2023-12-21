package com.xoff.abittracked

import com.google.gson.Gson
import com.xoff.abittracked.model.TaskItem
import junit.framework.TestCase.assertEquals
import org.junit.Test
class TaskManagerTestKt {
    @Test
    fun serializeObjectListTest() {
        val tasks = listOf(
            TaskItem(1, "Technical Author"),
            TaskItem(2, "Technical Author"),
            TaskItem(3, "Technical Editor")
        )
        val serialized = Gson().toJson(tasks)

        val json =
            """[{"id":1,"name":"Technical Author"},{"id":2,"name":"Technical Author"},{"id":3,"name":"Technical Editor"}]"""
        assertEquals(serialized, json)
    }
}