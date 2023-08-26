package com.deepcoder.pomofocus.ui.screens.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DismissDirection
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SwipeToDismiss
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.deepcoder.pomofocus.R
import com.deepcoder.pomofocus.data.local.service.ServiceHelper
import com.deepcoder.pomofocus.data.local.service.StopwatchService
import com.deepcoder.pomofocus.data.local.service.StopwatchState
import com.deepcoder.pomofocus.ui.composable.CustomDialog
import com.deepcoder.pomofocus.ui.composable.DashBorderCard
import com.deepcoder.pomofocus.ui.composable.TaskCard
import com.deepcoder.pomofocus.ui.composable.setStatusBarColor
import com.deepcoder.pomofocus.ui.screens.StopwatchType.*
import com.deepcoder.pomofocus.ui.theme.Black32
import com.deepcoder.pomofocus.ui.theme.Black80
import com.deepcoder.pomofocus.ui.theme.BluePrimary
import com.deepcoder.pomofocus.ui.theme.BlueSecondary
import com.deepcoder.pomofocus.ui.theme.ButtonSize200
import com.deepcoder.pomofocus.ui.theme.ButtonSize32
import com.deepcoder.pomofocus.ui.theme.ButtonSize40
import com.deepcoder.pomofocus.ui.theme.ButtonSize72
import com.deepcoder.pomofocus.ui.theme.GreenPrimary
import com.deepcoder.pomofocus.ui.theme.GreenSecondary
import com.deepcoder.pomofocus.ui.theme.Radius12
import com.deepcoder.pomofocus.ui.theme.Radius4
import com.deepcoder.pomofocus.ui.theme.RedPrimary
import com.deepcoder.pomofocus.ui.theme.RedSecondary
import com.deepcoder.pomofocus.ui.theme.Space0
import com.deepcoder.pomofocus.ui.theme.Space12
import com.deepcoder.pomofocus.ui.theme.Space16
import com.deepcoder.pomofocus.ui.theme.Space2
import com.deepcoder.pomofocus.ui.theme.Space4
import com.deepcoder.pomofocus.ui.theme.Space8
import com.deepcoder.pomofocus.ui.theme.Thickness2
import com.deepcoder.pomofocus.ui.theme.Whit
import com.deepcoder.pomofocus.ui.theme.Whit60
import com.deepcoder.pomofocus.util.Constants.ACTION_SERVICE_START
import com.deepcoder.pomofocus.util.Constants.ACTION_SERVICE_STOP
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    stopwatchService: StopwatchService,
) {
    val state by viewModel.state.collectAsState()

    HomeContent(state = state, homeInteraction = viewModel, stopwatchService = stopwatchService)

}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalAnimationApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
private fun HomeContent(
    state: HomeUiState,
    stopwatchService: StopwatchService,
    homeInteraction: HomeInteraction
) {
    val typography = MaterialTheme.typography

    val context = LocalContext.current
    val minutes by stopwatchService.minutes
    val seconds by stopwatchService.seconds
    val currentState by stopwatchService.currentState
    val isRoundFinished by stopwatchService.isRoundFinished

    LaunchedEffect(key1 = isRoundFinished) {
        if (state.stopwatchType == Pomofocus && isRoundFinished) homeInteraction.onRoundFinish()
    }

    val (primaryColor, secondaryColor) = when (state.stopwatchType) {
        Pomofocus -> RedPrimary to RedSecondary
        ShortBreak -> GreenPrimary to GreenSecondary
        LongBreak -> BluePrimary to BlueSecondary
    }

    val animatedPrimaryColor by animateColorAsState(targetValue = primaryColor, label = "primaryColor", animationSpec = tween(400))
    val animatedSecondaryColor by animateColorAsState(targetValue = secondaryColor, label = "secondaryColor", animationSpec = tween(400))


    val systemUIController = rememberSystemUiController()
    LaunchedEffect(state.stopwatchType) { setStatusBarColor(systemUIController = systemUIController, color = primaryColor) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = animatedPrimaryColor)
            .padding(vertical = Space16, horizontal = Space8),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Column(
            modifier = Modifier
                .background(color = animatedSecondaryColor, shape = RoundedCornerShape(Radius12))
                .padding(horizontal = Space8, vertical = Space8),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth().height(ButtonSize40),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Button(
                    onClick = { homeInteraction.onPomodoroButtonClicked() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = Space0, vertical = Space2)
                        .weight(1f),
                    shape = RoundedCornerShape(Radius4),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.stopwatchType == Pomofocus)
                            Black32
                        else
                            Color.Transparent
                    )
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(R.string.pomodoro),
                        style = typography.bodySmall,
                        color = Whit,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = { homeInteraction.onShortBreakButtonClicked() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = Space0, vertical = Space2)
                        .weight(1f),
                    shape = RoundedCornerShape(Radius4),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.stopwatchType == ShortBreak)
                            Black32
                        else
                            Color.Transparent
                    )
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(R.string.short_break),
                        style = typography.bodySmall,
                        color = Whit,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }

                Button(
                    onClick = { homeInteraction.onLongBreakButtonClicked() },
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(horizontal = Space0, vertical = Space2)
                        .weight(1f),
                    shape = RoundedCornerShape(Radius4),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (state.stopwatchType == LongBreak)
                            Black32
                        else
                            Color.Transparent
                    )
                ) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        text = stringResource(R.string.long_break),
                        style = typography.bodySmall,
                        color = Whit,
                        maxLines = 1,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Text(
                modifier = Modifier.fillMaxWidth().padding(all = Space8),
                textAlign = TextAlign.Center,
                text = "$minutes:$seconds",
                style = typography.titleLarge,
                color = Whit
            )

            Button(
                modifier = Modifier
                    .height(ButtonSize72)
                    .width(ButtonSize200)
                    .padding(all = Space8)
                    .shadow(
                        elevation = if (currentState == StopwatchState.Started) Space12 else Space0,
                        spotColor = Black80
                    ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Whit,
                    contentColor = RedPrimary
                ),
                shape = RoundedCornerShape(Radius4),
                onClick = {
                    ServiceHelper.triggerForegroundService(
                        context = context,
                        action = if (currentState == StopwatchState.Started) ACTION_SERVICE_STOP
                        else ACTION_SERVICE_START
                    )
                }
            ) {
                Text(
                    text = if (currentState == StopwatchState.Started)
                        stringResource(R.string.restart)
                    else
                        stringResource(R.string.start),
                    style = typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    color = animatedPrimaryColor
                )
            }
        }

        AnimatedVisibility(
            visible = state.stopwatchType == Pomofocus,
            enter = scaleIn(),
            exit = scaleOut()
        ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                modifier = Modifier.padding(top = Space16),
                text = if (state.tasks.isNotEmpty()) {
                    "#${state.inProgressTask.fromRoundCount}"
                } else {
                    "#0"
                },
                style = typography.bodySmall,
                color = Whit60,
                textAlign = TextAlign.Center
            )

            Text(
                text = if (state.tasks.isNotEmpty()) state.tasks.last().title
                else stringResource(R.string.time_to_focus),
                style = typography.bodyMedium,
                color = Whit,
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = Space16),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {
                Text(
                    text = stringResource(id = R.string.tasks),
                    style = typography.bodyLarge,
                    color = Whit,
                    textAlign = TextAlign.Center
                )

                Icon(
                    modifier = Modifier
                        .size(ButtonSize32)
                        .background(color = RedSecondary, shape = RoundedCornerShape(Space4))
                        .clickable {
                            //todo: on more button clicked
                        },
                    tint = Whit,
                    painter = painterResource(id = R.drawable.ic_more),
                    contentDescription = null
                )
            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Space16),
                color = Whit60,
                thickness = Thickness2
            )

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(Space8),
                contentPadding = PaddingValues(vertical = Space8, horizontal = Space16)
            ) {
                items(state.tasks, key = { it.id }) { task ->
                    val dismissState = rememberDismissState(
                        confirmValueChange = {
                            homeInteraction.onRemoveTask(task.id)
                            true
                        }
                    )

                    AnimatedVisibility(
                        visible = !dismissState.isDismissed(DismissDirection.EndToStart),
                        enter = fadeIn() + scaleIn(),
                        exit = fadeOut() + scaleOut()
                    ) {
                        SwipeToDismiss(
                            state = dismissState,
                            directions = setOf(DismissDirection.EndToStart),
                            background = { SwipeBackground() },
                            dismissContent = {
                                TaskCard(
                                    modifier = Modifier.animateItemPlacement(
                                        animationSpec = tween(
                                            400
                                        )
                                    ),
                                    title = task.title,
                                    roundNumber = task.fromRoundCount.toString(),
                                    fromFoundNumber = task.roundCount.toString(),
                                    backgroundColor = if (task.isFinished) Color.Green else Whit
                                )
                            })
                    }

                }

                item { DashBorderCard(onCardClicked = { homeInteraction.onAddTaskCardClicked() }) }
            }
        }
    }
        CustomDialog(
            onCloseDialog = { homeInteraction.onCloseAddTaskDialog() },
            onSaveData = { workOn, note, estPomodoros ->
                homeInteraction.onSaveTask(workOn, note, estPomodoros)
            },
            openDialog = state.isAddTaskCardClicked
        )

    }
}

@Composable
private fun SwipeBackground() {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .background(color = GreenPrimary, shape = RoundedCornerShape(Radius12))
            .padding(Space16),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.End
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_remove),
            tint = Whit,
            contentDescription = null
        )
    }
}
