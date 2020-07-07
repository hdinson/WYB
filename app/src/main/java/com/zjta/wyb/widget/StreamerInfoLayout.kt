package com.zjta.wyb.widget

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.zjta.wyb.R


class StreamerInfoLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs) {

    private val containerView: View = View.inflate(context, R.layout.layout_order_stream_info, this)



}