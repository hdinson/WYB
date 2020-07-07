package com.zjta.wyb.ui.usercenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.CommonFragmentPagerAdapter
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.listener.SlidingTabSelectorScale
import com.zjta.wyb.ui.home.HomeDiscoverFragment
import com.zjta.wyb.ui.home.HomeNowFragment
import com.zjta.wyb.ui.home.HomeRequireFragment
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.utils.SystemBarModeUtils
import kotlinx.android.synthetic.main.framgnt_v_manager_me.*

/**
 * v 管理-我的
 *
 */
class VManagerMeFragment : ViewPagerLazyFragment() {

    override fun onCreateView(
        original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_v_manager_me, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SystemBarModeUtils.setPaddingTop(view.context, viewContainer)
    }


    override fun lazyInit() {
        LogUtils.v("VManagerMeFragment item 4")
        ivTopToHome.click { activity?.onBackPressed() }
        val titles = arrayOf("V号管理", "账号/资产", "商学院")
        val fragmentList = arrayListOf(VManagerHomeFragment(), VManagerAssetsFragment(), VManagerSchoolFragment())
        vpVManagerContent.offscreenPageLimit = fragmentList.size
        if (fragmentManager !== null) {
            val mViewPagerAdapter = CommonFragmentPagerAdapter(fragmentList, childFragmentManager)
            vpVManagerContent.adapter = mViewPagerAdapter
        }
        vpVManagerContent.overScrollMode = View.OVER_SCROLL_NEVER
        stlTopTeb.setViewPager(vpVManagerContent, titles)
        vpVManagerContent.currentItem = 1
        vpVManagerContent.addOnPageChangeListener(SlidingTabSelectorScale(stlTopTeb))
    }


}