package com.example.androidpractice.txss

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentTxssBinding
import com.example.androidpractice.extension.click
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator

class TxssFragment : Fragment() {
    private val binding by lazy {
        FragmentTxssBinding.inflate(LayoutInflater.from(context))
    }
    private val tabTitleList = arrayListOf<String>("거래내역", "소비")
    private lateinit var viewPagerAdapter: ViewPagerAdapter

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

            
            appbar.outlineProvider = null
            fragmentManager?.let {
                viewPagerAdapter = ViewPagerAdapter(it, lifecycle)
            }

            viewPager.adapter = viewPagerAdapter
            TabLayoutMediator(tlTab, viewPager) { tab, position ->
                tab.text = tabTitleList[position]
            }.attach()

            ivHeart.click {
                val dialog = TxssBottomDialogFragment()
                dialog.show(childFragmentManager, TxssBottomDialogFragment::class.java.simpleName)
            }
        }
    }
}