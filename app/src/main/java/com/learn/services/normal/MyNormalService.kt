package com.learn.services.normal

import android.app.Service
import android.content.Intent
import android.os.*
import android.os.Process.THREAD_PRIORITY_BACKGROUND
import com.learn.services.CommonUtil

class MyNormalService : Service() {

    val TAG: String = MyNormalService::class.java.name
    private var serviceLooper: Looper? = null
    private var serviceHandler: ServiceHandler? = null

    override fun onCreate() {
        super.onCreate()

        CommonUtil.printLogs(TAG, "onCreate()")

        HandlerThread("ServiceStartArguments", THREAD_PRIORITY_BACKGROUND).apply {
            start()
            // Get the HandlerThread's Looper and use it for our Handler
            serviceLooper = looper
            serviceHandler = ServiceHandler(looper)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        CommonUtil.printLogs(TAG, "onStartCommand()")
        intent?.extras?.getString(NormalServiceFragment.key_name, "")?.let { CommonUtil.printLogs(TAG, it) }

        serviceHandler?.obtainMessage()?.also {
            it.arg1 = startId
            serviceHandler?.sendMessage(it)
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent): IBinder {
        CommonUtil.printLogs(TAG, "onBind()")
        TODO("Return the communication channel to the service.")
    }

    override fun onUnbind(intent: Intent?): Boolean {
        CommonUtil.printLogs(TAG, "onUnbind()")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent?) {
        CommonUtil.printLogs(TAG, "onRebind()")
        super.onRebind(intent)
    }

    override fun onDestroy() {
        CommonUtil.printLogs(TAG, "onDestroy()")
        super.onDestroy()
    }

    private inner class ServiceHandler(looper: Looper) : Handler(looper) {

        override fun handleMessage(msg: Message) {
            // Normally we would do some work here, like download a file.
            // For our sample, we just sleep for 5 seconds.
            try {
                CommonUtil.printLogs(TAG, "Message:${msg.arg1}")
                Thread.sleep(5000)
            } catch (e: InterruptedException) {
                // Restore interrupt status.
                Thread.currentThread().interrupt()
            }

            // Stop the service using the startId, so that we don't stop
            // the service in the middle of handling another job
            stopSelf(msg.arg1)
        }
    }

}