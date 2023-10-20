package com.example.androidpractice.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidpractice.R
import com.example.androidpractice.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private val binding: FragmentHomeBinding by lazy {
        FragmentHomeBinding.inflate(LayoutInflater.from(context))
    }
    private var homeViewModel: HomeViewModel? = null
    private var homeController: HomeController? = null
    private val controllerListener: HomeControllerListener by lazy {
        object : HomeControllerListener {
            override fun onTitleClick(name: UiComponentName) {
                goToUiComponentFragment(name)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let { _context ->
            homeController = HomeController(_context, controllerListener)

            with(binding) {
                homeController?.let { _homeController ->
                    val lm = LinearLayoutManager(_context).apply {
                        orientation = LinearLayoutManager.VERTICAL
                    }
                    rvHome.apply {
                        layoutManager = lm
                        adapter = _homeController.adapter
                        setHasFixedSize(true)
                        addItemDecoration(DividerItemDecoration(_context, lm.orientation))
                    }
                }
            }
        }

        homeController?.setData()
    }

    private fun goToUiComponentFragment(uiComponentName: UiComponentName) {
        when (uiComponentName) {
            UiComponentName.COORDINATE_LAYOUT -> findNavController().navigate(R.id.action_to_coordinateMainFragment)
            UiComponentName.WEB -> findNavController().navigate(R.id.action_to_webFragment)
            UiComponentName.LUCK -> findNavController().navigate(R.id.action_to_luckFragment)
            UiComponentName.TXSS -> findNavController().navigate(R.id.action_to_txssFragment)
            else -> {

            }
        }
    }
}

enum class UiComponentName {
    COORDINATE_LAYOUT,
    WEB,
    LUCK,
    TXSS
}