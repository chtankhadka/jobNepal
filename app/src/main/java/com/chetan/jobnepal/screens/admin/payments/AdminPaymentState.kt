package com.chetan.jobnepal.screens.admin.payments

import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class AdminPaymentState(
    val paymentItem: Pair<String, String> = "" to "",
    val bankName: String = "",
    val downloadAttachementUrl: List<Pair<String,String>> = listOf(),
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
