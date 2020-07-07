package com.zjta.wyb.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.kotlin.show
import com.zjta.wyb.ui.home.MainActivity
import com.zjta.wyb.ui.login.LoginActivity
import com.zjta.wyb.utils.RxHelper
import com.zjta.wyb.utils.SPUtils
import com.zjta.wyb.utils.StringUtils
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : BaseActivity() {

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val firstInstall = SPUtils.isFirstInstall(this)
        if (firstInstall) {
            WelcomeActivity.start(this)
            overridePendingTransition(0, 0)
            onBackPressed()
        } else {
            tvCountDown.show()
            RxHelper.countdown(1)
                .compose(bindToLifecycle())
                .subscribe({
                               tvCountDown.text = "跳过 $it"
                           }, {
                               doSkip()
                           }, {
                               doSkip()
                           })
                .addToManaged()
        }
    }

    private fun doSkip() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
