package com.zjta.wyb.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseFragment
import kotlinx.android.synthetic.main.welcome_fragment3.*

class WelcomeFragment3 : BaseFragment() {
    override fun onCreateView(
        original: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.welcome_fragment3, container, false)
    }

    private var mListener: View.OnClickListener? = null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        btnEnter.setOnClickListener(mListener)
    }

    fun setOnClickEnterListener(listener: View.OnClickListener) {
        mListener = listener
    }
}