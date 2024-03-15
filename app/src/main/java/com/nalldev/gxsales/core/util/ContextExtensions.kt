package com.nalldev.gxsales.core.util

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDateRangePickerDialog
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import taimoor.sultani.sweetalert2.Sweetalert
import java.io.File
import java.io.FileOutputStream

internal fun Context.sweetAlertCancelable(title: String, content : String, type: Int, confirmText : String? = null, cancelText : String? = null, onConfirm : (() -> Unit)? = null, onCancel : (() -> Unit)? = null) {
    Sweetalert(this, type).let {
        it.titleText = title
        it.contentText = content
        it.setCancelable(false)
        confirmText?.let { text ->
            it.showConfirmButton(true)
            it.setConfirmButton(text
            ) {dialog ->
                onConfirm?.invoke()
                dialog.dismiss()
            }
        }
        cancelText?.let { text ->
            it.showCancelButton(true)
            it.setCancelButton(text
            ) {dialog ->
                onCancel?.invoke()
                dialog.dismiss()
            }
        }
        it.dismiss()
        it.show()
    }
}

internal fun Context.createDateRangeBuilder() : CardDateRangePickerDialog.Builder {
    return CardDateRangePickerDialog.builder(this).setDisplayType(mutableListOf(DateTimeConfig.DAY, DateTimeConfig.MONTH, DateTimeConfig.YEAR))
}

internal fun Context.createMultipartFromUri(uri: Uri, contentResolver: ContentResolver): MultipartBody.Part {
    val tempFile = File.createTempFile("upload", ".jpg", this.cacheDir).apply {
        deleteOnExit()
    }

    contentResolver.openInputStream(uri)?.use { inputStream ->
        FileOutputStream(tempFile).use { fileOutputStream ->
            inputStream.copyTo(fileOutputStream)
        }
    }

    val requestBody = tempFile.asRequestBody("image/*".toMediaTypeOrNull())
    return MultipartBody.Part.createFormData("file", tempFile.name, requestBody)
}