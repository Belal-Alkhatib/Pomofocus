package com.deepcoder.pomofocus.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.deepcoder.pomofocus.ui.theme.Black80
import com.deepcoder.pomofocus.ui.theme.CardSize64
import com.deepcoder.pomofocus.ui.theme.Radius4
import com.deepcoder.pomofocus.ui.theme.Space4
import com.deepcoder.pomofocus.ui.theme.Space8
import com.deepcoder.pomofocus.ui.theme.Whit

@Composable
fun TaskCard(
    title: String,
    roundNumber: String,
    fromFoundNumber: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Whit,
) {

    Card(
        modifier = modifier
            .height(CardSize64),
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = backgroundColor, shape = RoundedCornerShape(Radius4))
                .padding(horizontal = Space8),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                modifier = Modifier.padding(start = Space4),
                text = title,
                style = MaterialTheme.typography.labelLarge,
                color = Black80,
            )

            FactorText(firstNumber = roundNumber, secondNumber = fromFoundNumber)

        }

    }

}

@Preview
@Composable
fun TaskCardPrev() {
    TaskCard(title = "Task Title", roundNumber = "1", fromFoundNumber = "2", backgroundColor = Color.Green)
}