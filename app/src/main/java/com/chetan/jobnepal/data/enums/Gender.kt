package com.chetan.jobnepal.data.enums

import androidx.annotation.StringRes
import com.chetan.jobnepal.R

enum class Gender(val value: String, @StringRes val resId: Int) {
    Male("male", R.string.male),
    Female("female", R.string.female)
}