package com.zjta.wyb.widget

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.zjta.wyb.R


class UserCenterHomeItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs) {


    private val tvBubbleNum: TextView

    init {
        val view = View.inflate(context, R.layout.item_user_center_home, this)
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.UserCenterHomeItem)

        val titleString = attributes.getString(R.styleable.UserCenterHomeItem_itemTitle) ?: ""
        val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        tvTitle.text = titleString

        val backgroundColor = attributes.getColor(R.styleable.UserCenterHomeItem_itemBackgroundColor, Color.WHITE)
        setBackgroundColor(backgroundColor)

        val backgroundDrawable = attributes.getDrawable(R.styleable.UserCenterHomeItem_itemBackgroundDrawable)
        background = backgroundDrawable


        val itemDrawableStartVisible =
            attributes.getBoolean(R.styleable.UserCenterHomeItem_itemDrawableStartVisible, true)

        if (itemDrawableStartVisible) {
            val leftDrawable =
                attributes.getResourceId(R.styleable.UserCenterHomeItem_itemDrawableStart, R.mipmap.ic_uc_comment)
            val drawableLeft = resources.getDrawable(leftDrawable, null)
            drawableLeft.setBounds(0, 0, drawableLeft.minimumWidth, drawableLeft.minimumHeight);
            tvTitle.setCompoundDrawables(drawableLeft, null, null, null)
        }

        val itemBubbleVisible = attributes.getBoolean(R.styleable.UserCenterHomeItem_itemBubbleVisible, false)
        tvBubbleNum = view.findViewById(R.id.tvBubbleNum)
        tvBubbleNum.visibility = if (itemBubbleVisible) View.VISIBLE else View.GONE

        attributes.recycle()
    }
}