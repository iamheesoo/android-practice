package com.example.androidpractice.coordinate

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpractice.databinding.FragmentCoordinateMainBinding

class CoordinateMainFragment : Fragment() {

    private val binding: FragmentCoordinateMainBinding by lazy {
        FragmentCoordinateMainBinding.inflate(LayoutInflater.from(context))
    }
    private val adapter = TitleAdapter()
    private val list = List<String>(100) { it.toString() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            Log.i("!!!", "onViewCreated")
            rvMain.adapter = adapter
            rvMain.layoutManager = LinearLayoutManager(root.context, LinearLayoutManager.VERTICAL, false)
            adapter.setData(list)
        }
    }
}