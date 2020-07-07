package com.zjta.wyb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.CommonFragmentPagerAdapter
import com.zjta.wyb.adapter.SimpleTextAdapter
import com.zjta.wyb.base.BaseFragment
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.listener.SlidingTabSelectorScale
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.utils.SystemBarModeUtils
import kotlinx.android.synthetic.main.home_appointment_fragment.*

/**
 * 首页-有约
 */
class AppointmentFragment : ViewPagerLazyFragment() {


    override fun onCreateView(original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return original.inflate(R.layout.home_appointment_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SystemBarModeUtils.setPaddingTop(view.context, viewContainer)
    }

    override fun lazyInit() {
        LogUtils.v("HomeFragment lazyInit")
        val titles = arrayOf("到点直播", "我的@播", "我的发单")
        val fragmentList =
            arrayListOf(AppointmentRemindFragment(), AppointmentNoticeFragment(), AppointmentRequireFragment())
        vpAppointContent.offscreenPageLimit = fragmentList.size
        if (fragmentManager !== null) {
            val mViewPagerAdapter = CommonFragmentPagerAdapter(fragmentList, childFragmentManager)
            vpAppointContent.adapter = mViewPagerAdapter
        }
        vpAppointContent.overScrollMode = View.OVER_SCROLL_NEVER
        stlTopTeb.setViewPager(vpAppointContent, titles)
        vpAppointContent.addOnPageChangeListener(SlidingTabSelectorScale(stlTopTeb))
    }
}