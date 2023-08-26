package com.deepcoder.pomofocus.ui.screens.home

interface HomeInteraction {
    fun onStartButtonClicked()
    fun onPomodoroButtonClicked()
    fun onShortBreakButtonClicked()
    fun onLongBreakButtonClicked()
    fun onAddTaskCardClicked()
    fun onCloseAddTaskDialog()
    fun onSaveTask(workOn: String, note: String, estPomodoros: Int)
    fun onRoundFinish()
    fun onRemoveTask(taskId: Long)

}