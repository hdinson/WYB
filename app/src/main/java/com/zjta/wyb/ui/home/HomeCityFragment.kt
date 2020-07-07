package com.zjta.wyb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.SimpleTextAdapter
import com.zjta.wyb.api.HomeModelApi
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.LiveVideo
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.widget.recycleview.CommonAdapter
import io.reactivex.Observable
import kotlinx.android.synthetic.main.framgnt_home_city.*

/**
 * 首页-同城
 */
class HomeCityFragment : ViewPagerLazyFragment() {
    override fun lazyInit() {

        //HttpHelper.create(HomeModelApi::class.java)

         val list = ArrayList<String>()
        for (index in 1..100) {
            list.add("  Test $index")
        }


         rvHomeCity.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = SimpleTextAdapter(list)
        }
        //LinearSnapHelper().attachToRecyclerView(rvHomeCity)
        //SnapHelper().attachToRecyclerView(rvHomeCity)

    }

    override fun onCreateView(
        original: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_home_city, container, false)
    }


}