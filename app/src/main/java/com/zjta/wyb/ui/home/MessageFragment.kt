package com.zjta.wyb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseFragment
import com.zjta.wyb.base.ViewPagerLazyFragment

/**
 * 首页-消息
 */
class MessageFragment : ViewPagerLazyFragment() {
    override fun lazyInit() {


    }

    override fun onCreateView(original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return original.inflate(R.layout.home_message_fragment, container, false)
    }
}