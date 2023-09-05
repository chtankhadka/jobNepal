package com.chetan.jobnepal.screens.admin.dashboard

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.NotificationAdd
import androidx.compose.material.icons.filled.Payments
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.chetan.jobnepal.utils.ProfileAnimation

@Composable
fun ModalDrawerSheetPage(
    state: AdminDashboardState,
    onEvent: (event: AdminDashboardEvent) -> Unit,
    onClick: (MenuItem) -> Unit,
) {
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
            Column(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {

                Text(text = state.currentUserName)
                Text(text = "Form Requested: 4")
                Text(text = "Attend Exam: 1")
            }

        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        val menuList = listOf(
            MenuItem.Notification,
            MenuItem.Offer,
            MenuItem.Payments,
            MenuItem.FormRequest,
            MenuItem.ChatRequest,
            MenuItem.Documents
        )

        menuList.forEach { menuItem ->
            Spacer(modifier = Modifier.height(5.dp))
            ElevatedCard(
                modifier = Modifier.clickable {
                    onClick(menuItem)
                },
                shape = RoundedCornerShape(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = menuItem.icon, contentDescription = "")
                    Text(text = menuItem.label)
                }
            }
        }
        Spacer(modifier = Modifier.height(5.dp))
    }
}

sealed class MenuItem(val icon: ImageVector, val label: String){
    object Notification : MenuItem(Icons.Default.NotificationAdd, "Send Notification")
    object Offer : MenuItem(Icons.Default.LocalOffer, "Offer")
    object Payments : MenuItem(Icons.Default.Payments, "Payments")
    object FormRequest: MenuItem(Icons.Default.MenuBook, "Form Request")
    object ChatRequest: MenuItem(Icons.Default.Chat,"Chat Request")
    object Documents : MenuItem(Icons.Default.LibraryBooks, "Documents")
}