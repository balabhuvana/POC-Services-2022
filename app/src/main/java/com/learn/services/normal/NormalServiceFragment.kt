package com.learn.services.normal

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.learn.services.databinding.FragmentNormalServiceBinding

class NormalServiceFragment : Fragment() {

    private lateinit var binding: FragmentNormalServiceBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNormalServiceBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnServiceOne.setOnClickListener {
            startServiceOne()
        }

        binding.btnServiceTwo.setOnClickListener {
            startServiceTwo()
        }
    }

    private fun startServiceOne() {
        val intent = Intent(activity, MyNormalService::class.java)
        val bundle = Bundle()
        bundle.putString(key_name, "arun")
        intent.putExtras(bundle)
        activity?.startService(intent)
    }

    private fun startServiceTwo() {
        val intent = Intent(activity, MyNormalService::class.java)
        val bundle = Bundle()
        bundle.putString(key_name, "deva")
        intent.putExtras(bundle)
        activity?.startService(intent)
    }

    companion object {
        var key_name = "name"
    }


}