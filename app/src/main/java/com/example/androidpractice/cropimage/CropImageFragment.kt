package com.example.androidpractice.cropimage

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.androidpractice.databinding.FragmentCropimageBinding
import com.example.androidpractice.extension.showToast

class CropImageFragment : Fragment() {
    private val binding by lazy {
        FragmentCropimageBinding.inflate(layoutInflater)
    }
    private val viewModel by lazy {
        ViewModelProvider(this).get(CropImageViewModel::class.java)
    }
    private val permissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(
            Manifest.permission.READ_MEDIA_IMAGES
        )
    } else {
        arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
        )
    }
    private val adapter = GalleryAdapter(
        onItemClick = ::onClickImage
    )

    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val isGranted = permissions.entries.all { it.value }

            if (isGranted) {
                context?.contentResolver?.let {
                    viewModel.fetchMedia(it)
                }
            } else {
                showToast("권한 획득 실패")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.mediaList.observe(this) {
            adapter.setData(it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionLauncher.launch(permissions)
        with(binding) {
            rvGallery.adapter = adapter
        }
    }

    private fun onClickImage(data: Media) {
        Glide.with(binding.root)
            .load(data.uri)
            .into(binding.ivMain)
    }
}