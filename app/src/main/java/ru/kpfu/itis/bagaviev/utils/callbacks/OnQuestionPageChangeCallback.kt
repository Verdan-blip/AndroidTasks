package ru.kpfu.itis.bagaviev.utils.callbacks

import androidx.viewpager2.widget.ViewPager2

class OnQuestionPageChangeCallback(
    private val viewPager2: ViewPager2
) : ViewPager2.OnPageChangeCallback() {

    private val pagesCount = viewPager2.adapter?.itemCount ?: 0
    private var position = 0

    init {
        viewPager2.setCurrentItem(1, false)
    }

    override fun onPageSelected(position: Int) {
        this.position = position
    }

    override fun onPageScrollStateChanged(state: Int) {
        if (state == ViewPager2.SCROLL_STATE_IDLE) {
            when (position) {
                pagesCount - 1 -> {
                    viewPager2.setCurrentItem(1, false)
                }
                0 -> {
                    viewPager2.setCurrentItem(pagesCount - 2,false);
                }
                else -> {
                    super.onPageSelected(position)
                }
            }
        } else {
            super.onPageScrollStateChanged(state)
        }
    }

}