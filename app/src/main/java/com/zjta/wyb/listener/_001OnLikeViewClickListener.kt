package com.zjta.wyb.listener

import android.widget.CheckBox
import com.zjta.wyb.entity.UserInfo

/**
 *  玩安卓收藏点击监听
 */
interface _001OnLikeViewClickListener {
    fun onClickLikeView(likeView: CheckBox, dataBean: UserInfo.WybVnoBean, position:Int)
}