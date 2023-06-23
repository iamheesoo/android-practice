package com.example.androidpractice.luck

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.androidpractice.databinding.FragmentLuckBinding

class LuckFragment : Fragment() {
    private val binding: FragmentLuckBinding by lazy {
        FragmentLuckBinding.inflate(LayoutInflater.from(context))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launchWhenResumed {
            startAnimateCounter()
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    private fun startAnimateCounter() {
        val animator = ValueAnimator.ofInt(0, 100).apply {
            duration = 5000


            addUpdateListener { _animator ->
                binding.tvCounter.text = _animator.animatedValue.toString()
            }
        }

        animator.start()
    }
}