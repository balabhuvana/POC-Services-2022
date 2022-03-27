package com.learn.services

import android.content.Context
import android.util.Log
import android.widget.Toast

class CommonUtil {

    companion object {
        fun printLogs(TAG: String, methodName: String) {
            Log.i(TAG, "Method name: $methodName")
        }

        fun showToastMessage(context: Context, text: String) {
            Toast.makeText(context, text, Toast.LENGTH_LONG).show()
        }
    }

}