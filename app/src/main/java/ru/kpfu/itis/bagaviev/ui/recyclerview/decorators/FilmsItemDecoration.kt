package ru.kpfu.itis.bagaviev.ui.recyclerview.decorators

import android.graphics.Rect
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class FilmsItemDecoration(
    private val displayMetrics: DisplayMetrics
) : RecyclerView.ItemDecoration() {

    companion object {
        const val ITEM_OFFSET_IN_DP = 8f
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        with (outRect) {
            left = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ITEM_OFFSET_IN_DP, displayMetrics)
                .toInt()
            right = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ITEM_OFFSET_IN_DP, displayMetrics)
                .toInt()
            top = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ITEM_OFFSET_IN_DP, displayMetrics)
                .toInt()
            bottom = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ITEM_OFFSET_IN_DP, displayMetrics)
                .toInt()
        }
    }
}
