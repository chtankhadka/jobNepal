package com.chetan.jobnepal.ui.component.dropdown

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownJobNepal(
    modifier: Modifier = Modifier,
    list: Array<String>,
    onClick: (String) ->Unit
) {
    val context = LocalContext.current
    var expanded by remember {
        mutableStateOf(false)
    }
    var selectedItemIndex by remember {
        mutableIntStateOf(0)
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(horizontal = 5.dp, vertical = 8.dp),
    ) {
        BasicTextField(
            value = list[selectedItemIndex],
            onValueChange = {
            },
            readOnly = true,
            singleLine = true,
            modifier = Modifier.menuAnchor(),
            decorationBox = {innerTextField ->
                Row (
                    modifier,

                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                    ){
                    innerTextField()
                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)

                }
            }
        )
//        TextField(
//            value = list[selectedItemIndex],
//            onValueChange = {},
//            readOnly = true,
//            singleLine = true,
//            label = null,
//            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//            modifier = Modifier.menuAnchor()
//        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEachIndexed { index, item ->
                if (index != 0) {
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = item,
                                fontWeight = if (index == selectedItemIndex) FontWeight.Bold else null
                            )
                        },
                        onClick = {
                            onClick(item)
                            selectedItemIndex = index
                            expanded = false
                        })
                }

            }
        }
    }
}