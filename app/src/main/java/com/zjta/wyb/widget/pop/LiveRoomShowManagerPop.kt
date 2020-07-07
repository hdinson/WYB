package com.zjta.wyb.widget.pop

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.zjta.wyb.R
import com.zjta.wyb.widget.smartpopupwindow.SmartPopupWindow


class LiveRoomShowManagerPop(context: Context) : SmartPopupWindow(context), View.OnClickListener {

    init {

        val popView = LayoutInflater.from(context).inflate(R.layout.pop_live_room_shop_manager, FrameLayout(context))

        isOutsideTouchable = true
        animationStyle = R.style.pop_home_bottom

        popView.findViewById<View>(R.id.ivPusherEdit).setOnClickListener(this)
        popView.findViewById<View>(R.id.ivPusherImp).setOnClickListener(this)
        popView.findViewById<View>(R.id.ivPusherAdd).setOnClickListener(this)

        contentView = popView

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ivPusherEdit -> {
            }
            R.id.ivPusherImp -> {
            }
            R.id.ivPusherAdd -> {
            }
        }

    }


}