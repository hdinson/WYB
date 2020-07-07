package com.zjta.wyb.ui.login

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.jkb.vcedittext.VerificationAction.OnVerificationCodeChangedListener
import com.zjta.wyb.R
import com.zjta.wyb.api.UserActionApi
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.UserInfo
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.closeKeyboard
import com.zjta.wyb.kotlin.showKeyboard
import com.zjta.wyb.utils.DateUtils
import com.zjta.wyb.utils.RxHelper
import com.zjta.wyb.utils.SystemBarModeUtils
import kotlinx.android.synthetic.main.activity_input_verify_code.*

class InputVerifyCodeActivity : BaseActivity() {

    private var mPhoneCode: String = ""
    private var mPhoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input_verify_code)
        SystemBarModeUtils.darkMode(this, true)

        initUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        mPhoneCode = intent.getStringExtra(EXTRA_CODE) ?: ""
        mPhoneNumber = intent.getStringExtra(EXTRA_PHONE) ?: ""
        tvPhone.text = "$mPhoneCode$mPhoneNumber"
        verifyCodeNum.isFocusable = true
        verifyCodeNum.isFocusable = true
        showKeyboard(verifyCodeNum)
        titleBar.titleLeftBtn.click {
            closeKeyboard(it)
            onBackPressed()
        }
        startCountDown()
        tvCountDown.click { getVerificationCode() }

        verifyCodeNum.setOnVerificationCodeChangedListener(object : OnVerificationCodeChangedListener {
            override fun onInputCompleted(s: CharSequence?) {
                if (s?.toString()?.length == 6) {
                    doRegister(s.toString())
                }
            }

            override fun onVerCodeChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    /**
     * 注册
     */
    private fun doRegister(code: String) {
        HttpHelper.create(UserActionApi::class.java)
            .doRegister(mPhoneCode, mPhoneNumber, code, DateUtils.getCurrentTimeMillis10().toString())
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<UserInfo>() {
                override fun onHandleSuccess(t: UserInfo?, message: String) {
                    //todo 保存

                }
            })
    }

    /**
     * 接口获取验证码
     */
    private fun getVerificationCode() {
        HttpHelper.create(UserActionApi::class.java)
            .queryVerificationCode(mPhoneCode, mPhoneNumber, 1)
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<Void>() {
                override fun onHandleSuccess(t: Void?, message: String) {
                    startCountDown()
                }

                override fun onComplete() {
                    super.onComplete()
                    tvCountDown.isEnabled = true
                }
            })
    }

    /**
     * 开始倒计时
     */
    private fun startCountDown() {
        tvCountDown.isEnabled = false
        RxHelper.countdown(59)
            .compose(bindToLifecycle())
            .subscribe {
                tvCountDown.isEnabled = it == 0
                tvCountDown.text = if (it == 0) "重新发送" else "$it 秒后重新发送"
            }
            .addToManaged()
    }

    companion object {
        private const val EXTRA_CODE = "phone"
        private const val EXTRA_PHONE = "phone"
        fun start(context: Context, phoneCode: String, phoneNumber: String) {
            val intent = Intent(context, InputVerifyCodeActivity::class.java)
            intent.putExtra(EXTRA_CODE, phoneCode)
            intent.putExtra(EXTRA_PHONE, phoneNumber)
            context.startActivity(intent)
        }
    }

}
