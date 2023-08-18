package com.chetan.jobnepal.ui.component.textfield

import android.view.KeyEvent.ACTION_DOWN
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material3.MaterialTheme
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TextFieldJobNepal(
    modifier: Modifier = Modifier,
    keyboardOptions: KeyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
    label: String? = null,
    placeholder: String? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    value: String,
    onValueChange: (String) -> Unit,
    required: Boolean? = null,
    isError: Boolean = false,
    readOnly: Boolean = false,
    interactionSource: MutableInteractionSource = remember {
        MutableInteractionSource()
    }
) {
    val focusManager = LocalFocusManager.current
    TextField(
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        interactionSource = interactionSource,
        isError = isError,
        singleLine = singleLine,
        enabled = enabled,
        colors = TextFieldDefaults.textFieldColors(backgroundColor = MaterialTheme.colorScheme.primaryContainer),
        value = value,
        onValueChange = onValueChange,
        trailingIcon = trailingIcon,
        leadingIcon = leadingIcon,
        label = {
            if (label != null) {
                Text(
                    buildAnnotatedString {
                        append("$label ")
                        if (required == true) {
                            withStyle(style = SpanStyle(color = Color.Red)) {
                                append("*")
                            }
                        } else if (required == false) {
                            append("(" + "Optional" + ")")
                        }
                    }
                )
            }
        },
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = {
            if (placeholder != null) {
                Text(placeholder)
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .onPreviewKeyEvent {
                if (it.key == Key.Tab && it.nativeKeyEvent.action == ACTION_DOWN) {
                    focusManager.moveFocus(FocusDirection.Down)
                    true
                } else {
                    false
                }
            }
    )
}

@Preview
@Composable
fun ShowTextField() {
    TextFieldJobNepal(
        label = "hi",
        value = "Nepal",
        onValueChange = {
    },
    placeholder = "Your Father Name")
}
