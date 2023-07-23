package com.chetan.jobnepal.ui.component.dialogs

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.AsyncImage
import com.chetan.jobnepal.screens.academic.AcademicState
import com.chetan.jobnepal.ui.component.IconJobNepal

@Composable
fun AcademicDialog(
    state: AcademicState,
    list: List<Pair<String, String>>,
    onDismissRequest: () -> Unit,
    onClick: (List<Uri>) -> Unit
) {
    var selectedImageUris by remember {
        mutableStateOf<List<Uri>>(emptyList())
    }
    val multiplePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(),
        onResult = { uris ->
            selectedImageUris = uris

        })

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
                .padding(10.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .shadow(4.dp, shape = RoundedCornerShape(20))
                        .clip(RoundedCornerShape(20))
                        .background(MaterialTheme.colorScheme.tertiaryContainer)
                        .height(32.dp)
                        .weight(1f)
                        .clickable {
                            onDismissRequest()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        modifier = Modifier.height(IntrinsicSize.Min),
                        text = state.selectedLevel,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Box(
                    modifier = Modifier
                        .shadow(4.dp, shape = RoundedCornerShape(20))
                        .clip(RoundedCornerShape(20))
                        .background(MaterialTheme.colorScheme.secondaryContainer)
                        .height(IntrinsicSize.Min)
                        .clickable {
                            onDismissRequest()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        modifier = Modifier.size(32.dp),
                        imageVector = Icons.Default.Close,
                        contentDescription = "Close",
                        tint = Color.Black
                    )
                }
            }
            LazyVerticalGrid(
                modifier = Modifier.fillMaxSize(0.7f),
                columns = GridCells.Adaptive(minSize = 100.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                content = {
                    items(selectedImageUris) { uri ->
                        Card(
                            modifier = Modifier
                                .size(100.dp)
                                .clickable { },
                            shape = RoundedCornerShape(10.dp),
                            elevation = CardDefaults.cardElevation(2.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                AsyncImage(
                                    model = uri,
                                    contentDescription = ""
                                )
                                IconJobNepal(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd),
                                    icon = Icons.Default.Close,
                                    onClick = {
                                        selectedImageUris =
                                            selectedImageUris.filterNot { it == uri }
                                    }
                                )

                            }
                        }
                    }
                })
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.3f),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (!selectedImageUris.isEmpty()){
                    Button(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        onClick = {
                            onClick(selectedImageUris)
                        }) {
                        Text(text = "Upload your Attachments")
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                }
                

                FloatingActionButton(
                    modifier = Modifier.height(IntrinsicSize.Min),
                    shape = RoundedCornerShape(16.dp),
                    onClick = {
                        multiplePhotoPickerLauncher.launch(
                            PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                        )
                    },
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "add"
                    )
                }
            }

        }

    }
}

