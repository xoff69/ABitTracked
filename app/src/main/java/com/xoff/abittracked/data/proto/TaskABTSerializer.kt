package com.xoff.abittracked.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.xoff.abittracked.proto.TaskABT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object TaskABTSerializer : Serializer<TaskABT> {

    override val defaultValue: TaskABT = TaskABT.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): TaskABT = withContext(Dispatchers.IO) {
        try {
            return@withContext TaskABT.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: TaskABT, output: OutputStream) = withContext(Dispatchers.IO) { t.writeTo(output) }
}

val Context.taskDataStore: DataStore<TaskABT> by dataStore(
    fileName = "taskABT2.pb",
    serializer = TaskABTSerializer
)
