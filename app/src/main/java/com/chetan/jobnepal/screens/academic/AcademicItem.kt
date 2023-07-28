package com.chetan.jobnepal.screens.academic

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepal

@Composable
fun AcademicItem(
    title: String = "",
    data: MutableList<MappedList>,
    onClick: (String?,List<String>) -> Unit,
    showEdit: Boolean,
) {
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
            Text(text = title)
            DropdownJobNepal(
                list = listOf(
                    Triple("Edit" , Icons.Default.Edit,true),
                    Triple("Delete" , Icons.Default.Delete,true)
                )
            ){
                onClick(it, data.map { it.name })
            }
            Divider()
        }
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(5.dp)
        ){

            items(data.size){
                Box(
                    modifier = Modifier.size(200.dp)
                        .clip(RoundedCornerShape(5.dp))
                ){
                    AsyncImage(
                        model = data[it].url, contentDescription = "",
                        contentScale = ContentScale.Crop
                    )
                    if (showEdit){
                        IconJobNepal(
                            modifier = Modifier.align(Alignment.TopEnd),
                            onClick = {
                                onClick(null, listOf(data[it].name))
                                data.removeAt(it)
                            },
                            icon = Icons.Default.Delete)
                    }

                }
            }
        }
    }
}