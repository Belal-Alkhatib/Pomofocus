package com.deepcoder.pomofocus.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.deepcoder.pomofocus.ui.theme.Black64
import com.deepcoder.pomofocus.ui.theme.Black80
import com.deepcoder.pomofocus.ui.theme.Gray
import com.deepcoder.pomofocus.ui.theme.Space4

@Composable
fun FactorText(
    firstNumber: String,
    secondNumber: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = firstNumber,
            style = MaterialTheme.typography.labelLarge,
            color = Black64,
        )

        Text(
            text = "/",
            style = MaterialTheme.typography.labelMedium,
            color = Black64,
        )

        Text(
            text = secondNumber,
            style = MaterialTheme.typography.labelMedium,
            color = Black64,
        )
    }
}

@Preview
@Composable
fun FactorTextPrev() {
    FactorText("2", "1")
}