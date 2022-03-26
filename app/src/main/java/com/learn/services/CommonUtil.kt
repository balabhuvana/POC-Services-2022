package com.learn.services

import android.util.Log

class CommonUtil {

    companion object {
        fun printLogs(TAG: String, methodName: String) {
            Log.i(TAG, "Method name: $methodName")
        }
    }

}