package com.xoff.abittracked.data.proto

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import com.xoff.abittracked.proto.Done
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.OutputStream

object DoneSerializer : Serializer<Done> {

    override val defaultValue: Done = Done.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): Done = withContext(Dispatchers.IO) {
        try {
            return@withContext Done.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: Done, output: OutputStream) = withContext(Dispatchers.IO) { t.writeTo(output) }
}

val Context.doneDataStore: DataStore<Done> by dataStore(
    fileName = "done.pb",
    serializer = DoneSerializer
)
