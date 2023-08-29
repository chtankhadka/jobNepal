package com.chetan.jobnepal.data.models.adminpayment

import android.net.Uri

data class AddAdminPaymentMethodRequest(
    val bankName: String,
    val bankLogo: Uri = Uri.EMPTY,
    val bankQr: Uri = Uri.EMPTY
)
