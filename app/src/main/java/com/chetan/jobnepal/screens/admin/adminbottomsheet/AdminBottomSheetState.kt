package com.chetan.jobnepal.screens.admin.adminbottomsheet

import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class AdminBottomSheetState(
    val totalCost: String = "",
    val discountPercentage: String = "",
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
