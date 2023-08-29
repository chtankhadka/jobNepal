package com.chetan.jobnepal.screens.admin.payments

import com.chetan.jobnepal.data.models.adminpayment.AddAdminPaymentMethodRequest

sealed interface AdminPaymentEvent{
    class OnPaymentDetailsAdd(val data : AddAdminPaymentMethodRequest) : AdminPaymentEvent
    class OnBankNameChange(val value: String): AdminPaymentEvent
    object DismissInfoMsg: AdminPaymentEvent

}