package com.learn.services.remotebinded

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import com.learn.services.CommonUtil

class MyRemoteService : Service() {

    lateinit var messenger: Messenger

    internal class MyRemoteHandler(var context: Context) : Handler() {

        companion object {
            val My_CLIENT_REGISTER: Int = 1;
            val My_CLIENT_UN_REGISTER: Int = 2;
            val My_CLIENT_NEED_DATA: Int = 3;
            var messengerList: ArrayList<Messenger> = ArrayList()
            var mValue = 0
        }

        override fun handleMessage(msg: Message) {
            when (msg.what) {
                My_CLIENT_REGISTER -> {
                    messengerList.add(msg.replyTo)
                    CommonUtil.showToastMessage(context, "My_CLIENT_REGISTER")
                }
                My_CLIENT_UN_REGISTER -> {
                    messengerList.remove(msg.replyTo)
                    CommonUtil.showToastMessage(context, "My_CLIENT_UN_REGISTER")
                }
                My_CLIENT_NEED_DATA -> {
                    mValue = msg.arg1
                    CommonUtil.showToastMessage(context, "My_CLIENT_NEED_DATA $mValue")
                    for (messenger in messengerList) {
                        messenger.send(Message.obtain(null, My_CLIENT_NEED_DATA, mValue, 0))
                    }
                }
            }
            super.handleMessage(msg)
        }
    }

    override fun onBind(intent: Intent): IBinder {
        messenger = Messenger(MyRemoteHandler(applicationContext))
        return messenger.binder
    }
}