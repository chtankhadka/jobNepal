package com.chetan.jobnepal.screens.admin.formrequest

import com.chetan.jobnepal.data.models.adminpayment.PaidPaymentDetails
import com.chetan.jobnepal.ui.component.JobNepalScreenState
import com.chetan.jobnepal.ui.component.dialogs.Message
import com.chetan.jobnepal.ui.component.dialogs.Progress

data class FormRequestState(
    val paymentReceiptList: List<PaidPaymentDetails> = emptyList(),
    val userPaymentVideoIdList: List<String> = emptyList(),
    override val infoMsg: Message? = null,
    override val progress: Progress? = null
) : JobNepalScreenState(infoMsg, progress)
