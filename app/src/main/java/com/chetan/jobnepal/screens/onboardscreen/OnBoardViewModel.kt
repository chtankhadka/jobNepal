package com.chetan.jobnepal.screens.onboardscreen

import androidx.lifecycle.ViewModel
import com.chetan.jobnepal.data.local.Preference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class OnBoardViewModel @Inject constructor(val preference: Preference) : ViewModel() {

    private val _state = MutableStateFlow(OnBoardState())
    val state : StateFlow<OnBoardState> = _state

    val onEvent: (event : OnBoardEvent) -> Unit = {event ->
        when (event){
            is OnBoardEvent.SwithchPage -> {
                _state.update{
                    it.copy(currentPageNumber = event.pageNumber)
                }
                if (state.value.isLastPage){
                    preference.onBoardCompleted = true
                    Timber.tag("i m here")

                }
            }
        }

    }

}