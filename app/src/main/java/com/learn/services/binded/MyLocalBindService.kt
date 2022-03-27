package com.learn.services.binded

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import java.util.*

class MyLocalBindService : Service() {
    private var mRandomNUmber = 0
    private val mGenerator = Random()

    override fun onBind(intent: Intent?): IBinder = MyBinder()

    fun getRandomNumber() = mGenerator.nextInt()

    inner class MyBinder : Binder() {
        fun getServiceObject(): MyLocalBindService = this@MyLocalBindService
    }
}
