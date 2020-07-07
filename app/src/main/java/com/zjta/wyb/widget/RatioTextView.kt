package com.zjta.wyb.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.zjta.wyb.R

/**
 * 比例的TextView
 */
class RatioTextView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatTextView(context, attrs) {


    private var mRatio: Float

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RatioTextView)
        mRatio = attributes.getFloat(R.styleable.RatioTextView_textRatio, 1f)
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        val childWidthSize = measuredWidth
        //高度和宽度一样
        val wid = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        super.onMeasure(wid, widthMeasureSpec);

    }

    public fun setRatio(ratio: Float) {
        mRatio = ratio
       // requestLayout()
    }
}