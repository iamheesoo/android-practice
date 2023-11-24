package com.example.androidpractice.txss.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpractice.databinding.FragmentTransactionHistoryBinding
import com.example.androidpractice.txss.txssDummyList

class TransactionHistoryFragment: Fragment() {
    private val binding by lazy {
        FragmentTransactionHistoryBinding.inflate(layoutInflater)
    }

    private val adapter = HistoryAdapter()

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
            rvHistory.adapter = adapter
            rvHistory.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            adapter.submitList(txssDummyList)
        }
    }
}