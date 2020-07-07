package com.zjta.wyb.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.zjta.wyb.R

/**
 * 比例的ImageView
 */
class RatioImageView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {


    private var mRatio: Float

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.RatioImageView)
        mRatio = attributes.getFloat(R.styleable.RatioImageView_imgRatio, 1f)
        attributes.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        setMeasuredDimension(widthSize, (widthSize * mRatio).toInt())
    }

    public fun setRatio(ratio: Float) {
        mRatio = ratio
        requestLayout()
    }
}