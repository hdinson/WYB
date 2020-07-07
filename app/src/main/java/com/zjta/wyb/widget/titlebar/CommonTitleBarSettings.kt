package com.zjta.wyb.widget.titlebar

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import com.zjta.wyb.R

/**
 * 标题栏设置
 */
class CommonTitleBarSettings internal constructor(
    context: Context, attrs: AttributeSet?
) {
    var titleText: String? = ""
    var rightText: String? = ""
    val isLeftBtnVisible: Boolean
    val isRightBtnVisible: Boolean
    val isRightTvVisible: Boolean
    val ancestorFitsSystemWindow: Boolean
    val titleTextColor: Int
    val rightTextColor: Int
    val leftBtnDrawable: Int
    val rightBtnDrawable: Int
    val backGroundResource: Int

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.CommonTitleBar)
        isLeftBtnVisible = attributes.getBoolean(R.styleable.CommonTitleBar_leftBtnVisible, false)
        isRightBtnVisible = attributes.getBoolean(R.styleable.CommonTitleBar_rightBtnVisible, false)
        isRightTvVisible = attributes.getBoolean(R.styleable.CommonTitleBar_rightTextVisible, false)
        leftBtnDrawable = attributes.getResourceId(R.styleable.CommonTitleBar_leftBtnDrawable, R.mipmap.ic_back_black)
        rightBtnDrawable = attributes.getResourceId(R.styleable.CommonTitleBar_rightBtnDrawable, R.mipmap.ic_back_black)
        titleText = attributes.getString(R.styleable.CommonTitleBar_titleText)
        titleTextColor = attributes.getColor(R.styleable.CommonTitleBar_titleTextColor, Color.BLACK)
        rightText = attributes.getString(R.styleable.CommonTitleBar_rightText)
        rightTextColor = attributes.getColor(R.styleable.CommonTitleBar_rightTextColor, Color.BLACK)
        ancestorFitsSystemWindow = attributes.getBoolean(R.styleable.CommonTitleBar_ancestorFitsSystemWindow, false)

        backGroundResource =
            attributes.getResourceId(R.styleable.CommonTitleBar_backgroundResource, R.drawable.common_title_bar_bg)

        attributes.recycle()
    }
}