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
    onLevelSelected: (String) -> Unit
) {
    var expanded by remember {
        mutableStateOf(false)
    }
    Box(
        modifier = Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconJobNepal(onClick = {
            expanded = true
        }, icon = Icons.Default.AddBox)
        CascadeDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            androidx.compose.material3.DropdownMenuItem(text = { Text(text = "1.SLC/SEE") },
                onClick = {
                    onLevelSelected("SLC/SEE")
                    expanded = false
                })

            //Intermediate
            DropdownMenuItem(text = { Text(text = "2.Intermediate") }, children = {
                DropdownMenuItem(text = { Text(text = "1.Technical") }, children = {
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "1.HA") },
                        onClick = {
                            onLevelSelected("HA")
                            expanded = false
                        })
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "2.Nursing") },
                        onClick = {
                            onLevelSelected("Nursing")
                            expanded = false
                        })
                })

                DropdownMenuItem(text = { Text(text = "2.Non-Technical") }, children = {
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "1.IAC") },
                        onClick = {
                            onLevelSelected("IAC")
                            expanded = false
                        })
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "2.Education") },
                        onClick = {
                            onLevelSelected("Education")
                            expanded = false
                        })
                })
            })

            //Bachelors

            DropdownMenuItem(text = { Text(text = "2.Bachelors") }, children = {
                DropdownMenuItem(text = { Text(text = "1.Technical") }, children = {
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "1.BSc.CSIT") },
                        onClick = {
                            onLevelSelected("BSc.CSIT")
                            expanded = false
                        })
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "2.BSC-Nursing") },
                        onClick = {
                            onLevelSelected("BSC-Nursing")
                            expanded = false
                        })
                })

                DropdownMenuItem(text = { Text(text = "2.Non-Technical") }, children = {
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "1.BSC") },
                        onClick = {
                            onLevelSelected("BSC")
                            expanded = false
                        })
                    androidx.compose.material3.DropdownMenuItem(text = { Text(text = "2.BBA") },
                        onClick = {
                            onLevelSelected("BBA")
                            expanded = false
                        })
                })
            })
        }
    }
}
