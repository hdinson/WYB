package com.zjta.wyb.ui.usercenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zjta.wyb.R
import com.zjta.wyb.adapter.CommonFragmentPagerAdapter
import com.zjta.wyb.base.BaseActivity
import kotlinx.android.synthetic.main.activity_v_manager.*

class VManagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_v_manager)

        initUI()
    }

    private fun initUI() {
        //设置ViewPager
        val fragmentList =
            arrayListOf<Fragment>(VManagerPagerFragment(), VManagerOrderFragment(), VManagerNoticeFragment(),
                                  VManagerMeFragment())
        val mViewPagerAdapter = CommonFragmentPagerAdapter(fragmentList, supportFragmentManager)
        vpContent.adapter = mViewPagerAdapter
        vpContent.overScrollMode = View.OVER_SCROLL_NEVER
        vpContent.offscreenPageLimit = fragmentList.size
        bottomBarLayout.setViewPager(vpContent)
        vpContent.currentItem = fragmentList.size - 1
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, VManagerActivity::class.java))
        }
    }
}
