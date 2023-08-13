package com.chetan.jobnepal.ui.component.textfield

import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit

@Composable
fun CustomBasicTextFieldJobNepal(
    modifier: Modifier = Modifier,
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    placeHolderText: String = "Here",
    fontSize : TextUnit = MaterialTheme.typography.bodyMedium.fontSize
){
    var text by rememberSaveable { mutableStateOf("") }
    BasicTextField(
        modifier = modifier,
        value = text,
        onValueChange = {
            text = it
        },
//        singleLine = singleLine,
//        textStyle = textStyle,

        ) {

    }

}