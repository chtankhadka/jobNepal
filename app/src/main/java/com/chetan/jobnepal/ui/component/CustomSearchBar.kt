package com.chetan.jobnepal.ui.component

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    expand : Boolean = false,
    textStyle: TextStyle = TextStyle.Default,
    placeholder: String,
    modifier: Modifier,
){
    var showHistory by remember {
        mutableStateOf(false)
    }
    val focusRequester = remember { FocusRequester()}
        BasicTextField(
        modifier = modifier,
        value = query,
        singleLine = true,
        onValueChange = onQueryChange,
        textStyle = textStyle,
        decorationBox = {
            Box(modifier = Modifier
                .fillMaxWidth(0.65f)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(12.dp)
                .animateContentSize(),
                contentAlignment = Alignment.TopCenter
            ){
                Column(modifier) {
                    Row(modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row(modifier = Modifier.fillMaxWidth(0.90f),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)) {
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
                        Icon(imageVector = if (showHistory) Icons.Default.KeyboardArrowUp else Icons.Default.History,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(20.dp).clickable {
                                showHistory = !showHistory
                            })

                    }

                    Divider()
                    if (showHistory){
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(0.2f)) {
                            listOf("nepal", "Gopal", "Sopal").forEach {
                                Text(text = it)
                            }
                        }
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