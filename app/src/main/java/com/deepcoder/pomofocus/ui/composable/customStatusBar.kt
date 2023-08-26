package com.deepcoder.pomofocus.ui.composable

import androidx.compose.ui.graphics.Color
import com.deepcoder.pomofocus.ui.theme.RedPrimary
import com.google.accompanist.systemuicontroller.SystemUiController

fun setStatusBarColor(
    color: Color ,
    systemUIController: SystemUiController
) {
    systemUIController.setStatusBarColor(color)
}