package com.learn.services.remotebinded

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.learn.services.CommonUtil

class MyMessengerService : Service() {

    lateinit var messenger: Messenger

    internal class IncomingHandler(
        context: Context,
        private val applicationContext: Context = context.applicationContext
    ) : Handler() {
        private var MSG_SAY_HELLO: Int = 1
        private var MSG_SAY_BYE: Int = 2
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MSG_SAY_HELLO -> CommonUtil.showToastMessage(applicationContext, "Hello")
                MSG_SAY_BYE -> CommonUtil.showToastMessage(applicationContext, "Bye")
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onBind(intent: Intent): IBinder {
        val incomingHandler = IncomingHandler(this)
        messenger = Messenger(incomingHandler)
        return messenger.binder
    }
}