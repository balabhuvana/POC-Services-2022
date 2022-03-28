package com.learn.services.remotebinded

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.os.Message
import android.os.Messenger
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learn.services.CommonUtil
import com.learn.services.databinding.FragmentMessengerServiceBinding

class MessengerServiceFragment : Fragment() {

    private lateinit var binding: FragmentMessengerServiceBinding
    private var mService: Messenger? = null
    private var isBind: Boolean = false

    private var myServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, ibinder: IBinder?) {
            CommonUtil.showToastMessage(context!!, "onServiceConnected()")
            mService = Messenger(ibinder)
            isBind = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mService = null
            CommonUtil.showToastMessage(context!!, "onServiceDisconnected()")
            isBind = true
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMessengerServiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        if (!isBind) {
            startService()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFirstMessage.setOnClickListener {
            val msg = Message.obtain(null, 1)
            mService?.send(msg)
        }

        binding.btnSecondMessage.setOnClickListener {
            val msg = Message.obtain(null, 2)
            mService?.send(msg)
        }

    }

    override fun onStop() {
        if (isBind) {
            stopService()
        }
        super.onStop()
    }

    private fun startService() {
        val intent = Intent(activity, MyMessengerService::class.java)
        activity?.bindService(intent, myServiceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun stopService() {
        activity?.unbindService(myServiceConnection)
    }
}
