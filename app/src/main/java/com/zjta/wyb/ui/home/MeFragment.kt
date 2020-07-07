package com.zjta.wyb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjta.wyb.R
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.ui.recharge.RechargeActivity
import com.zjta.wyb.ui.usercenter.CollectingActivity
import kotlinx.android.synthetic.main.home_me_fragment.*

/**
 * 首页-我的
 */
class MeFragment : ViewPagerLazyFragment() {


    override fun onCreateView(original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return original.inflate(R.layout.home_me_fragment, container, false)
    }

    override fun lazyInit() {
    }


}