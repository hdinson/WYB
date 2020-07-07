package com.zjta.wyb.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseFragment

class WelcomeFragment2 : BaseFragment() {
    override fun onCreateView(original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return original.inflate(R.layout.welcome_fragment2, container, false)
    }
}