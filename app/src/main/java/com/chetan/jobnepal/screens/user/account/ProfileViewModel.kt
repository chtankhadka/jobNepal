package com.chetan.jobnepal.screens.user.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chetan.jobnepal.data.Resource
import com.chetan.jobnepal.data.enums.Gender
import com.chetan.jobnepal.data.models.profile.UploadProfileParam
import com.chetan.jobnepal.data.repository.firebasestoragerepository.FirebaseStorageRepository
import com.chetan.jobnepal.data.repository.firestorerepository.FirestoreRepository
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val storageRepository: FirebaseStorageRepository,
    private val firestoreRepository: FirestoreRepository
) : ViewModel() {
    private val _state = MutableStateFlow(ProfileState())
    val state: StateFlow<ProfileState> = _state
    init {
        getProfileData()

    }
    val onEvent: (event: ProfileEvent) -> Unit = { event ->
        when (event) {

            is ProfileEvent.OnFirstNameChange -> {
                _state.update {
                    it.copy(editFirstName = event.value)
                }
            }

            is ProfileEvent.OnMiddleNameChange -> {
                _state.update {
                    it.copy(editMiddleName = event.value)
                }
            }

            is ProfileEvent.OnEmailChange -> {
                _state.update {
                    it.copy(editEmail = event.value)
                }
            }

            is ProfileEvent.OnLastNameChange -> {
                _state.update {
                    it.copy(editLastName = event.value)
                }
            }
            is ProfileEvent.OnDobChange ->{
                _state.update {
                    it.copy(editDob = event.value)
                }
            }

            is ProfileEvent.OnGenderChange -> {
                _state.update {
                    it.copy(editGender = event.value)
                }
            }

            is ProfileEvent.OnFatherFirstNameChange -> {
                _state.update {
                    it.copy(editFatherFirstName = event.value)
                }
            }

            is ProfileEvent.OnFatherMiddleNameChange -> {
                _state.update {
                    it.copy(editFatherMiddleName = event.value)
                }
            }

            is ProfileEvent.OnFatherLastNameChange -> {
                _state.update {
                    it.copy(editFatherLastNam = event.value)
                }
            }

            is ProfileEvent.Upload -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(progress = Progress(value = 0.0F))
                    }
                    val resource = storageRepository.uploadProfileImage(event.value)
                    when (resource) {
                        is Resource.Failure -> {

                        }

                        Resource.Loading -> {

                        }

                        is Resource.Success -> {
                            val state = state.value
                            when (val resource1 = firestoreRepository.uploadProfileData(
                                UploadProfileParam(
                                    firstName = state.editFirstName,
                                    middleName = state.editMiddleName,
                                    lastName = state.editLastName,
                                    email = state.editEmail,
                                    phoneNo = "",
                                    profileUrl = resource.data.second,
                                    photoName = resource.data.first,
                                    dob = state.editDob,
                                    gender = state.editGender?.value,
                                    fatherFirstName = state.editFatherFirstName,
                                    fatherMiddleName = state.editFatherMiddleName,
                                    fatherLastName = state.editFatherLastNam
                                )
                            )) {
                                is Resource.Failure -> {

                                }
                                Resource.Loading -> {

                                }
                                is Resource.Success -> {
                                    getProfileData()
                                }
                            }
                        }
                    }
                }

            }

            is ProfileEvent.PermanentDistrict -> {
                _state.update {
                    it.copy(
                        district = event.value
                    )
                }
            }
            is ProfileEvent.PermanentMunicipality -> {
                _state.update {
                    it.copy(
                        municipality = event.value
                    )
                }

            }
            is ProfileEvent.PermanentProvince -> {
                _state.update {
                    it.copy(
                        provience = event.value
                    )
                }
            }

            is ProfileEvent.PermanentVillage -> {
                _state.update {
                    it.copy(
                        villageName = event.value
                    )
                }

            }
            is ProfileEvent.PermanentWard -> {
                _state.update {
                    it.copy(
                        wardNo = event.value
                    )
                }
            }
            is ProfileEvent.TemporaryDistrict -> {
                _state.update {
                    it.copy(
                        tdistrict = event.value
                    )
                }
            }
            is ProfileEvent.TemporaryMunicipality -> {
                _state.update {
                    it.copy(
                        tmunicipality = event.value
                    )
                }
            }
            is ProfileEvent.TemporaryProvince -> {
                _state.update {
                    it.copy(
                        tprovience = event.value
                    )
                }
            }
            is ProfileEvent.TemporaryVillage -> {
                _state.update {
                    it.copy(
                        tvillageName = event.value
                    )
                }
            }
            is ProfileEvent.TemporaryWard -> {
                _state.update {
                    it.copy(
                        twardNo = event.value
                    )
                }
            }

            is ProfileEvent.GrandFathersFirstName -> {
                _state.update {
                    it.copy(
                        editGrandFatherFirstName = event.value
                    )
                }
            }
            is ProfileEvent.GrandFathersLastName -> {
                _state.update {
                    it.copy(
                        editGrandFatherLastNam = event.value
                    )
                }
            }
            is ProfileEvent.GrandFathersMiddleName -> {
                _state.update {
                    it.copy(
                        editGrandFatherMiddleName = event.value
                    )
                }
            }
            is ProfileEvent.HusbandWifeFirstName -> {
                _state.update {
                    it.copy(
                        editHusbandWifeFirstName = event.value
                    )
                }
            }
            is ProfileEvent.HusbandWifeLastName -> {
                _state.update {
                    it.copy(
                        editHusbandWifeLastName = event.value
                    )
                }
            }
            is ProfileEvent.HusbandWifeMiddleName -> {
                _state.update {
                    it.copy(
                        editHusbandWifeMiddleName = event.value
                    )
                }
            }
        }

    }

    fun getProfileData() {
        viewModelScope.launch {
            _state.update {
                it.copy(progress = Progress(value = 0.0F))
            }
            val resource = firestoreRepository.getProfileData()
            when (resource) {
                is Resource.Failure -> {
                    _state.update {
                        it.copy(
                            infoMsg = Message.Error(description = resource.exception.message),
                            progress = null
                        )
                    }
                }

                Resource.Loading -> {

                }

                is Resource.Success -> {
                    _state.update {
                        it.copy(
                            progress = null,
                            editFirstName = resource.data.firstName ?: "",
                            editMiddleName = resource.data.middleName ?: "",
                            editLastName = resource.data.lastName ?: "",
                            editDob = resource.data.dob ?: "",
                            editGender = Gender.values().find {gender ->
                                gender.value == resource.data.gender
                            },
                            editEmail = resource.data.email ?: "",
                            editFatherFirstName = resource.data.fatherFirstName ?: "",
                            editFatherMiddleName = resource.data.fatherMiddleName ?: "",
                            editFatherLastNam = resource.data.fatherLastName ?: "",
                            imageUrl = resource.data.profileUrl?: ""

                            )
                    }
                }
            }

        }
    }
}