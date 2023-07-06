package com.chetan.jobnepal.utils

import android.content.Context
import androidx.annotation.StringRes

sealed class StringValue {

    data class DynamicString(val value: String?) : StringValue()

    object Empty : StringValue()

    class StringResource(
        @StringRes val resId: Int,
        vararg val args: Any?
    ) : StringValue()

    class NestedStringResource(
        @StringRes val resId: Int,
        val args: StringValue
    ) : StringValue()

    fun asString(context: Context?): String {
        return when (this) {
            is Empty -> ""
            is DynamicString -> value ?: "N/A"
            is StringResource -> context?.getString(resId, *args).orEmpty()
            is NestedStringResource -> context?.getString(resId, args.asString(context)).orEmpty()
        }
    }
}
