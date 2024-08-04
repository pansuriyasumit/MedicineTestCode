package com.fifteen11.checkappversion.utils

class AppConstant {
    companion object {
        const val BASE_URL = "https://api.jsonbin.io"

        // Network Connection Timeout
        const val CONNECT_TIMEOUT = 60000L
        const val READ_TIMEOUT = 60000L
        const val WRITE_TIMEOUT = 60000L

        // Define Preference Key
        const val IS_USER_LOGIN = "is_user_login"
        const val LOGIN_USER_NAME = "user_name"

        //Date Format
        const val DATE_FORMAT = "yyyy-MM-dd EEE HH:mm:ss"
    }
}
