package com.chetan.jobnepal.screens.admin.adminbottomsheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.ui.component.textfield.TextFieldJobNepal

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdminBottomSheetScreen(
    navController: NavHostController,
    state: AdminBottomSheetState,
    onEvent: (event: AdminBottomSheetEvent) -> Unit
) {
    val ctx = LocalContext.current
    Scaffold(topBar = {
        CenterAlignedTopAppBar(modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp), navigationIcon = {
            IconJobNepal(
                onClick = {
                    navController.popBackStack()
                }, icon = Icons.Default.ArrowBack
            )
        }, title = {
            Text(
                text = "Update Notice", style = MaterialTheme.typography.titleLarge
            )
        })
    }, content = {
        Column(modifier = Modifier.padding(it)) {

            state.infoMsg?.let {
                MessageDialog(message = it, onDismissRequest = {
                    if (onEvent != null && state.infoMsg.isCancellable == true) {
                        onEvent(AdminBottomSheetEvent.DissmissInfoMsg)
                    }
                }, onPositive = { }, onNegative = { })
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                TextFieldJobNepal(
                    modifier = Modifier.weight(1f),
                    singleLine = false,
                    value = state.totalCost,
                    label = "Total Cost",
                    onValueChange = {
                        onEvent(AdminBottomSheetEvent.OnTotalCostChange(it))
                    })
                TextFieldJobNepal(
                    modifier = Modifier.weight(1f),
                    value = state.discountPercentage, onValueChange = {
                        onEvent(AdminBottomSheetEvent.OnDiscountPercentageChange(it))
                    }, label = "Discount %"
                )
            }

            Button(onClick = {
                onEvent(AdminBottomSheetEvent.UpdateBottomSheetNotice)
            }) {
                Text(text = "Update Notice")

            }
        }
    })
}