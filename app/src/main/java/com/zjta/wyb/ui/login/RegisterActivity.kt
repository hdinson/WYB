package com.zjta.wyb.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.zjta.wyb.R
import com.zjta.wyb.api.UserActionApi
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.utils.SystemBarModeUtils
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        SystemBarModeUtils.darkMode(this, true)
        initUI()
    }

    private fun initUI() {
        titleBar.titleLeftBtn.click { onBackPressed() }

        tvPhoneCode.click { ChooseAreaCodeActivity.startActivityForResult(this) }
        btnDoRegister.click {
            if (cbAgreement.isChecked.not()) {
                "请阅读并同意用户协议".toast()
                return@click
            }
            if (StringUtils.isEmpty(etPhone.text)) {
                "请输入手机号".toast()
                return@click
            }
            btnDoRegister.isEnabled = false
            HttpHelper.create(UserActionApi::class.java)
                .queryVerificationCode(tvPhoneCode.text.toString(), etPhone.text.toString(), 1)
                .compose(RxSchedulers.io_main())
                .subscribe(object : BaseObserver<Void>() {
                    override fun onHandleSuccess(t: Void?, message: String) {
                        btnDoRegister.isEnabled = true
                        startActivity(Intent(this@RegisterActivity, InputVerifyCodeActivity::class.java))
                    }

                    override fun onComplete() {
                        super.onComplete()
                        btnDoRegister.isEnabled = true
                    }
                })
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ChooseAreaCodeActivity.REQUEST_CODE && data != null) {
                tvPhoneCode.text = data.getStringExtra(ChooseAreaCodeActivity.RESULT_DATE_CODE)
            }
        }
    }
}
