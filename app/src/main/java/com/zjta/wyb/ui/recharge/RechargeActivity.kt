package com.zjta.wyb.ui.recharge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.utils.SystemBarModeUtils
import kotlinx.android.synthetic.main.activity_recharge.*

class RechargeActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge)

        SystemBarModeUtils.darkMode(this, true)
        initUI()
    }

    private fun initUI() {
        commonTitleBar.titleRightBtn.click { RechargeDetailActivity.start(this) }
        commonTitleBar.titleLeftBtn.click { onBackPressed() }
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RechargeActivity::class.java))
        }
    }
}
