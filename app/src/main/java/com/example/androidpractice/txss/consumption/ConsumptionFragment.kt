package com.example.androidpractice.txss.consumption

import android.animation.ValueAnimator
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.text.color
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentConsumptionBinding
import com.example.androidpractice.extension.toMoney

class ConsumptionFragment : Fragment() {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            lifecycleScope.launchWhenResumed {
                startMoneyCounter()
            }

            tvMoneyContent.text = SpannableStringBuilder().apply {
                color(ContextCompat.getColor(root.context, R.color.gray500)) {
                    append("지난달 이맘때보다 ")
                }
                color(ContextCompat.getColor(root.context, R.color.red200)) {
                    append("9만 5,422원 ")
                }
                color(ContextCompat.getColor(root.context, R.color.gray500)) {
                    append("더 쓰고 있어요.")
                }
            }
        }
    }

    private fun startMoneyCounter() {
        val animator = ValueAnimator.ofInt(0, 271162).apply {
            duration = 2000

            addUpdateListener { _animator ->
                val value = _animator.animatedValue as Int
                binding.tvMoney.text = "${value.toLong().toMoney()}원"
            }
        }

        animator.start()
    }
}