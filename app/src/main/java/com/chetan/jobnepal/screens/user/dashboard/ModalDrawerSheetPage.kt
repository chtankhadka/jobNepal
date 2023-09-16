package com.chetan.jobnepal.screens.user.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Book
import androidx.compose.material.icons.filled.Details
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.PrivacyTip
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.R
import com.chetan.jobnepal.screens.user.dashboard.myForm.MyForm
import com.chetan.jobnepal.ui.component.dialogs.AboutUsDialog
import com.chetan.jobnepal.ui.component.dialogs.PrivacyPolicyDialog
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepalSetting
import com.chetan.jobnepal.utils.ProfileAnimation

@Composable
fun ModalDrawerSheetPage(
    navController: NavHostController,
    state: DashboardState,
    onClick: (String) -> Unit,
    onEvent: (event: DashboardEvent) -> Unit,
) {

    var showDialog by remember {
        mutableStateOf(false)
    }
    if (showDialog) {
        PrivacyPolicyDialog(onDismiss = {
            showDialog = it
        })
    }
    var showAboutUsDialog by remember {
        mutableStateOf(false)
    }
    if (showAboutUsDialog) {
        AboutUsDialog(onDismiss = {
            showAboutUsDialog = it
        })
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .fillMaxHeight(),
        verticalArrangement = Arrangement.Top
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            ProfileAnimation(
                size = 100.dp,
                padding = 10.dp,
                uri = state.profileUrl,
                enableEdit = false
            )
            Box(modifier = Modifier
                .padding(vertical = 10.dp,)) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 10.dp,)
                        .height(70.dp),
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(text = state.currentUserName,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                val list = listOf(
                    SettingItem.Nepali,
                    SettingItem.About_Us,
                    SettingItem.Privacy_Policy,
                    SettingItem.Logout
                )
                DropdownJobNepalSetting(modifier = Modifier.align(Alignment.TopEnd),list = list, state = state, onEvent = onEvent, onClick = {
                    when (it) {
                        SettingItem.Nepali -> {
                            onEvent(DashboardEvent.Logout)
                            onClick("Nepali")
                        }

                        SettingItem.About_Us -> {
                            showAboutUsDialog = true
                        }

                        SettingItem.Logout -> {
                            onClick("logout")
                        }

                        SettingItem.Privacy_Policy -> {
                            showDialog = true
                        }
                    }
                })
            }


        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        val menuList = listOf(
            MenuItem.Profile,
            MenuItem.Documents
        )

        menuList.forEach {
            Spacer(modifier = Modifier.height(5.dp))
            ElevatedCard(
                modifier = Modifier.clickable {
                    when (it) {
                        MenuItem.Profile -> {
                            navController.navigate(Destination.Screen.ProfileScreen.route)
                        }

                        MenuItem.Documents -> {
                            navController.navigate(Destination.Screen.Academic.route)
                        }
                    }
                },
                shape = RoundedCornerShape(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = it.icon, contentDescription = "")
                    Text(text = stringResource(id = it.label))
                }
            }

        }
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            Arrangement.Bottom
        ) {
            MyForm(state, onEvent, navController)
        }
    }
}


sealed class MenuItem(val icon: ImageVector, val label: Int) {
    object Profile : MenuItem(Icons.Default.ManageAccounts, R.string.profile)
    object Documents : MenuItem(Icons.Default.Book, R.string.documents)
}

sealed class SettingItem(val label: Int, val icon: ImageVector, val active: Boolean) {
    object Nepali : SettingItem(R.string.language, Icons.Default.Language, true)
    object About_Us : SettingItem(R.string.about_us, Icons.Default.Details, true)
    object Privacy_Policy : SettingItem(R.string.privacy_policy, Icons.Default.PrivacyTip, true)
    object Logout : SettingItem(R.string.logout, Icons.Default.Logout, true)
}