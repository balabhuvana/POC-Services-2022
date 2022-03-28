package com.learn.services.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import androidx.fragment.app.commit
import com.learn.services.R
import com.learn.services.databinding.ActivityMainBinding
import com.learn.services.remotebinded.MessengerServiceFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        supportFragmentManager.commit {
            setReorderingAllowed(true)
            add<MessengerServiceFragment>(R.id.fragment_container_view)
        }
    }
}