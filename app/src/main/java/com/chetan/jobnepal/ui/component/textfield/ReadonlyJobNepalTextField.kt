package com.chetan.jobnepal.ui.component.textfield

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp

@Composable
fun ReadonlyJobNepalTextField(
    modifier: Modifier = Modifier,
    helperText: String? = null,
    value: String,
    onClick: () -> Unit,
    label: String? = null,
    required: Boolean? = null,
    enabled: Boolean = true,
    isError: Boolean = false,
    leadingIcon: @Composable (() -> Unit)? = null
) {

    val focusManager = LocalFocusManager.current

    Box(modifier = modifier) {
        TextFieldJobNepal(
            label = label,
            leadingIcon = leadingIcon,
            enabled = enabled,
            value = value,
            onValueChange = {},
            required = required,
            isError = isError,
            readOnly = true
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .clip(TextFieldDefaults.filledShape)
                .clickable(onClick = {
                    focusManager.clearFocus()
                    onClick()
                })
                .padding(start = 40.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            if (helperText != null) {
                Text(
                    text = helperText,
                    style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.outline)
                )
            }
        }
    }
}
