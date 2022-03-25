package com.learn.services

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.learn.services.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        binding.btnServiceOne.setOnClickListener {
            startServiceOne()
        }

        binding.btnServiceTwo.setOnClickListener {
            startServiceTwo()
        }
    }

    private fun startServiceOne() {
        val intent: Intent = Intent(this, MyService::class.java)
        val bundle = Bundle()
        bundle.putString(key_name, "arun")
        intent.putExtras(bundle)
        startService(intent)
    }

    private fun startServiceTwo() {
        val intent: Intent = Intent(this, MyService::class.java)
        val bundle = Bundle()
        bundle.putString(key_name, "deva")
        intent.putExtras(bundle)
        startService(intent)
    }

    companion object {
        var key_name = "name"
    }
}