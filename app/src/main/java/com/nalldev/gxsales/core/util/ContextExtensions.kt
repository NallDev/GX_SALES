package com.nalldev.gxsales.core.util

import android.content.Context
import com.loper7.date_time_picker.DateTimeConfig
import com.loper7.date_time_picker.dialog.CardDateRangePickerDialog
import taimoor.sultani.sweetalert2.Sweetalert

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