package com.chetan.jobnepal.ui.component.dropdown

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.chetan.jobnepal.screens.user.dashboard.DashboardEvent
import com.chetan.jobnepal.screens.user.dashboard.DashboardState

@Composable
fun DropdownJobNepalSetting(
    list: List<Triple<String, ImageVector, Boolean>>,
    state: DashboardState,
    onEvent: (event: DashboardEvent) -> Unit,
    onClick: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = !expanded }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "More"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            list.forEach { tripleValue ->
                Row(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DropdownMenuItem(
                        modifier = Modifier.weight(1f),
                        text = { Text(tripleValue.first) },
                        leadingIcon = {
                            Icon(
                                imageVector = tripleValue.second,
                                contentDescription = tripleValue.first
                            )
                        },
                        onClick = {
                                  if (tripleValue.first !="Nepali")
                                      onClick(tripleValue.first)
                        },
                    )
                    if (tripleValue.first == "Nepali"){
                        Switch(
                            modifier = Modifier
                                .semantics { contentDescription = "Demo" }
                            ,
                            checked = state.nepaliLanguage ,
                            onCheckedChange = {
                                onEvent(DashboardEvent.ChangeLanguage(it))
                                onClick(tripleValue.first)
                            })
                    }

                }

            }

        }
    }
}