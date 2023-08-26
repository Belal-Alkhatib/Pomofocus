package com.deepcoder.pomofocus.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.deepcoder.pomofocus.domain.model.Task
import com.deepcoder.pomofocus.domain.usecase.TaskManagerUseCase
import com.deepcoder.pomofocus.domain.usecase.StopwatchSettingsUseCase
import com.deepcoder.pomofocus.ui.screens.StopwatchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    //  private val mediaPlayer: MediaPlayer
    private val taskManagerUseCase: TaskManagerUseCase,
    private val stopwatchSettingsUseCase: StopwatchSettingsUseCase
) : ViewModel(), HomeInteraction {

    private val _state: MutableStateFlow<HomeUiState> by lazy { MutableStateFlow(HomeUiState()) }
    val state = _state.asStateFlow()

    init { updateTasks() }

    override fun onStartButtonClicked() {
        // mediaPlayer.start()
    }

    override fun onPomodoroButtonClicked() {
        _state.update { it.copy(stopwatchType = StopwatchType.Pomofocus) }
    }

    override fun onShortBreakButtonClicked() {
        _state.update { it.copy(stopwatchType = StopwatchType.ShortBreak) }
    }

    override fun onLongBreakButtonClicked() {
        _state.update { it.copy(stopwatchType = StopwatchType.LongBreak) }
    }

    override fun onAddTaskCardClicked() {
        _state.update { it.copy(isAddTaskCardClicked = true) }
    }

    override fun onCloseAddTaskDialog() {
        _state.update { it.copy(isAddTaskCardClicked = false) }
    }

    override fun onSaveTask(workOn: String, note: String, estPomodoros: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            taskManagerUseCase.upsertTask(
                Task(
                    id = 0, title = workOn, roundCount = estPomodoros, note = note,
                    currentRoundCount = 0,
                    isFinished = false,
                )
            )
            updateTasks()
        }
    }

    override fun onRoundFinish() {
        viewModelScope.launch(Dispatchers.IO) {
                getFirstUnDoTask()?.let { task ->
                stopwatchSettingsUseCase.doWhenTimerFinish(task.id)
                updateTasks()
            }
        }
        _state.update { it.copy(stopwatchType = StopwatchType.ShortBreak) }
    }

    override fun onRemoveTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
             taskManagerUseCase.removeTask(taskId)
            updateTasks()
        }
    }


    private fun updateTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            val tasks = taskManagerUseCase.getTasks().map { task -> task.toTaskItemUiState() }
            _state.update { it.copy(tasks = tasks, inProgressTask = getFirstUnDoTask() ?: TaskItemUiState()) }
        }
    }

    private suspend fun getFirstUnDoTask(): TaskItemUiState? = taskManagerUseCase.getFirstUnDoTask()?.toTaskItemUiState()


}