package com.chetan.jobnepal.screens.admin.formrequest

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chetan.jobnepal.data.models.formrequest.FormRequestJobDetails

@Composable
fun FormRequestJobDetailsDialog(
    listOfJobs: FormRequestJobDetails,
    onDismissListener: () -> Unit
) {

    Dialog(properties = DialogProperties(), onDismissRequest = { onDismissListener() }) {
        Card(
            modifier = Modifier
                .fillMaxHeight(0.9f)
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                    Text(text = listOfJobs.videoLink)
                    Card(
                        modifier = Modifier
                            .padding(10.dp),
                        elevation = CardDefaults.cardElevation(10.dp)
                    ) {
                        IconButton(
                            onClick = {
                                onDismissListener()
                            }) {
                            Icon(imageVector = Icons.Filled.Close, contentDescription = "")
                        }
                    }
                }

                listOfJobs.academicList.forEach { data ->
                    if (data.jobList.isNotEmpty()) {
                        Text(
                            text = data.listName + ": ",
                            style = MaterialTheme.typography.titleLarge
                        )
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState()),
                            verticalAlignment = Alignment.CenterVertically) {
                            data.jobList.forEach { jobList ->
                                Card(
                                    elevation = CardDefaults.cardElevation(10.dp)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(5.dp),
                                        text = jobList.jobName,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }


                        }
                        Divider()
                        Text(
                            text = "Levels :", style = MaterialTheme.typography.titleLarge
                        )
                        Row(modifier = Modifier.fillMaxWidth().horizontalScroll(rememberScrollState())) {
                            data.levels.forEach { levels ->
                                Card(
                                    elevation = CardDefaults.cardElevation(10.dp)
                                ) {
                                    Text(
                                        modifier = Modifier.padding(5.dp),
                                        text = levels.levelName,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                }
                            }

                        }
                    }

                }
            }
        }
    }
}



