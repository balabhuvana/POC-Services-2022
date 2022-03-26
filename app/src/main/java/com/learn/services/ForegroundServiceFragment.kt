package com.learn.services

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.learn.services.databinding.FragmentForegroundServiceBinding

class ForegroundServiceFragment : Fragment() {

    private lateinit var binding: FragmentForegroundServiceBinding
    private lateinit var intent: Intent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentForegroundServiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnStart.setOnClickListener {
            startMyForegroundServices()
        }

        binding.btnStop.setOnClickListener {
            stopMyForegroundServices()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun startMyForegroundServices() {
        intent = Intent(activity, MyForegroundService::class.java)
        activity?.startForegroundService(intent)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun stopMyForegroundServices() {
        activity?.stopService(intent)
    }
}