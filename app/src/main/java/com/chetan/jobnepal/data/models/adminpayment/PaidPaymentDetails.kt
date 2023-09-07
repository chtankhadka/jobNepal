package com.chetan.jobnepal.data.models.adminpayment

data class PaidPaymentDetails(
    val emailAddress: String = "",
    val videoId: String = "",
    val receiptLink: String = "",
    val approved: Boolean = false,
    val paidBankReceiptIs: Boolean = false,
    val paidBankReceipt: String = "",
    val adminCardIs: Boolean = false,
    val admitCard: String = "",
    val paidBankVoucher: String = "",
    val bankVoucher : Boolean= false
)
