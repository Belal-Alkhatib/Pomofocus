package com.deepcoder.pomofocus.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.KeyboardType.Companion.Number
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.deepcoder.pomofocus.R
import com.deepcoder.pomofocus.ui.theme.Black64
import com.deepcoder.pomofocus.ui.theme.Radius16
import com.deepcoder.pomofocus.ui.theme.Space16
import com.deepcoder.pomofocus.ui.theme.Space4
import com.deepcoder.pomofocus.ui.theme.Space8
import com.deepcoder.pomofocus.ui.theme.Whit

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomDialog(
    onCloseDialog: () -> Unit,
    onSaveData: (String, String, Int) -> Unit,
    openDialog: Boolean,
) {
    var workOnText by remember { mutableStateOf("") }
    var noteText by remember { mutableStateOf("") }
    var estPomodorosText by remember { mutableStateOf(1) }

    if (openDialog) {
        Dialog(
            onDismissRequest = { onCloseDialog() },
            properties = DialogProperties(dismissOnBackPress = true, dismissOnClickOutside = true)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Whit, shape = RoundedCornerShape(Space4)),
                verticalArrangement = Arrangement.spacedBy(Space4),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                EditTextFieldWithHint(
                    value = workOnText,
                    onValueChange = { workOnText = it },
                    hint = stringResource(R.string.what_are_you_working_on)
                )

                EditTextFieldWithHint(
                    value = noteText,
                    onValueChange = { noteText = it },
                    hint = stringResource(R.string.add_note)
                )

                EditTextFieldWithHint(
                    value = estPomodorosText.toString(),
                    onValueChange = { estPomodorosText = if(it.isBlank()) 0 else it.toInt() },
                    hint = stringResource(R.string.est_pomodoros),
                    keyboardType = Number
                )

                Button(
                    onClick = {
                        onSaveData(workOnText, noteText, estPomodorosText)
                        onCloseDialog()
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Space8, horizontal = Space16)
                ) {
                    Text(text = stringResource(R.string.save))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTextFieldWithHint(
    value: String,
    onValueChange: (String) -> Unit,
    hint: String,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    TextField(
        value = value,
        onValueChange = { onValueChange(it) },
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
        placeholder = {
            Text(
                text = hint,
                style = MaterialTheme.typography.labelSmall,
                color = Black64
            )
        },
        modifier = Modifier
            .padding(vertical = Space8, horizontal = Space4)
            .background(Whit, shape = RoundedCornerShape(Space16))
            .fillMaxWidth(),
        maxLines = 1,
        shape = RoundedCornerShape(Radius16),
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
        )
    )
}

@Preview
@Composable
fun CustomDialogPrev() {
    CustomDialog({},{a,b,c ->}, openDialog = true)
}
