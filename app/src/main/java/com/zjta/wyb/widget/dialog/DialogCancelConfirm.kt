package com.zjta.wyb.widget.dialog

import android.app.Dialog
import android.content.Context
import android.view.View
import android.widget.TextView
import com.zjta.wyb.R

/**
 * 提示框
 */
class DialogCancelConfirm(context: Context) : Dialog(context, R.style.AppDialog), View.OnClickListener {

    private var operationListener: OnOperationClickListener? = null
    private var singleClickListener: OnDialogSingleClickListener? = null

    fun setOperationListener(operationListener: OnOperationClickListener): DialogCancelConfirm {
        this.operationListener = operationListener
        return this
    }

    fun setDialogSingleClickListener(listener: OnDialogSingleClickListener): DialogCancelConfirm {
        this.singleClickListener = listener
        return this
    }

    fun setMessage(message: CharSequence): DialogCancelConfirm {
        findViewById<TextView>(R.id.dialogText).text = message
        return this
    }

    fun setButtonText(left: CharSequence, right: CharSequence): DialogCancelConfirm {
        findViewById<View>(R.id.dialogRightBtn).visibility = View.VISIBLE
        findViewById<View>(R.id.leftRightDivider).visibility = View.VISIBLE
        findViewById<TextView>(R.id.dialogLeftBtn).text = left
        findViewById<TextView>(R.id.dialogRightBtn).text = right
        return this
    }

    fun setButtonText(text: CharSequence): DialogCancelConfirm {
        findViewById<View>(R.id.dialogRightBtn).visibility = View.GONE
        findViewById<View>(R.id.leftRightDivider).visibility = View.GONE
        findViewById<TextView>(R.id.dialogLeftBtn).text = text
        return this
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.dialogLeftBtn -> {
                when {
                    singleClickListener != null -> {
                        singleClickListener!!.onClick(this)
                    }
                    operationListener != null -> {
                        operationListener!!.onClick(this, true)
                    }
                    else -> cancel()
                }
            }
            R.id.dialogRightBtn -> operationListener?.onClick(this, false)
        }
    }

    init {
        setContentView(R.layout.dialog_cancel_confirm)
        findViewById<View>(R.id.dialogLeftBtn).setOnClickListener(this)
        findViewById<View>(R.id.dialogRightBtn).setOnClickListener(this)
    }
}