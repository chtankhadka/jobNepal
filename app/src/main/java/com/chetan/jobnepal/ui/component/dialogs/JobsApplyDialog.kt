import android.widget.AutoCompleteTextView.OnDismissListener
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DownloadDone
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chetan.jobnepal.screens.admin.uploadvideo.UploadVideoEvent
import com.chetan.jobnepal.screens.dashboard.DashboardEvent
import com.chetan.jobnepal.ui.component.CustomTooltipShape
import com.chetan.jobnepal.ui.component.IconJobNepal

@Composable
fun JobsApplyDialog(
    listOfJobs: List<Triple<String, List<String>, List<String>>>,
    onEvent: (event: DashboardEvent) -> Unit,
    onApplied: () -> Unit,
    onDismissListener: () -> Unit
) {

    Dialog(
        properties = DialogProperties(),
        onDismissRequest = {onDismissListener()}
    ) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 3.dp, end = 3.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconJobNepal(
                    onClick = {
                        onApplied()
                    },
                    icon = Icons.Default.DownloadDone
                )
            }
            LazyColumn(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(listOfJobs) {tipleList->
                    var selectedList by remember { mutableStateOf(listOf<String>()) }
                    var selectedLevels by remember {
                        mutableStateOf(listOf<String>())
                    }
                    var isShowLevels by remember {
                        mutableStateOf(false)
                    }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tipleList.first,
                                modifier = Modifier.padding(horizontal = 2.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Row(
                                modifier = Modifier,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(text = "Select All")
                                Checkbox(
                                    checked = selectedList.containsAll(tipleList.second),
                                    onCheckedChange = {
                                        if (it) {
                                            selectedList = tipleList.second.toMutableList()
                                        } else {
                                            selectedList = emptyList()
                                        }
                                        onEvent(DashboardEvent.UpdateCheckedList(tipleList.first,selectedList,selectedLevels))
                                    })
                            }
                        }
                        Divider(modifier = Modifier.padding(bottom = 5.dp))

                        VerticalGrid(columnCount = 3) {
                            tipleList.second.map { item ->
                                {
                                    checkBox(
                                        item = item,
                                        isSelected = selectedList.contains(item),
                                        onChange = { changed ->
                                            selectedList = if (changed) {
                                                selectedList + item
                                            } else {
                                                selectedList - item
                                            }
                                            onEvent(DashboardEvent.UpdateCheckedList(tipleList.first,selectedList,selectedLevels))
                                        }
                                    )
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                        Divider()
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 5.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = tipleList.first,
                                modifier = Modifier.padding(horizontal = 2.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            IconButton(
                                onClick = {
                                    isShowLevels = !isShowLevels
                                },
                                content = {
                                    Icon(
                                        imageVector = if (isShowLevels) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                                        contentDescription = ""
                                    )
                                },
                            )

                        }
                        Divider(modifier = Modifier.padding(bottom = 10.dp))
                        if (isShowLevels) {
                            LazyRow(
                                contentPadding = PaddingValues(5.dp),
                                horizontalArrangement = Arrangement.spacedBy(10.dp),
                                content = {
                                    items(tipleList.third) { value ->
                                        LevelSelectionCheckBox(
                                            item = value,
                                            onChange = {
                                                selectedLevels =
                                                    if (it) {
                                                        selectedLevels + value
                                                    } else {
                                                        selectedLevels - value
                                                    }
                                                onEvent(
                                                    DashboardEvent.UpdateCheckedList(
                                                        tipleList.first,
                                                        selectedList,
                                                        selectedLevels
                                                    )
                                                )
                                            }
                                        )

                                    }
                                })
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun checkBox(
    item: String,
    isSelected: Boolean,
    onChange: (Boolean) -> Unit
) {
    PlainTooltipBox(
        tooltip = {
            Text(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 15.dp),
                text = item
            )

        },
        shape = CustomTooltipShape()
    ) {
        Card(
            modifier = Modifier.tooltipTrigger(),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.width(70.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(checked = isSelected, onCheckedChange = {
                    onChange(it)
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LevelSelection(
    item: String,
    onChange: (Boolean) -> Unit
) {
    var isSelected by remember {
        mutableStateOf(false)
    }
    PlainTooltipBox(
        tooltip = {
            Text(
                modifier = Modifier.padding(start = 5.dp, end = 5.dp, bottom = 15.dp),
                text = item
            )
        },
        shape = CustomTooltipShape()
    ) {
        Card(
            modifier = Modifier.tooltipTrigger(),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.width(50.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = item,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Checkbox(checked = isSelected, onCheckedChange = {
                    isSelected = it
                    onChange(it)
                })
            }
        }
    }
}

@Composable
fun VerticalGrid(columnCount: Int, items: () -> List<(@Composable () -> Unit)>) {
    Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(5.dp)) {
        items.invoke().windowed(columnCount, columnCount, true).forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                it.forEach {
                    it.invoke()
                }
            }
        }
    }
}



