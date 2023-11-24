package com.example.androidpractice.txss

import android.Manifest
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import com.example.androidpractice.IMAGE_URI
import com.example.androidpractice.databinding.FragmentBottomSheetBinding
import com.example.androidpractice.extension.click
import com.example.androidpractice.extension.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TxssBottomDialogFragment : BottomSheetDialogFragment() {
    private val binding: FragmentBottomSheetBinding by lazy {
        FragmentBottomSheetBinding.inflate(layoutInflater)
    }

    private val launcher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        findNavController().currentBackStackEntry?.savedStateHandle?.set(
            IMAGE_URI,
            uri
        )
        this@TxssBottomDialogFragment.dismiss()
    }

    private val permissionList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        arrayOf(Manifest.permission.READ_MEDIA_IMAGES, Manifest.permission.READ_MEDIA_VIDEO)
    } else {
        arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    private val checkPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            if (result.all { true }) {
                launcher.launch("image/*")
            } else {
                showToast("갤러리 접근 권한 요청에 실패하였습니다.")
            }
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
            llGallery.click {
                checkPermission.launch(permissionList)
            }
            llHeart.click {
                findNavController().currentBackStackEntry?.savedStateHandle?.set(
                    IMAGE_URI,
                    null
                )
                this@TxssBottomDialogFragment.dismiss()
            }
        }

    }
}