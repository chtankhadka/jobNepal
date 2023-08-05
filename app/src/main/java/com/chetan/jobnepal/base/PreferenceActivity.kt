package com.chetan.jobnepal.base

import android.content.Context
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import java.util.*


abstract class PreferenceActivity : ComponentActivity() {

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            val context = "ne".let {
                applyOverrideConfiguration(Configuration().apply {
                    setLocale(Locale(it))
                })
                newBase
            } ?: run {
                newBase
            }
            super.attachBaseContext(context)
        }
    }


}