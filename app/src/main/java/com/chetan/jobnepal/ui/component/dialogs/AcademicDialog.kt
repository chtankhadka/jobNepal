package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage

@Composable
fun AcademicDialog(
    list: List<Pair<String, String>>,
    onDismissRequest: () -> Unit
) {
    Dialog(
        properties = DialogProperties(),
        onDismissRequest = onDismissRequest
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .fillMaxWidth()
                .fillMaxSize(0.9f)
                .padding(10.dp)
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 200.dp),
                content = {
                    items(list) {
                        Card(
                            modifier = Modifier
                                .size(200.dp)
                                .clickable { },
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = it.second,
                                    contentDescription = ""
                                )
                            }
                        }
                    }
                })

        }

    }
}
