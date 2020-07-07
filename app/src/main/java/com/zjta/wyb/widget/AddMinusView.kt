package com.zjta.wyb.widget

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import com.zjta.wyb.R
import com.zjta.wyb.download.listener.OnNumberChangeListener
import com.zjta.wyb.kotlin.closeKeyboard
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.utils.StringUtils

class AddMinusView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), View.OnClickListener {
    private var mEt: EditText
    private var mBtnAdd: View
    private var mBtnMinus: View
    private var mCurrentNum = 1 //购买数量
    private var mInventory = Int.MAX_VALUE //商品库存
    private var mListener: OnNumberChangeListener? = null

    init {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.view_add_minus, this)
        mBtnAdd = view.findViewById<View>(R.id.btnAdd)
        mBtnAdd.setOnClickListener(this)
        mBtnMinus = view.findViewById<View>(R.id.btnMinus)
        mBtnMinus.setOnClickListener(this)
        mEt = view.findViewById(R.id.etNum)
        mBtnMinus.isEnabled = false
        mEt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (StringUtils.isEmpty(s.toString())) {
                    mEt.setText("1")
                    mCurrentNum = 1
                    mEt.setSelection(mEt.text.length)
                } else {
                    val temp = s.toString()
                        .toInt()
                    if (temp > mInventory) {
                        mEt.setText(mInventory.toString())
                        mCurrentNum = mInventory
                        mEt.setSelection(mEt.text.length)
                    } else {
                        mCurrentNum = temp
                    }
                }
                mBtnMinus.isEnabled = mCurrentNum != 1
                mBtnAdd.isEnabled = mCurrentNum != mInventory
            }

        })
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnAdd -> {
                if (mCurrentNum < mInventory) {
                    mCurrentNum++
                    mEt.setText(mCurrentNum.toString())
                    mListener?.onChange(mCurrentNum)
                } else {
                    "已达到可购买上限".toast()
                }
            }
            R.id.btnMinus -> {
                if (mCurrentNum > 1) {
                    mCurrentNum--
                    mListener?.onChange(mCurrentNum)
                    mEt.setText(mCurrentNum.toString())
                }
            }
        }
        mEt.clearFocus()
        v.context.closeKeyboard(v)
        mBtnMinus.isEnabled = mCurrentNum != 1
        mBtnAdd.isEnabled = mCurrentNum != mInventory
    }

    /**
     * 设置库存最大数
     */
    fun setInventory(inventory: Int): AddMinusView {
        mInventory = inventory
        return this
    }

    /**
     * 设置当前的购买数量
     */
    fun setCurrentNum(num :Int){
        mCurrentNum=num
        mEt.setText(num.toString())
    }

    fun setOnNumberChangeListener(listener: OnNumberChangeListener) {
        mListener = listener
    }

}