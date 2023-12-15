package com.xoff.abittracked

import android.app.Application
import com.xoff.abittracked.data.proto.appStartupParamsDataStore
import com.xoff.abittracked.data.proto.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import com.xoff.abittracked.data.proto.appStartupParamsDataStore
import com.xoff.abittracked.proto.copy
class ProtoDataStoreApplication : Application() {

    private val appCoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    override fun onCreate() {
        super.onCreate()

        appCoroutineScope.launch {
            this@ProtoDataStoreApplication.appStartupParamsDataStore.updateData { params ->
                params.copy {
                    startupUnixTimestamp = System.currentTimeMillis()
                    startupCounter = params.startupCounter + 1
                }
            }
        }
    }
}
