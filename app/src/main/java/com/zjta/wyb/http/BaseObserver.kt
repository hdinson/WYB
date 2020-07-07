package com.zjta.wyb.http

import android.app.AlertDialog
import android.content.Context
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.widget.dialog.AppProgressDialog
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class BaseObserver<T> : Observer<HttpResult<T>> {
    private var mPd: AlertDialog? = null

    constructor() {}

    constructor(pd: AlertDialog) {
        this.mPd = pd
    }

    constructor(context: Context) {
        this.mPd = AppProgressDialog(context)
    }

    constructor(context: Context, dialogCancelable: Boolean) {
        this.mPd = AppProgressDialog(context, dialogCancelable)
    }

    override fun onSubscribe(d: Disposable) {
        if (mPd != null && !mPd!!.isShowing) {
            mPd!!.show()
            mPd!!.setOnDismissListener {
                d.dispose()
            }
        }
    }

    override fun onNext(value: HttpResult<T>) {

        when (value.code) {
            SUCCESS -> {
                onHandleSuccess(value.data, message = value.message) //成功
            }
            AUTHORITY_ERROR, SERVER_ERROR, FAIL_ERROR -> onHandleError(value.code, value.message)
            NO_LOGIN -> {

            }
        }
    }

    override fun onError(e: Throwable) {
        dismissDialog()
        LogUtils.d("Request error: " + e.toString() + "\n" + e.localizedMessage)
        onHandleError(511, "网络异常，请稍后再试")
    }

    override fun onComplete() {
        dismissDialog()
        onHandleComplete()
    }

    private fun dismissDialog() {
        if (mPd != null && mPd!!.isShowing) {
            mPd!!.dismiss()
        }
    }

    abstract fun onHandleSuccess(t: T?, message: String)

    open fun onHandleError(code: Int, message: String) {
        message.toast()
        onHandleComplete()
    }

    /**
     * 重写{$onHandleError} 一定要调用super.onHandleError(code, message),否则该方法不执行
     */
    open fun onHandleComplete() {}

    companion object {

        //HTTP状态码（200：成功；400：失败；401：未登入；403：权限不足，禁止访问；500：服务器异常”
        private const val SUCCESS = 200
        private const val FAIL_ERROR = 400
        private const val NO_LOGIN = 401
        private const val AUTHORITY_ERROR = 403
        private const val SERVER_ERROR = 500

    }
}
