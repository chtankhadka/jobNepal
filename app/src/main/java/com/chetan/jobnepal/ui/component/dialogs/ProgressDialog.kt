package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chetan.jobnepal.R
import com.chetan.jobnepal.utils.StringValue

data class Progress(
    val cancellable: Boolean = true,
    val isSearching: Boolean = false,
    val title: StringValue = StringValue.StringResource(R.string.app_name),
    val value: Float
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressDialog(
    progress: Progress,
    onCancelProgressRequest: (() -> Unit)
) {
    val ctx = LocalContext.current
    if (progress.value != 0F) {
        Dialog(onDismissRequest = { }) {
        }
        LinearProgressIndicator(
            modifier = Modifier.fillMaxWidth(),
            progress = progress.value
        )
    } else {
        Dialog(
            onDismissRequest = { },
            properties = remember { DialogProperties(usePlatformDefaultWidth = false) }
        ) {
            Box(Modifier.fillMaxSize()) {

                    CircularProgressIndicator(Modifier.align(Alignment.Center))
                }
            }

    }
}
