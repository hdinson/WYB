package com.zjta.wyb.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import com.zjta.wyb.R
import com.zjta.wyb.api.UserActionApi
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.UserInfo
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.*
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        overridePendingTransition(R.anim.activity_in_from_bottom, R.anim.activity_in_nothing)
        SystemBarModeUtils.darkMode(this, true)
        initUI()
    }

    private fun initUI() {
        btnRegister.click { startActivity(Intent(this, RegisterActivity::class.java)) }
        tvPhoneCode.click { ChooseAreaCodeActivity.startActivityForResult(this) }
        btnDoLogin.click { doLogin() }

        btnGetVerifyCode.click {
            if (StringUtils.isEmpty(etPhone.text.toString())) {
                "请输入手机号码".toast()
                return@click
            }

            btnGetVerifyCode.isEnabled = false
            btnGetVerifyCode.text = "正在获取"

            HttpHelper.create(UserActionApi::class.java)
                .queryVerificationCode(tvPhoneCode.text.toString(), etPhone.text.toString(), 1)
                .compose(RxSchedulers.io_main())
                .subscribe(object : BaseObserver<Void>() {
                    override fun onHandleSuccess(t: Void?, message: String) {
                        RxHelper.countdown(59)
                            .compose(bindToLifecycle())
                            .subscribe {
                                btnGetVerifyCode.isEnabled = it == 0
                                btnGetVerifyCode.text = if (it == 0) "获取验证码" else "${it}s"
                            }
                            .addToManaged()
                    }

                    override fun onHandleError(code: Int, message: String) {
                        super.onHandleError(code, message)
                        btnGetVerifyCode.isEnabled = true
                        btnGetVerifyCode.text = "获取验证码"
                    }
                })
        }
    }


    /**
     * 登录
     */
    private fun doLogin() {
        if (cbAgreement.isChecked.not()) {
            "请阅读并同意用户协议".toast()
            return
        }
        if (StringUtils.isEmpty(etPhone.text)) {
            "请输入手机号".toast()
            return
        }
        if (StringUtils.isEmpty(etVerifyCode.text)) {
            "请输入验证码".toast()
            return
        }
        btnDoLogin.isEnabled = false
        HttpHelper.create(UserActionApi::class.java)
            .doLogin(tvPhoneCode.text.toString(), etPhone.text.toString(), etVerifyCode.text.toString(), 1,
                     DateUtils.getCurrentTimeMillis13().toString())
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<UserInfo>() {
                override fun onHandleSuccess(t: UserInfo?, message: String) {
                    if (t == null) return
                    //保存用户数据
                    SPUtils.setUserInfo(t)
                    setResult(Activity.RESULT_OK)
                    finish()
                }

                override fun onHandleError(code: Int, message: String) {
                    super.onHandleError(code, message)
                    etVerifyCode.setText("")
                }

                override fun onHandleComplete() {
                    btnDoLogin.isEnabled = true
                }
            })

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == ChooseAreaCodeActivity.REQUEST_CODE && data != null) {
                tvPhoneCode.text = data.getStringExtra(ChooseAreaCodeActivity.RESULT_DATE_CODE)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_out_nothing, R.anim.activity_out_to_bottom)
    }

    override fun finishWithAnim(): Boolean {
        return false
    }

    companion object {
        fun startActivityForResult(act: Activity, requestCode: Int) {
            val intent = Intent(act, LoginActivity::class.java)
            act.startActivityForResult(intent, requestCode)
        }
    }
}
