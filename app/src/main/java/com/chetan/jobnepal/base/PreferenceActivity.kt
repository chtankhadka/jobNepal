package com.chetan.jobnepal.base

import android.content.Context
import android.content.res.Configuration
import androidx.activity.ComponentActivity
import com.chetan.jobnepal.data.local.Preference
import com.chetan.jobnepal.di.CustomEntryPoint
import java.util.Locale


abstract class PreferenceActivity : ComponentActivity() {
    lateinit var preference: Preference
    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            val language = if (CustomEntryPoint.getInstance(newBase.applicationContext).preference.isNepaliLanguage) "ne" else "en"
            val context = language.let {
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