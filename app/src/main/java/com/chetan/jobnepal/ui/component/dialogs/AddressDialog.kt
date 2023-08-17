package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties

@Composable
fun AddressDialog(
    addressList: MutableList<String>,
    onDismissRequest: () -> Unit,
    onClick: (String) -> Unit
) {
    Dialog(
        properties = DialogProperties(),
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.primary)
                .fillMaxWidth()
                .fillMaxSize(0.9f)
                .padding(10.dp),
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                items(addressList){
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onClick(it)
                            },
                        text = it
                    )
                }
            })

        }

    }
}

