package com.learn.services.binded

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.learn.services.CommonUtil
import com.learn.services.databinding.FragmentMyLocalBindBinding

class MyLocalBindFragment : Fragment() {

    private lateinit var binding: FragmentMyLocalBindBinding
    private var isBind = false
    private val TAG = MyLocalBindFragment::class.java.name
    lateinit var myLocalBindService: MyLocalBindService

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(p0: ComponentName?, iBinder: IBinder?) {
            CommonUtil.showToastMessage(context!!, "onServiceConnected()")
            isBind = true
            val myBind = iBinder as MyLocalBindService.MyBinder
            myLocalBindService = myBind.getServiceObject()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            CommonUtil.showToastMessage(context!!, "onServiceDisconnected()")
            isBind = false
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMyLocalBindBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnBindService.setOnClickListener {
//            startBindService()
        }

        binding.btnUnBindService.setOnClickListener {
//            activity?.unbindService(serviceConnection)
        }

        binding.btnGetRandomNumber.setOnClickListener {
            if (isBind) {
                updateRandomValeInUI(myLocalBindService.getRandomNumber().toString())
            } else {
                CommonUtil.showToastMessage(context!!, "Service un bounded")
            }
        }
    }

    private fun startBindService() {
        val intent = Intent(activity, MyLocalBindService::class.java)
        activity?.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    private fun updateRandomValeInUI(number: String) {
        binding.tvRandomNumber.text = number
    }

    override fun onStart() {
        super.onStart()
        startBindService()
    }

    override fun onStop() {
        context?.unbindService(serviceConnection)
        isBind = false
        super.onStop()
    }
}