package com.zjta.wyb.widget.dialog

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.zjta.wyb.R

class AppProgressDialog @JvmOverloads constructor(context: Context, theme: Int = R.style.AppDialog) :
    AlertDialog(context, theme) {
    private var mCancelable = true

    constructor(context: Context, cancelable: Boolean) : this(context, R.style.AppDialog) {
        mCancelable = cancelable
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(mCancelable)
        setCanceledOnTouchOutside(mCancelable)
        val inflate = layoutInflater.inflate(R.layout.app_progress_loading, null, false)
        setContentView(inflate)
        window?.apply {
            val params = this.attributes
            params.width = WindowManager.LayoutParams.WRAP_CONTENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            this.attributes = params
        }
    }
}