package com.xoff.abittracked.ui

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.xoff.abittracked.data.proto.appStartupParamsDataStore
import com.xoff.abittracked.data.proto.taskDataStore
import com.xoff.abittracked.model.Tasks
import com.xoff.abittracked.proto.AppStartupParams
import com.xoff.abittracked.proto.TaskABT
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import mu.KotlinLogging
import java.text.SimpleDateFormat
import java.util.*

private const val DEFAULT_TIMESTAMP = 0
private val logger = KotlinLogging.logger {}
class MainViewModel(application: Application) : AndroidViewModel(application) {



    data class ViewState(
        val appCounter: Int = 0,
        val lastStartup: String = "",
        val tasks:Tasks=Gson().fromJson<Tasks>("""{"title":"toto","tasks":[{"id":1,"name":"Technical Author"},{"id":2,"name":"Technical Author"},{"id":3,"name":"Technical Editor"}]}"""
            ,Tasks::class.java)
    ){


    }

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    private var appStartupParamsCollectorJob: Job? = null

    private val appStartupParamsDataStore: DataStore<AppStartupParams>
        get() = getApplication<Application>().applicationContext.appStartupParamsDataStore

    private val taskDataStore: DataStore<TaskABT>
        get() = getApplication<Application>().applicationContext.taskDataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {

            appStartupParamsDataStore.data.collectLatest { startupParams: AppStartupParams ->
                _viewState.update { currentState ->
                    currentState.copy(
                        appCounter = startupParams.startupCounter,
                        lastStartup = convertToReadableFormat(startupParams.startupUnixTimestamp)
                    )
                }
            }
            taskDataStore.data.collectLatest { p: TaskABT ->
                _viewState.update { currentState ->
                    currentState.copy(

                        tasks = Gson().fromJson(p.content,Tasks::class.java)
                    )
                }
            }
        }
    }

    fun resetData() {
        viewModelScope.launch(Dispatchers.IO) {
            appStartupParamsDataStore.updateData { AppStartupParams.getDefaultInstance() }
        }
    }
    fun populateData() {
        viewModelScope.launch(Dispatchers.IO) {

            taskDataStore.updateData { TaskABT.getDefaultInstance() }
        }
    }
    override fun onCleared() {
        appStartupParamsCollectorJob?.cancel()
        super.onCleared()
    }

    private fun convertToReadableFormat(unixTimestamp: Long): String {
        return if (unixTimestamp > DEFAULT_TIMESTAMP) {
            val timestampAsDate = Date(unixTimestamp)
            SimpleDateFormat.getDateTimeInstance().format(timestampAsDate)
        } else {
            "No app opening registered yet."
        }
    }
}
