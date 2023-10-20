package com.example.androidpractice.txss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidpractice.databinding.FragmentConsumptionBinding

class ConsumptionFragment: Fragment() {
    private val binding by lazy {
        FragmentConsumptionBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }
}