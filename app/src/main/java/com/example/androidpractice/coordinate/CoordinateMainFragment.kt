package com.example.androidpractice.coordinate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidpractice.databinding.FragmentCoordinateMainBinding

class CoordinateMainFragment : Fragment() {

    private val binding: FragmentCoordinateMainBinding by lazy {
        FragmentCoordinateMainBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }
}