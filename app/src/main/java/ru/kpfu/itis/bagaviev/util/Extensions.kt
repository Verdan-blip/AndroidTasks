package ru.kpfu.itis.bagaviev.util

import android.util.DisplayMetrics

fun Int.getValueInPx(dm: DisplayMetrics): Int {
    return (this * dm.density).toInt()
}