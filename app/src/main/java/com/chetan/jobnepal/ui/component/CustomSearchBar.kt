package com.chetan.jobnepal.ui.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.Placeholder
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String,
//    onSearch: (String) -> Unit,
//    active: Boolean,
//    onActiveChange: (Boolean) -> Unit,
    modifier: Modifier,
//    leadingIcon: @Composable() (() -> Unit)?,
//    trailingIcon: @Composable() (() -> Unit)?,
//    shape: Shape,
//    colors: SearchBarColors,
//    tonalElevation: Dp,
//    interactionSource: MutableInteractionSource,
//    content: @Composable() ColumnScope.() -> Unit
){
    BasicTextField(
        modifier = modifier,
        value = query,
        onValueChange = onQueryChange,
        textStyle = textStyle,
        decorationBox = {
            Box(modifier = Modifier
                .fillMaxWidth(0.65f)
                .border(
                    border = BorderStroke(
                        1.dp,
                        MaterialTheme.colorScheme.outlineVariant
                    ),
                    shape = RoundedCornerShape(15)
                )
                .padding(12.dp),
                contentAlignment = Alignment.Center
            ){
                Column {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                            Icon(imageVector = Icons.Default.Search,
                                contentDescription = "",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp),
                            )
                            if (query.isEmpty()){
                                Text(
                                    text = placeholder,
                                    style = MaterialTheme.typography.titleMedium.copy(color = MaterialTheme.colorScheme.outline)
                                )
                            }else{
                                it()
                            }
                        }
                        Icon(imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp),)

                    }

                }



            }
        }
    )

}

@Composable
@Preview
fun check(){
    var query by remember {
        mutableStateOf("")
    }
    CustomSearchBar(query = query, onQueryChange = {
            query = it
    },
        modifier = Modifier,
        placeholder = "Search Here"
        )
}