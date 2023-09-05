package com.chetan.jobnepal.screens.admin.formrequest

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun FormRequestJobDetailsDialog(
    jobDetails: String,
    onDismissListener: () -> Unit
) {

    Dialog(properties = DialogProperties(), onDismissRequest = { onDismissListener() }) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(MaterialTheme.colorScheme.background)
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(text = jobDetails, modifier = Modifier.padding(vertical = 10.dp))


            }
        }
    }
}



