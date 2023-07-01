package com.chetan.jobnepal.ui.component.dropdown


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chetan.jobnepal.ui.component.IconJobNepal
import me.saket.cascade.CascadeDropdownMenu

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CascadeDropdownMenuJobNepal(
    onLevelSelected: () -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconJobNepal(onClick = {
            expanded = true
        }, icon = Icons.Default.AddBox)
        CascadeDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(text = { Text(text = "1. Item") },
                children = {
                    androidx.compose.material3.DropdownMenuItem(
                        text = { Text(text = "1.1. Sub-Item") },
                        onClick = {
                            onLevelSelected()
                            expanded = false
                        }
                    )
                }
            )
            DropdownMenuItem(
                text = { Text(text = "2. Item") },
                children = {
                    androidx.compose.material3.DropdownMenuItem(
                        text = { Text(text = "2.2 Sub-Item") },
                        onClick = {
                            onLevelSelected()
                            expanded = false
                        })
                    androidx.compose.material3.DropdownMenuItem(
                        text = { Text(text = "2.3 Sub-Item") },
                        onClick = {
                            onLevelSelected()
                            expanded = false
                        })
                    DropdownMenuItem(
                        text = { Text(text = "2.4 Sub-Item") },
                        children = {
                            androidx.compose.material3.DropdownMenuItem(
                                text = { Text(text = "2.4.1") },
                                onClick = {
                                    onLevelSelected()
                                    expanded = false
                                }
                            )
                        },
                        childrenHeader = {
                            Text(text = "Ulaal")
                        }
                    )
                }

            )


        }
    }
}
