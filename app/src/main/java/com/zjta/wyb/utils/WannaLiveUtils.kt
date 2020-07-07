package com.zjta.wyb.utils

import android.graphics.Color
import com.zjta.wyb.entity.LiveVideo

object WannaLiveUtils {

    /**
     * 首页列表分类颜色
     * type: 10代购预约、11代购预告、20带游预约、21带游预告、30拼团预约、31拼团预告、40文章、41音频、42视频
     *
     * status 动态状态(10准备中、20等待中、30执行中)
     */
    fun getHomeModelItemColor(status: Int, type: Int): Pair<String, Int> {
        if (status == 30) return Pair("直播", Color.parseColor("#FEB938"))
        return when (type) {
            10, 11 -> Pair("代购", Color.parseColor("#2A62E8"))
            20, 21 -> Pair("带游", Color.parseColor("#7F94FF"))
            30, 31 -> Pair("拼团", Color.parseColor("#FE9730"))
            40 -> Pair("文章", Color.parseColor("#FF6795"))
            41 -> Pair("音频", Color.parseColor("#37DBAE"))
            42 -> Pair("视频", Color.parseColor("#51DCFE"))
            else -> Pair("", Color.TRANSPARENT)
        }
    }

    /**
     * 首页通用布局中间显示布局类型
     * 0。不显示
     * 1。显示@播服务费
     * 2。显示播放按钮
     */
    fun homeItemShowMiddleType(bean: LiveVideo): Int {
        if (bean.type == 41 || bean.type == 42) return 2
        if (bean.infoPrice != 0.0 && bean.status != 30) return 1
        return 0
    }

    /**
     * 动态价格对应的币种(10人民币、20波币、30分贝)
     */
    fun formatMoneyType(infoCoinType: Int): Pair<String, String> {
        return when (infoCoinType) {
            10 -> Pair("¥", "人民币")
            20 -> Pair("#", "波币")
            30 -> Pair("&", "分贝")
            else -> Pair("", "")
        }
    }
}