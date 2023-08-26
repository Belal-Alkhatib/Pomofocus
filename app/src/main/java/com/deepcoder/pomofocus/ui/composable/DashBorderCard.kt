package com.deepcoder.pomofocus.ui.composable

import android.graphics.Paint
import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.deepcoder.pomofocus.R
import com.deepcoder.pomofocus.ui.modifier.dashedBorder
import com.deepcoder.pomofocus.ui.theme.Black32
import com.deepcoder.pomofocus.ui.theme.ButtonSize24
import com.deepcoder.pomofocus.ui.theme.CardSize64
import com.deepcoder.pomofocus.ui.theme.Radius4
import com.deepcoder.pomofocus.ui.theme.Radius8
import com.deepcoder.pomofocus.ui.theme.RedSecondary
import com.deepcoder.pomofocus.ui.theme.RedThirdly
import com.deepcoder.pomofocus.ui.theme.Space2
import com.deepcoder.pomofocus.ui.theme.Space4
import com.deepcoder.pomofocus.ui.theme.Space8
import com.deepcoder.pomofocus.ui.theme.Whit60

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBorderCard(
    onCardClicked: () -> Unit,
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.add_task),
    iconPainter: Painter = painterResource(id = R.drawable.ic_add_circle),
) {
    Card(
        modifier = modifier
            .height(CardSize64)
            .dashedBorder(
                width = 2.dp,
                color = RedSecondary,
                shape = MaterialTheme.shapes.medium, on = 4.dp, off = 4.dp
            ),
        onClick = { onCardClicked() },
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(color = RedThirdly, shape = RoundedCornerShape(Radius4)),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                modifier = Modifier.size(ButtonSize24),
                painter = iconPainter,
                contentDescription = null,
                tint = Whit60
            )
            Text(modifier = Modifier.padding(start = Space4), text = text, style = MaterialTheme.typography.labelLarge, color = Whit60)
        }

    }
}

@Preview
@Composable
fun DashBorderPreview() {
    DashBorderCard(
        onCardClicked = { },
        iconPainter = painterResource(id = R.drawable.ic_add_circle)
    )
}