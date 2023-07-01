package com.chetan.jobnepal.ui.component.dropdown

import android.widget.Toast
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedDropdownJobNepal(
    modifier: Modifier = Modifier,
    list: Array<String>
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
    ) {
        TextField(
            value = list[selectedItemIndex],
            onValueChange = {},
            readOnly = true,
            singleLine = true,
            label = null,
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
        )
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
                            selectedItemIndex = index
                            expanded = false
                            Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                        })
                }

            }
        }
    }
}