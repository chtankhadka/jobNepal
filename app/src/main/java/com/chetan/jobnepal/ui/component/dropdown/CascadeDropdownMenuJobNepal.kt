package com.chetan.jobnepal.ui.component.dropdown


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chetan.jobnepal.R
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.maxkeppeker.sheets.core.icons.twotone.ExpandMore
import me.saket.cascade.CascadeDropdownMenu

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CascadeDropdownMenuJobNepal(
    expanded: Boolean = false,
    onLevelSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {

    CascadeDropdownMenu(expanded = expanded,
        onDismissRequest = {
            onDismiss()
        }) {
        androidx.compose.material3.DropdownMenuItem(text = { Text(text = "1.SLC/SEE") },
            onClick = {
                onLevelSelected("see")
                onDismiss()
            })

        //Intermediate
        androidx.compose.material3.DropdownMenuItem(text = { Text(text = "2. +2/PCL") },
            onClick = {
                onLevelSelected("intermediate")
                onDismiss()
            })

        //Bachelors
        androidx.compose.material3.DropdownMenuItem(text = { Text(text = "3.Bachelors") },
            onClick = {
                onLevelSelected("bachelors")
                onDismiss()
            })

        androidx.compose.material3.DropdownMenuItem(text = { Text(text = "4.Masters") },
            onClick = {
                onLevelSelected("masters")
                onDismiss()
            })

        DropdownMenuItem(text = { Text(text = "5.Other Docs") }, children = {
            androidx.compose.material3.DropdownMenuItem(
                text = {
                    Text(text = "1."+ stringResource(R.string.citizenship))
                },
                onClick = {
                    onLevelSelected("citizenship")
                    onDismiss()
                })
            androidx.compose.material3.DropdownMenuItem(
                text = { Text(text = "2."+ stringResource(R.string.experience)) },
                onClick = {
                    onLevelSelected("experience")
                    onDismiss()
                })
            androidx.compose.material3.DropdownMenuItem(
                text = { Text(text = "3."+ stringResource(R.string.training)) },
                onClick = {
                    onLevelSelected("training")
                    onDismiss()
                })

            androidx.compose.material3.DropdownMenuItem(
                text = { Text(text = "4."+ stringResource(R.string.signature)) },
                onClick = {
                    onLevelSelected("signature")
                    onDismiss()
                })

            androidx.compose.material3.DropdownMenuItem(
                text = { Text(text = "5."+ stringResource(R.string.others)) },
                onClick = {
                    onLevelSelected("others")
                    onDismiss()
                })

        })
    }
}


