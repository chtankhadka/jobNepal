package com.chetan.jobnepal.data.local

import android.app.Application
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Preference constructor(
    val context: Context
) {
    @Inject
    constructor(
        application: Application,

        ) : this(
        application.applicationContext
    )

    companion object {
        private const val PREFERENCE_NAME = "PREFERENCE_NAME"
        private const val ONBOARD_COMPLETED = "ONBOARD_COMPLETED"
        private const val DB_TABLE = "DB_TABLE"
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)

    var onBoardCompleted
        get() = sharedPreferences.getBoolean(ONBOARD_COMPLETED, false)
        set(value) {
            sharedPreferences.edit().putBoolean(ONBOARD_COMPLETED,value).apply()
        }

    var dbTable
        get() = sharedPreferences.getString(DB_TABLE,"")
        set(value) {
            sharedPreferences.edit().putString(DB_TABLE, value).apply()
        }
}