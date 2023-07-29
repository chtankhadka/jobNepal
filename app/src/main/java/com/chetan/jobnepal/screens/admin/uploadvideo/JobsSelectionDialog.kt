import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PlainTooltipBox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.chetan.jobnepal.ui.component.CustomTooltipShape

@Composable
fun JobsSelectionDialog() {
    val listOfJobs = listOf(
        "Technical" to listOf(
            "opal",
            "gopal fgsdfgsdf daasdf adf a asfdfa",
            "sopal skldfklg js fjsdflkgj;lsdfjg",
            "opal",
            "gopal",
            "sopal"
        ),
        "Nechnical" to listOf("1", "2", "3"),
        "Pechnical" to listOf("4", "5", "6"),
        "Khecknical" to listOf("7", "8", "9"),
        "Jhecknical" to listOf("opal", "gopal", "sopal"),
    )
    Dialog(
        properties = DialogProperties(),
        onDismissRequest = {}
    ) {
        Box() {
            LazyColumn(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxHeight(0.9f)
                    .fillMaxWidth()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                items(listOfJobs.size) { index ->
                    var cardSize by remember { mutableIntStateOf(0) }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .onSizeChanged {
                                cardSize = it.height
                            }
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = cardSize.toString(),
                                modifier = Modifier.padding(horizontal = 2.dp),
                                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold)
                            )
                            Checkbox(checked = true, onCheckedChange = { })

                        }
                        Divider(modifier = Modifier.padding(bottom = 5.dp))

                        VerticalGrid(columnCount = 3) {
                            listOfJobs[index].second.map {
                                {
                                    checkBox(item = it)
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))

                    }
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun checkBox(
    item: String
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
            modifier = Modifier
                .tooltipTrigger(),
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
                    isSelected = it
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

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun showNow() {
    JobsSelectionDialog()
}



