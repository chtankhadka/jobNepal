package com.chetan.jobnepal.ui.component.textfield

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.toSize


@Composable
fun <T> EnumTextFieldJobNepal(
    modifier : Modifier = Modifier,
    options: Iterable<T>,
    asString: T.() -> String,
    label: String?,
    value: T,
    onValueChange: (T) -> Unit,
    required: Boolean?,
    placeHolder: String? = null,
    leadingIcon: @Composable (() -> Unit)? = null

){
    var expanded by remember {
        mutableStateOf(false)
    }
    var textFieldSize by remember {
        mutableStateOf(Size.Zero)
    }
    val source = remember { MutableInteractionSource()}
    if (source.collectIsPressedAsState().value){
        expanded = true
    }

    Column() {
        TextFieldJobNepal(
            modifier = modifier
                .onGloballyPositioned { coordinate ->
                    textFieldSize = coordinate.size.toSize()
                },
            trailingIcon = {
                Icon(
                    if (expanded) Icons.Filled.ArrowDropUp else Icons.Filled.ArrowDropDown,
                    "Dropdown Arrow"
                )
            },
            readOnly = true,
            value = value.asString(),
            onValueChange = {},
            placeholder = placeHolder,
            leadingIcon = leadingIcon,
            required = required,
            label = label,
            interactionSource = source
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = {
                expanded = false
            },
            modifier = Modifier
                .width(with(LocalDensity.current) { textFieldSize.width.toDp() })
        ) {
            options.forEach {
                DropdownMenuItem(onClick = {
                    onValueChange(it)
                    expanded = false
                }, text = {
                    Text(text = it.asString())
                })
            }
        }
    }
}