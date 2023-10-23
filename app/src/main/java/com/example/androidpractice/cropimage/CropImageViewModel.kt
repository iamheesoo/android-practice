package com.example.androidpractice.cropimage

import android.content.ContentResolver
import android.net.Uri
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CropImageViewModel: ViewModel() {

    private val _mediaList = MutableLiveData<List<Media>>()
    val mediaList: LiveData<List<Media>> = _mediaList

    private val _selectedMedia = MutableLiveData<Media>()
    val selectedMedia: LiveData<Media> = _selectedMedia

    fun fetchMedia(contentResolver: ContentResolver) {
        val photoAndVideos: MutableList<Media> = mutableListOf()

        // Define columns we're interested in
        val projection = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.DATE_ADDED
        )

        // Combine image and video in one query
        val selection = "${MediaStore.Files.FileColumns.MEDIA_TYPE} = ${MediaStore.Files.FileColumns.MEDIA_TYPE_IMAGE}"

        contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            projection,
            selection,
            null,
            "${MediaStore.Files.FileColumns.DATE_ADDED} DESC"
        )?.use { cursor ->
            val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns._ID)
            val mimeTypeColumn = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.MIME_TYPE)

            while (cursor.moveToNext()) {
                val id = cursor.getLong(idColumn)
                val mimeType = cursor.getString(mimeTypeColumn)

                val contentUri: Uri = when {
                    mimeType.startsWith("image/") -> {
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI.buildUpon().appendPath(id.toString()).build()
                    }
                    else -> continue
                }

                photoAndVideos.add(
                    Media(
                        contentUri,
                        mimeType,
                    )
                )
            }
        }

        _mediaList.value = photoAndVideos
    }

     fun setSelectedMedia(data: Media) {
        _selectedMedia.value = data
    }
}

data class Media(
    val uri: Uri,
    val mimeType: String,
)