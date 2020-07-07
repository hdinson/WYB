package com.zjta.wyb.adapter

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.View
import com.zjta.wyb.R
import com.zjta.wyb.entity.LiveVideo
import com.zjta.wyb.utils.DateUtils
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.utils.WannaLiveUtils
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_appointment_require.view.*


/**
 * 有约-我的发单样式
 */
class AppointmentRequireAdapter(dataList: List<LiveVideo>) : CommonAdapter<LiveVideo>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_appointment_require

    @SuppressLint("SetTextI18n")
    override fun convert(holder: CommonViewHolder, dataBean: LiveVideo, position: Int) {
        holder.itemView.container.setBackgroundColor(
            if (position % 2 == 0) Color.WHITE else Color.parseColor("#f7f7f7"))
        GlideUtils.setImage(holder.itemView.context, dataBean.imgUrl, holder.itemView.ivImage)

        val state = WannaLiveUtils.getHomeModelItemColor(dataBean.status, dataBean.type)
        holder.itemView.tvItemType.visibility = if (StringUtils.isEmpty(state.first)) View.GONE else View.VISIBLE
        val cl = ColorStateList(arrayOf(IntArray(0)), intArrayOf(state.second))
        holder.itemView.tvItemType.backgroundTintList = cl
        holder.itemView.tvItemType.text = state.first

        holder.itemView.tvTitleName.text = dataBean.name
        holder.itemView.tvTime.text = DateUtils.long2Str(dataBean.createTimeLong, "yyyy/MM/dd")
        holder.itemView.tvPrice.text = "${WannaLiveUtils.formatMoneyType(dataBean.infoCoinType).first}${dataBean
            .infoPrice}"
        holder.itemView.tvEndTime.text = DateUtils.long2Str(dataBean.executeEndTimeLong, "yyyy/MM/dd")
        holder.itemView.tvStoreCount.text = dataBean.vonNum
    }
}
