package com.fifteen11.checkappversion.utils

import android.content.Context
import android.content.SharedPreferences
import com.fifteen11.checkappversion.R
import com.fifteen11.checkappversion.utils.AppConstant.Companion.IS_USER_LOGIN
import com.fifteen11.checkappversion.utils.AppConstant.Companion.LOGIN_USER_NAME

class SharedPreferenceManager constructor(context: Context) {

    private val preferences: SharedPreferences =
        context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    private val editor = preferences.edit()

    fun checkPreferenceKey(keyName: String): Boolean {
        return preferences.contains(keyName)
    }

    fun removePreferenceKey(keyName: String) {
        editor.remove(keyName).apply()
    }

    fun clearPreferenceData() {
        editor.clear().apply()
    }

    fun setIsUserLogin(isUserLogin: Boolean) {
        editor.putBoolean(IS_USER_LOGIN, isUserLogin).apply()
    }

    fun getIsUserLogin(): Boolean {
        return preferences.getBoolean(IS_USER_LOGIN, false)
    }

    fun setUserName(userName: String) {
        editor.putString(LOGIN_USER_NAME, userName).apply()
    }

    fun getUserName(): String? {
        return preferences.getString(LOGIN_USER_NAME, "Admin User")
    }
}