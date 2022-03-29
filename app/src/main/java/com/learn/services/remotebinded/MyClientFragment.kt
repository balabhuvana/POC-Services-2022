package com.learn.services.remotebinded

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learn.services.CommonUtil
import com.learn.services.databinding.FragmentClientBinding

class MyClientFragment : Fragment() {

    private lateinit var binding: FragmentClientBinding
    private var mServiceMessenger: Messenger? = null
    private var isBind: Boolean = false
    private var mCurrentValue = 0

    private var myServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, ibinder: IBinder?) {
            CommonUtil.showToastMessage(context!!, "onServiceConnected()")
            mServiceMessenger = Messenger(ibinder)
            registerWithRemoteService()
            isBind = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mServiceMessenger = null
            CommonUtil.showToastMessage(context!!, "onServiceDisconnected()")
            isBind = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentClientBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (!isBind) {
            bindService()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnClientSendMessage.setOnClickListener {
            sendMessageToRemoteService()
        }

    }

    override fun onStop() {
        if (isBind) {
            unBindService()
        }
        super.onStop()
    }

    private fun bindService() {
        val intent = Intent(activity, MyRemoteService::class.java)
        activity?.bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun unBindService() {
        unRegisterWithRemoteService()
        activity?.unbindService(myServiceConnection)
        isBind = false
    }

    private fun registerWithRemoteService() {
        val msg = Message.obtain(null, MyRemoteService.MyRemoteHandler.My_CLIENT_REGISTER)
        msg.replyTo = Messenger(MyClientHandler(context!!))
        mServiceMessenger?.send(msg)
    }

    private fun unRegisterWithRemoteService() {
        val msg = Message.obtain(null, MyRemoteService.MyRemoteHandler.My_CLIENT_UN_REGISTER)
        msg.replyTo = Messenger(MyClientHandler(context!!))
        mServiceMessenger?.send(msg)
    }

    private fun sendMessageToRemoteService() {
        val msg = Message.obtain(null, MyRemoteService.MyRemoteHandler.My_CLIENT_NEED_DATA)
        mCurrentValue += 10
        msg.arg1 = mCurrentValue
        mServiceMessenger?.send(msg)
    }

    internal class MyClientHandler(var context: Context) : Handler() {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                MyRemoteService.MyRemoteHandler.My_CLIENT_NEED_DATA -> {
                    CommonUtil.showToastMessage(context = context, "Client receives data")
                }
            }
            super.handleMessage(msg)
        }
    }
}
