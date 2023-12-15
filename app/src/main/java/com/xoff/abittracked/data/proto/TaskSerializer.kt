package com.xoff.abittracked.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.xoff.abittracked.proto.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object TaskSerializer : Serializer<Task> {

    override val defaultValue: Task = Task.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Task = withContext(Dispatchers.IO) {
        try {
            return@withContext Task.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Task, output: OutputStream) = withContext(Dispatchers.IO) { t.writeTo(output) }
}

val Context.taskDataStore: DataStore<Task> by dataStore(
    fileName = "task.pb",
    serializer = TaskSerializer
)
