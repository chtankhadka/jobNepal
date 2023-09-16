package com.chetan.jobnepal.screens.user.account

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chetan.jobnepal.R
import com.chetan.jobnepal.data.enums.Gender
import com.chetan.jobnepal.screens.user.dashboard.DashboardEvent
import com.chetan.jobnepal.ui.component.IconJobNepal
import com.chetan.jobnepal.ui.component.animation.YouCannotClickMe
import com.chetan.jobnepal.ui.component.dialogs.AddressDialog
import com.chetan.jobnepal.ui.component.dialogs.MessageDialog
import com.chetan.jobnepal.ui.component.textfield.EnumTextFieldJobNepal
import com.chetan.jobnepal.ui.component.textfield.ReadonlyJobNepalTextField
import com.chetan.jobnepal.ui.component.textfield.TextFieldJobNepal
import com.chetan.jobnepal.utils.JsonReader
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavHostController, state: ProfileState, onEvent: (event: ProfileEvent) -> Unit
) {
    var addressDialog by remember {
        mutableStateOf(false)
    }
    var addressList by remember { mutableStateOf(mutableListOf<String>()) }

    var addressListIndicator by remember {
        mutableStateOf("pp")
    }


    val ctx = LocalContext.current
    val address = JsonReader.readAndDeserializeJson(ctx, "nepal.json")?.provinces?.toMutableList()


    if (addressDialog) {
        AddressDialog(addressList = addressList, onDismissRequest = { }, onClick = {
            when (addressListIndicator) {
                "pp" -> {
                    onEvent(ProfileEvent.PermanentProvince(it))
                }

                "pd" -> {
                    onEvent(ProfileEvent.PermanentDistrict(it))
                }

                "pm" -> {
                    onEvent(ProfileEvent.PermanentMunicipality(it))
                }

                "tp" -> {
                    onEvent(ProfileEvent.TemporaryProvince(it))
                }

                "td" -> {
                    onEvent(ProfileEvent.TemporaryDistrict(it))
                }

                "tm" -> {
                    onEvent(ProfileEvent.TemporaryMunicipality(it))
                }
            }
            addressDialog = false

        })
    }
    var selectedImageUri by remember {
        mutableStateOf<Uri?>(null)
    }
    val photoPickerLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.PickVisualMedia(),
            onResult = { uri ->
                if (uri != null) {
                    selectedImageUri = uri
                }
            })

    val calendarState = rememberSheetState()
    CalendarDialog(state = calendarState, config = CalendarConfig(
        monthSelection = true, yearSelection = true
    ), selection = CalendarSelection.Date { date ->
        onEvent(ProfileEvent.OnDobChange(date.toString()))
    })

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
                text = ctx.getString(R.string.profile),
                style = MaterialTheme.typography.titleLarge
            )
        })
    }, content = {
        state.infoMsg?.let {
            MessageDialog(
                message = it,
                onDismissRequest = {
                    if (onEvent != null && state.infoMsg.isCancellable == true) {
                        onEvent(ProfileEvent.DismissInfoMsg)
                    }
                },
                onPositive = { },
                onNegative = {})
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Box(modifier = Modifier.fillMaxWidth(0.7f)) {
                AsyncImage(
                    model = selectedImageUri ?: state.imageUrl, contentDescription = ""
                )
            }
            Card(
                modifier = Modifier.fillMaxWidth(0.9f),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            ) {
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        TextFieldJobNepal(
                            modifier = Modifier.weight(0.85f),
                            label = "First Name",
                            value = state.editFirstName,
                            onValueChange = {
                                onEvent(ProfileEvent.OnFirstNameChange(it))
                            },
                            required = true,
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        FloatingActionButton(modifier = Modifier.weight(0.15f), onClick = {
                            photoPickerLauncher.launch(
                                PickVisualMediaRequest(
                                    ActivityResultContracts.PickVisualMedia.ImageOnly
                                )
                            )
                        }) {
                            Icon(
                                imageVector = Icons.Default.Camera, contentDescription = ""
                            )
                        }

                    }
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextFieldJobNepal(modifier = Modifier.weight(1f),
                            label = "Middle Name",
                            value = state.editMiddleName,
                            onValueChange = {
                                onEvent(ProfileEvent.OnMiddleNameChange(it))
                            })
                        Spacer(modifier = Modifier.width(5.dp))
                        TextFieldJobNepal(
                            modifier = Modifier.weight(1f),
                            label = "Last Name",
                            value = state.editLastName,
                            onValueChange = {
                                onEvent(ProfileEvent.OnLastNameChange(it))
                            },
                            required = true
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        EnumTextFieldJobNepal(
                            modifier = Modifier.fillMaxWidth(0.5f),
                            options = Gender.values().toList(),
                            asString = { this?.resId?.let { ctx.getString(it) } ?: "" },
                            label = "Gender",
                            value = state.editGender,
                            onValueChange = { onEvent(ProfileEvent.OnGenderChange(it)) },
                            required = true
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        ReadonlyJobNepalTextField(value = state.editDob,
                            label = "Date of birth",
                            required = true,
                            onClick = {
                                calendarState.show()
                            }

                        )
                    }
                    TextFieldJobNepal(
                        label = "Email", value = state.editEmail, onValueChange = {
                            onEvent(ProfileEvent.OnEmailChange(it))
                        }, required = true
                    )

                    TextFieldJobNepal(
                        label = "Phone No.", value = state.editPhone, onValueChange = {
                            onEvent(ProfileEvent.OnPhoneChange(it))
                        }, required = true
                    )


                }


            }

            //Permanent Address

            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            ) {
                var isExpand by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Permanent Address")
                        Icon(imageVector = if (!isExpand) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isExpand = !isExpand
                            })
                    }
                    Divider(modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
                    if (isExpand) {

                        ReadonlyJobNepalTextField(label = "State",
                            value = state.provience,
                            onClick = {

                                if (address != null) {
                                    addressList = address.map { it.name }.toMutableList()
                                    addressListIndicator = "pp"
                                    addressDialog = true
                                }
                            })
                        if (state.provience.isNotBlank()) {
                            ReadonlyJobNepalTextField(value = state.district,
                                label = "District",
                                onClick = {
                                    if (address != null) {
                                        addressList =
                                            address.find { it.name == state.provience }?.districts?.map { it.name }
                                                ?.toMutableList() ?: mutableListOf()
                                        addressListIndicator = "pd"
                                        addressDialog = true
                                    }
                                })
                        }
                        if (state.district.isNotBlank()) {
                            ReadonlyJobNepalTextField(label = "Municipality",
                                value = state.municipality,
                                onClick = {
                                    if (address != null) {
                                        addressList =
                                            address.find { it.name == state.provience }?.districts?.find { it.name == state.district }?.municipalities?.toMutableList()
                                                ?: mutableListOf()
                                        addressListIndicator = "pm"
                                        addressDialog = true
                                    }
                                })
                        }
                        Row(modifier = Modifier.fillMaxWidth()) {
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                value = state.villageName,
                                label = "Village Name",
                                onValueChange = {
                                    onEvent(ProfileEvent.PermanentVillage(it))
                                })
                            Spacer(modifier = Modifier.width(5.dp))
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                value = state.wardNo,
                                label = "Ward No",
                                onValueChange = {
                                    onEvent(ProfileEvent.PermanentWard(it))
                                })

                        }


                    }

                }
            }


            //Temp address


            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            ) {

                var isExpand by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Temporary Address")
                        Icon(imageVector = if (!isExpand) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isExpand = !isExpand
                            })
                    }
                    Divider(modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
                    if (isExpand) {
                        ReadonlyJobNepalTextField(label = "State",
                            value = state.tprovience,
                            onClick = {

                                if (address != null) {
                                    addressList = address.map { it.name }.toMutableList()
                                    addressListIndicator = "tp"
                                    addressDialog = true
                                }
                            })
                        if (state.provience.isNotBlank()) {
                            ReadonlyJobNepalTextField(value = state.tdistrict,
                                label = "District",
                                onClick = {
                                    if (address != null) {
                                        addressList =
                                            address.find { it.name == state.tprovience }?.districts?.map { it.name }
                                                ?.toMutableList() ?: mutableListOf()
                                        addressListIndicator = "td"
                                        addressDialog = true
                                    }
                                })
                        }
                        if (state.district.isNotBlank()) {
                            ReadonlyJobNepalTextField(label = "Municipality",
                                value = state.tmunicipality,
                                onClick = {
                                    if (address != null) {
                                        addressList =
                                            address.find { it.name == state.tprovience }?.districts?.find { it.name == state.tdistrict }?.municipalities?.toMutableList()
                                                ?: mutableListOf()
                                        addressListIndicator = "tm"
                                        addressDialog = true
                                    }
                                })
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                value = state.tvillageName,
                                label = "Village Name",
                                onValueChange = {
                                    onEvent(ProfileEvent.TemporaryVillage(it))
                                })
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                value = state.twardNo,
                                label = "Ward No",
                                onValueChange = {
                                    onEvent(ProfileEvent.TemporaryWard(it))
                                })
                        }
                    }
                }
            }


            //Fathers Details
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            ) {

                var isExpand by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Father's Details")
                        Icon(imageVector = if (!isExpand) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isExpand = !isExpand
                            })
                    }
                    Divider(modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
                    if (isExpand) {
                        TextFieldJobNepal(label = "First Name",
                            value = state.editFatherFirstName,
                            onValueChange = {
                                onEvent(ProfileEvent.OnFatherFirstNameChange(it))
                            })
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                label = "Middle Name",
                                value = state.editFatherMiddleName,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnFatherMiddleNameChange(it))
                                })
                            Spacer(modifier = Modifier.width(5.dp))
                            TextFieldJobNepal(
                                modifier = Modifier.weight(1f),
                                label = "Last Name",
                                value = state.editFatherLastNam,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnFatherLastNameChange(it))
                                },
                            )
                        }
                    }
                }
            }

            //Mothers Details
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            ) {

                var isExpand by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Mother's Details")
                        Icon(imageVector = if (!isExpand) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isExpand = !isExpand
                            })
                    }
                    Divider(modifier = Modifier.padding(top = 20.dp, bottom = 10.dp))
                    if (isExpand) {
                        TextFieldJobNepal(label = "First Name",
                            value = state.editMotherFirstName,
                            onValueChange = {
                                onEvent(ProfileEvent.OnMotherFirstNameChange(it))
                            })
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                label = "Middle Name",
                                value = state.editMotherMiddleName,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnMotherMiddleNameChange(it))
                                })
                            Spacer(modifier = Modifier.width(5.dp))
                            TextFieldJobNepal(
                                modifier = Modifier.weight(1f),
                                label = "Last Name",
                                value = state.editMotherLastNam,
                                onValueChange = {
                                    onEvent(ProfileEvent.OnMotherLastNameChange(it))
                                }
                            )
                        }
                    }
                }
            }

            //Grand father's details
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            ) {
                var isExpand by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Grand Father's Details")
                        Icon(imageVector = if (!isExpand) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isExpand = !isExpand
                            })
                    }
                    Divider(
                        modifier = Modifier.padding(
                            top = 20.dp, bottom = 10.dp
                        )
                    )
                    if (isExpand) {
                        TextFieldJobNepal(label = "First Name",
                            value = state.editGrandFatherFirstName,
                            onValueChange = {
                                onEvent(ProfileEvent.GrandFathersFirstName(it))
                            })
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                label = "Middle Name",
                                value = state.editGrandFatherMiddleName,
                                onValueChange = {
                                    onEvent(ProfileEvent.GrandFathersMiddleName(it))
                                })
                            Spacer(modifier = Modifier.width(5.dp))
                            TextFieldJobNepal(
                                modifier = Modifier.weight(1f),
                                label = "Last Name",
                                value = state.editGrandFatherLastNam,
                                onValueChange = {
                                    onEvent(ProfileEvent.GrandFathersLastName(it))
                                },
                            )
                        }
                    }
                }
            }

            // Husband or Wife
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .animateContentSize(),
                colors = CardDefaults.cardColors(MaterialTheme.colorScheme.inversePrimary),
            ) {
                var isExpand by remember {
                    mutableStateOf(false)
                }
                Column(
                    modifier = Modifier.padding(7.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "Husband / Wife")
                        Icon(imageVector = if (!isExpand) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            contentDescription = null,
                            modifier = Modifier.clickable {
                                isExpand = !isExpand
                            })
                    }
                    Divider(
                        modifier = Modifier.padding(
                            top = 20.dp, bottom = 10.dp
                        )
                    )
                    if (isExpand) {
                        TextFieldJobNepal(label = "First Name",
                            value = state.editHusbandWifeFirstName,
                            onValueChange = {
                                onEvent(ProfileEvent.HusbandWifeFirstName(it))
                            })
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextFieldJobNepal(modifier = Modifier.weight(1f),
                                label = "Middle Name",
                                value = state.editHusbandWifeMiddleName,
                                onValueChange = {
                                    onEvent(ProfileEvent.HusbandWifeMiddleName(it))
                                })
                            Spacer(modifier = Modifier.width(5.dp))
                            TextFieldJobNepal(
                                modifier = Modifier.weight(1f),
                                label = "Last Name",
                                value = state.editHusbandWifeLastName,
                                onValueChange = {
                                    onEvent(ProfileEvent.HusbandWifeLastName(it))
                                }
                            )
                        }
                    }
                }
            }
            YouCannotClickMe(enable = !state.areAllDataFilled(),
                size = 200f,
                buttonWidth = 100,
                buttonHeight = 50,
                text = "Upload",
                boxColor = Color.Transparent,
                buttonColor = MaterialTheme.colorScheme.onTertiaryContainer,
                onClick = {
                    selectedImageUri?.let { it1 -> ProfileEvent.Upload(it1) }
                        ?.let { it2 -> onEvent(it2) }
                })
        }
    })
}

