package com.deepcoder.pomofocus.data.local.service

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.NotificationCompat
import com.deepcoder.pomofocus.util.Constants.ACTION_SERVICE_CANCEL
import com.deepcoder.pomofocus.util.Constants.ACTION_SERVICE_START
import com.deepcoder.pomofocus.util.Constants.ACTION_SERVICE_STOP
import com.deepcoder.pomofocus.util.Constants.NOTIFICATION_CHANNEL_ID
import com.deepcoder.pomofocus.util.Constants.NOTIFICATION_CHANNEL_NAME
import com.deepcoder.pomofocus.util.Constants.NOTIFICATION_ID
import com.deepcoder.pomofocus.util.Constants.STOPWATCH_STATE
import com.deepcoder.pomofocus.util.formatTime
import com.deepcoder.pomofocus.util.pad
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@ExperimentalAnimationApi
@AndroidEntryPoint
class StopwatchService : Service() {
    @Inject
    lateinit var notificationManager: NotificationManager
    @Inject
    lateinit var notificationBuilder: NotificationCompat.Builder
    @Inject
    lateinit var finishMusic: MediaPlayer
    private val binder = StopwatchBinder()
    private var duration: Duration = 1.minutes //Duration.ZERO
    private lateinit var timer: Timer

    var seconds = mutableStateOf("00")
        private set
    var minutes = mutableStateOf("01")
        private set
    var currentState = mutableStateOf(StopwatchState.Idle)
        private set
    var isRoundFinished = mutableStateOf(false)
        private set

    override fun onBind(p0: Intent?) = binder

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // Handle the different stopwatch states based on the received intent
        when (intent?.getStringExtra(STOPWATCH_STATE)) {
            StopwatchState.Started.name -> {
                setStopButton()
                startForegroundService()
                startStopwatch { minutes, seconds ->
                    updateNotification(minutes = minutes, seconds = seconds)
                }
            }

            StopwatchState.Stopped.name -> {
                stopStopwatch()
                setResumeButton()
            }

            StopwatchState.Canceled.name -> {
                stopStopwatch()
                cancelStopwatch()
                stopForegroundService()
            }
        }
        // Handle actions directly from intent
        intent?.action.let {
            when (it) {
                ACTION_SERVICE_START -> {
                    setStopButton()
                    startForegroundService()
                    startStopwatch { minutes, seconds ->
                        updateNotification(minutes = minutes, seconds = seconds)
                    }
                }

                ACTION_SERVICE_STOP -> {
                    stopStopwatch()
                    setResumeButton()
                }

                ACTION_SERVICE_CANCEL -> {
                    stopStopwatch()
                    cancelStopwatch()
                    stopForegroundService()
                }
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    private fun stopStopwatch() {
        if (this::timer.isInitialized) {
            timer.cancel()
        }
        currentState.value = StopwatchState.Stopped
        updateTimeUnits()
    }

    private fun cancelStopwatch() {
        duration = Duration.ZERO
        currentState.value = StopwatchState.Idle
        updateTimeUnits()
    }

    // Update time units for the UI
    private fun updateTimeUnits() {
        duration.toComponents { minutes, seconds, _ ->
            this@StopwatchService.minutes.value = minutes.toInt().pad()
            this@StopwatchService.seconds.value = seconds.pad()
        }
    }

    private fun startForegroundService() {
        createNotificationChannel()
        startForeground(NOTIFICATION_ID, notificationBuilder.build())
    }

    // Stop the foreground service and remove notification
    private fun stopForegroundService() {
        notificationManager.cancel(NOTIFICATION_ID)
        stopForeground(STOP_FOREGROUND_REMOVE)
        stopSelf()
    }

    // Create a notification channel for the service
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW
            )
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Update the notification with the current time
    private fun updateNotification(minutes: String, seconds: String) {
        notificationManager.notify(
            NOTIFICATION_ID,
            notificationBuilder.setContentText(
                formatTime(
                    minutes = minutes,
                    seconds = seconds,
                )
            ).build()
        )
    }


    // Start the stopwatch timer and handle tick updates
    // Stop the stopwatch and play finish music when timer reaches 0
    private fun startStopwatch(onTick: (m: String, s: String) -> Unit) {
        timer = fixedRateTimer(initialDelay = 1000L, period = 1000L) {
            if ((duration.minus(1.seconds)).isNegative()) {
                isRoundFinished.value = true
                stopStopwatch()
                updateNotification("01", "00")
                finishMusic.start()
            } else {
                updateTimeUnits()
                onTick(minutes.value, seconds.value)
                duration = duration.minus(1.seconds)
                isRoundFinished.value = false
            }
        }

//        if (currentState.value != StopwatchState.Started) { // Check if it's a restart
//            duration = 1.minutes // Reset the duration for a restart
//            updateTimeUnits()
//        }

        duration = 1.minutes
        currentState.value = StopwatchState.Started
        updateTimeUnits()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopForegroundService()
    }

    @SuppressLint("RestrictedApi")
    private fun setStopButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Stop",
                ServiceHelper.stopPendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    @SuppressLint("RestrictedApi")
    private fun setResumeButton() {
        notificationBuilder.mActions.removeAt(0)
        notificationBuilder.mActions.add(
            0,
            NotificationCompat.Action(
                0,
                "Restart",
                ServiceHelper.resumePendingIntent(this)
            )
        )
        notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build())
    }

    // Inner class for binding the service
    inner class StopwatchBinder : Binder() {
        fun getService(): StopwatchService = this@StopwatchService
    }
}

// Enum to represent the different states of the stopwatch
enum class StopwatchState {
    Idle,
    Started,
    Stopped,
    Canceled
}