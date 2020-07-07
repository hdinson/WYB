package com.zjta.wyb.ui.usercenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.SimpleTextAdapter
import com.zjta.wyb.base.ViewPagerLazyFragment
import kotlinx.android.synthetic.main.framgnt_home_city.*

/**
 * v 管理-v号管理
 */
class VManagerHomeFragment : ViewPagerLazyFragment() {
    override fun lazyInit() {


    }

    override fun onCreateView(
        original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_v_manager_home, container, false)
    }


}