package com.chetan.jobnepal.ui.component.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Downloading
import androidx.compose.material.icons.filled.House
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.chetan.jobnepal.R
import com.chetan.jobnepal.utils.LoadLottieAnimation
import com.chetan.jobnepal.utils.StringValue


sealed class Message(
    open val image: ImageVector?,
    open val lottieImage: Int?,
    open val yesNoRequired: Boolean,
    open val positiveButton: StringValue,
    open val negativeButton: StringValue,
    open val title: StringValue,
    open val description: String?,
    open val isCancellable: Boolean,
) {
    data class Success(
        override val image: ImageVector? = null,
        override val lottieImage: Int? = null,
        override val yesNoRequired: Boolean = true,
        override val positiveButton: StringValue = StringValue.DynamicString("Yes"),
        override val negativeButton: StringValue = StringValue.DynamicString("No"),
        override val title: StringValue = StringValue.DynamicString("Success"),
        override val description: String,
        override val isCancellable: Boolean = true,
    ) : Message(image, lottieImage, yesNoRequired,positiveButton ,negativeButton,title, description, isCancellable)

    data class Loading(
        override val image: ImageVector? = null,
        override val lottieImage: Int? = null,
        override val yesNoRequired: Boolean = true,
        override val positiveButton: StringValue = StringValue.DynamicString("Yes"),
        override val negativeButton: StringValue = StringValue.DynamicString("No"),
        override val title: StringValue = StringValue.DynamicString("Loading"),
        override val description: String,
        override val isCancellable: Boolean = true,
    ): Message(image,lottieImage,yesNoRequired,positiveButton,negativeButton,title,description,isCancellable)

    data class Error(
        override val image: ImageVector? = null,
        override val lottieImage: Int? = null,
        override val yesNoRequired: Boolean = true,
        override val positiveButton: StringValue = StringValue.DynamicString("Yes"),
        override val negativeButton: StringValue = StringValue.DynamicString("No"),
        override val title: StringValue = StringValue.DynamicString("Error"),
        override val description: String?,
        override val isCancellable: Boolean = true,
    ) : Message(image,lottieImage,yesNoRequired,positiveButton,negativeButton, title, description, isCancellable)


}

@Composable
fun MessageDialog(
    message: Message,
    onDismissRequest: (() -> Unit)?,
    onPositive: () -> Unit,
    onNegative: () -> Unit
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
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = message.title.asString(ctx),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge
            )
//            AsyncImage(
//                model = message.image,
//                modifier = Modifier.size(145.dp),
//                contentDescription = null
//            )
            if (message.lottieImage != null){
                LoadLottieAnimation(
                    modifier = Modifier.size(200.dp) ,
                    image = message.lottieImage!!
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = message.description?:"",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.outline)
            )
            Spacer(modifier = Modifier.height(34.dp))
            if (message.yesNoRequired){
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp).also { Arrangement.Center }){
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {
                    }) {
                        Text(text = "Okay")
                    }
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = {

                    }) {
                        Text(text = "Cancel")
                    }
                }

            }


        }
    }
}
