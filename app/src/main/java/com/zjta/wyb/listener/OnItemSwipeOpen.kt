package com.zjta.wyb.listener

import com.zjta.wyb.entity.UserInfo


/**
 *  货币滑动监听
 */
interface OnItemSwipeOpen {
    fun onOpen(view: UserInfo.WybVnoBean, position: Int)
}