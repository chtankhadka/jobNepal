package com.chetan.jobnepal.screens.user.dashboard

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
import androidx.compose.material.icons.filled.Contacts
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chetan.jobnepal.Destination
import com.chetan.jobnepal.screens.user.dashboard.myForm.MyForm
import com.chetan.jobnepal.ui.component.dropdown.DropdownJobNepalSetting
import com.chetan.jobnepal.utils.ProfileAnimation

@Composable
fun ModalDrawerSheetPage(
    navController: NavHostController,
    state: DashboardState,
    onClick: (String) -> Unit,
    onEvent: (event: DashboardEvent) -> Unit,

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
            ProfileAnimation(size = 100.dp,padding = 10.dp, uri = state.profileUrl,enableEdit = false)
            Column(
                modifier = Modifier.padding(vertical = 10.dp)
            ) {

                Text(text = state.currentUserName)
                Text(text = "Form Requested: 4")
                Text(text = "Attend Exam: 1")
            }
            val list = listOf(
                Triple("Nepali", Icons.Default.Language,true),
                Triple("Contacts" , Icons.Default.Contacts,true),
                Triple("Logout" , Icons.Default.Logout,true),
                )
            DropdownJobNepalSetting(list,state,onEvent,onClick ={
                    if (it == "Logout"){
                        onEvent(DashboardEvent.Logout)
                        onClick("logout")
                    }else if (it=="Nepali"){
                        onClick("Nepali")
                    }
            })

        }
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
        )
        val menuList = listOf(
            Icons.Default.Home to "Profile",
            Icons.Default.Contacts to "Documents"
        )

        menuList.forEach {
            Spacer(modifier = Modifier.height(5.dp))
            ElevatedCard(
                modifier = Modifier.clickable {
                  if (it.second == "Documents")  {
                      navController.navigate(Destination.Screen.Academic.route)
                  }
                    if (it.second == "Profile"){
                        navController.navigate(Destination.Screen.ProfileScreen.route)
                    }
                },
                shape = RoundedCornerShape(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 7.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Icon(imageVector = it.first, contentDescription = "")
                    Text(text = it.second)
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
            MyForm(state,onEvent)
        }
    }
}