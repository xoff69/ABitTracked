package com.xoff.abittracked.ui

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.xoff.abittracked.dao.TaskDatabase
import com.xoff.abittracked.dao.TaskRepository
import com.xoff.abittracked.data.proto.appStartupParamsDataStore
import com.xoff.abittracked.proto.AppStartupParams
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
class MainViewModel(application: Application,private val repository: TaskRepository) : AndroidViewModel(application) {

    data class ViewState(
        val appCounter: Int = 0,
        val lastStartup: String = ""
    ){
        var tasks =  listOf("a","ab","cccccccc")
    }

    private val _viewState = MutableStateFlow(ViewState())
    val viewState = _viewState.asStateFlow()

    private var appStartupParamsCollectorJob: Job? = null
    private val mRepository: TaskDatabase? = null
    private val appStartupParamsDataStore: DataStore<AppStartupParams>
        get() = getApplication<Application>().applicationContext.appStartupParamsDataStore

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val tasksitems=repository.getAllTasks()
    logger.info {  "nb taches ${tasksitems.size}"}
            appStartupParamsDataStore.data.collectLatest { startupParams: AppStartupParams ->
                _viewState.update { currentState ->
                    currentState.copy(
                        appCounter = startupParams.startupCounter,
                        lastStartup = convertToReadableFormat(startupParams.startupUnixTimestamp)
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
