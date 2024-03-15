package com.nalldev.gxsales.core.util

import android.app.Activity
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

internal fun Activity?.showSuccessToast(msg : String, showLong : Boolean = false) {
    val duration = if (showLong) MotionToast.LONG_DURATION else MotionToast.SHORT_DURATION
    this?.let { activity ->
        MotionToast.createToast(activity, null, msg, MotionToastStyle.SUCCESS, MotionToast.GRAVITY_BOTTOM, duration, null)
    }
}

internal fun Activity?.showInfoToast(msg : String, showLong : Boolean = false) {
    val duration = if (showLong) MotionToast.LONG_DURATION else MotionToast.SHORT_DURATION
    this?.let { activity ->
        MotionToast.createToast(activity, null, msg, MotionToastStyle.INFO, MotionToast.GRAVITY_BOTTOM, duration, null)
    }
}

internal fun Activity?.showErrorToast(msg : String, showLong : Boolean = false) {
    val duration = if (showLong) MotionToast.LONG_DURATION else MotionToast.SHORT_DURATION
    this?.let { activity ->
        MotionToast.createToast(activity, null, msg, MotionToastStyle.ERROR, MotionToast.GRAVITY_BOTTOM, duration, null)
    }
}

internal fun Activity?.showWarningToast(msg : String, showLong : Boolean = false) {
    val duration = if (showLong) MotionToast.LONG_DURATION else MotionToast.SHORT_DURATION
    this?.let { activity ->
        MotionToast.createToast(activity, null, msg, MotionToastStyle.WARNING, MotionToast.GRAVITY_BOTTOM, duration, null)
    }
}