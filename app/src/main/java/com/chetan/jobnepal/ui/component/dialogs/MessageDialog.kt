package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.chetan.jobnepal.R
import com.chetan.jobnepal.utils.StringValue


sealed class Message(
    open val image: Any,
    open val title: StringValue,
    open val description: String?,
    open val isCancellable: Boolean,
) {
    data class Success(
        override val image: Any = R.drawable.ic_launcher_background,
        override val title: StringValue = StringValue.DynamicString("Success"),
        override val description: String,
        override val isCancellable: Boolean = true,
    ) : Message(image, title, description, isCancellable)

    data class Error(
        override val image: Any = R.drawable.ic_launcher_background,
        override val title: StringValue = StringValue.DynamicString("Error"),
        override val description: String?,
        override val isCancellable: Boolean = true,
    ) : Message(image, title, description, isCancellable)


}

@Composable
fun MessageDialog(
    message: Message,
    onDismissRequest: (() -> Unit)?,
    onOK: () -> Unit
) {
    Dialog(onDismissRequest = {
        if (onDismissRequest != null) {
            onDismissRequest()
        }
    }) {
        val ctx = LocalContext.current
        Column(
            Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AsyncImage(
                model = message.image,
                modifier = Modifier.size(145.dp),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message.title.asString(ctx),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message.description?:"",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.outline)
            )
            Spacer(modifier = Modifier.height(34.dp))


        }
    }
}
