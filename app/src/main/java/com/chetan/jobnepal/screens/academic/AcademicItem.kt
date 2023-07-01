package com.chetan.jobnepal.screens.academic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal

@Composable
fun AcademicItem(list: List<Pair<String, String>>) {
    Card(
        modifier = Modifier
            .padding(start = 5.dp),
        shape = RoundedCornerShape(topStart = 5.dp, topEnd = 0.dp, bottomStart = 5.dp, bottomEnd = 0.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 5.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "SEE")
            DropdownJobNepal(
                list = listOf(
                    "Edit" to Icons.Default.Edit,
                    "Delete" to Icons.Default.Delete
                )
            )
            Divider()
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ){
            items(list.size){
                    AsyncImage(
                        modifier = Modifier.height(200.dp),
                        model = list[it].second, contentDescription = "",
                        contentScale = ContentScale.Crop
                        )
            }
        }
    }
}