package com.example.androidpractice.cropimage

import android.Manifest
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
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

    private lateinit var originalBitmap: Bitmap


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

        viewModel.selectedMedia.observe(this) {
            Log.i("!!!", "selectedMedia.observe")

            loadBitmapFromUri(it.uri)?.let { _bitmap ->
                Log.i("!!!", "selectedMedia.observe bitmap $_bitmap")
                originalBitmap = _bitmap
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        permissionLauncher.launch(permissions)
        with(binding) {
            rvGallery.adapter = adapter
            ivCrop.setColorFilter(applyLightness(50))

            ivCrop.setOnTouchListener(CustomTouchListener())
        }
    }

    private fun cropImage(src: Bitmap, left: Int, top: Int, right: Int, bottom: Int): Bitmap {
        val width = right - left
        val height = bottom - top

        // Create a new bitmap for the cropped image
        val croppedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)

        // Create a canvas and draw the cropped portion of the original image onto the new bitmap
        val canvas = Canvas(croppedBitmap)
        val srcRect = Rect(left, top, right, bottom)
        val destRect = Rect(0, 0, width, height)
        canvas.drawBitmap(src, srcRect, destRect, null)

        return croppedBitmap
    }

    private fun loadBitmapFromUri(uri: Uri): Bitmap? {
        return binding.root.context.contentResolver?.let { _contentResolver ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                ImageDecoder.decodeBitmap(
                    ImageDecoder.createSource(
                        _contentResolver,
                        uri
                    )
                ) { decoder: ImageDecoder, _: ImageDecoder.ImageInfo?, _: ImageDecoder.Source? ->
                    decoder.isMutableRequired = true
                    decoder.allocator = ImageDecoder.ALLOCATOR_SOFTWARE
                }
            } else {
                MediaStore.Images.Media.getBitmap(_contentResolver, uri)
            }
        } ?: run {
            null
        }
    }

    private fun onClickImage(data: Media) {
        Glide.with(binding.root)
            .load(data.uri)
            .into(binding.ivMain)
        viewModel.setSelectedMedia(data)
    }

    private var xDelta = 0
    private var yDelta = 0

    inner class CustomTouchListener : OnTouchListener {
        override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
            p1?.let { _event ->
                val x = _event.rawX.toInt()
                val y = _event.rawY.toInt()
                when (_event.action and MotionEvent.ACTION_MASK) {
                    MotionEvent.ACTION_DOWN -> {
                        val params = p0?.layoutParams as ConstraintLayout.LayoutParams
                        xDelta = x - params.leftMargin
                        yDelta = y - params.topMargin
                    }

                    MotionEvent.ACTION_UP -> {
                        showToast("image moved")
                    }

                    MotionEvent.ACTION_MOVE -> {
                        val params = p0?.layoutParams as ConstraintLayout.LayoutParams
                        with(params) {
                            leftMargin = x - xDelta
                            topMargin = y - yDelta
                            rightMargin = 0
                            bottomMargin = 0
                            p0.layoutParams = params
                        }
                        crop()
                    }
                }
                binding.root.invalidate()
            }
            return true
        }

    }

    private fun crop() {
        val cropArea = binding.ivCrop
        val cropped =
            cropImage(originalBitmap, cropArea.left, cropArea.top, cropArea.right, cropArea.bottom)
        Glide.with(binding.root)
            .load(cropped)
            .into(binding.ivResult)
    }

    fun applyLightness(progress: Int): PorterDuffColorFilter? {
        return if (progress > 0) {
            val value = progress * 255 / 100
            PorterDuffColorFilter(Color.argb(value, 255, 255, 255), PorterDuff.Mode.SRC_OVER)
        } else {
            val value = (progress * -1) * 255 / 100
            PorterDuffColorFilter(Color.argb(value, 0, 0, 0), PorterDuff.Mode.SRC_ATOP)
        }
    }
}