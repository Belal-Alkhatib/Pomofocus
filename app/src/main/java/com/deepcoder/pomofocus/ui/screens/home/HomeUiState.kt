package com.deepcoder.pomofocus.ui.screens.home

import com.deepcoder.pomofocus.domain.model.Task
import com.deepcoder.pomofocus.ui.screens.StopwatchType
import com.deepcoder.pomofocus.ui.screens.TimerButtonState
import com.deepcoder.pomofocus.ui.screens.TimerState

data class HomeUiState(
    val stopwatchType: StopwatchType = StopwatchType.Pomofocus,
    val tasks: List<TaskItemUiState> = emptyList(),
    val inProgressTask: TaskItemUiState = TaskItemUiState(),
    val isAddTaskCardClicked: Boolean = false,
    val currentTimeValue: String = "00:00",
    val timerState: TimerState = TimerState.Stop,
    val timerButtonState: TimerButtonState = TimerButtonState.Pause
)

data class TaskItemUiState(
    val id: Long = 0,
    val title: String = "",
    val roundCount: Int = 0,
    val fromRoundCount: Int = 0,
    val isFinished: Boolean = false,
    val note: String? = ""
)

fun Task.toTaskItemUiState() = TaskItemUiState(
    id = id,
    title = title,
    roundCount = roundCount,
    fromRoundCount = currentRoundCount,
    note = note,
    isFinished = isFinished,
)
