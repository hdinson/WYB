package com.zjta.wyb.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class CommonFragmentPagerAdapter<T : Fragment>(
    private val fragmentList: ArrayList<T>, fm: FragmentManager
) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var fm: FragmentManager? = null

    init {
        this.fm = fm
    }

    override fun getItem(position: Int): T {
        return fragmentList[position]
    }

    override fun getCount(): Int {
        return fragmentList.size
    }
}