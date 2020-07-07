package com.zjta.wyb.listener

import androidx.viewpager.widget.ViewPager
import com.flyco.tablayout.SlidingTabLayout

class SlidingTabSelectorScale(private val stl: SlidingTabLayout) : ViewPager.OnPageChangeListener {
    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        val count = stl.tabCount
        if (position >= count - 1) return
        val titleView1 = stl.getTitleView(position)
        val titleView2 = stl.getTitleView(position + 1)
        titleView2.scaleX = 1f + positionOffset * 0.3f
        titleView2.scaleY = 1f + positionOffset * 0.3f
        titleView1.scaleX = (1.3f - positionOffset * 0.3f)
        titleView1.scaleY = (1.3f - positionOffset * 0.3f)
    }

    override fun onPageSelected(position: Int) {

    }

}