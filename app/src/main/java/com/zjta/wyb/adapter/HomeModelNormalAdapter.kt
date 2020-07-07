package com.zjta.wyb.adapter

import android.content.res.ColorStateList
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.view.View
import com.zjta.wyb.R
import com.zjta.wyb.entity.LiveVideo
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.utils.WannaLiveUtils
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_home_model_normal.view.*


/**
 * 首页统一通用列表样式
 */
class HomeModelNormalAdapter(dataList: List<LiveVideo>) : CommonAdapter<LiveVideo>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_home_model_normal

    override fun convert(holder: CommonViewHolder, dataBean: LiveVideo, position: Int) {
        GlideUtils.setImage(holder.itemView.context, dataBean.imgUrl, holder.itemView.rIvImg)
        if (dataBean.status == 30) {
            holder.itemView.rIvImg.setRatio(1.5f)
        } else {
            holder.itemView.rIvImg.setRatio(1f)
        }
        holder.itemView.tvTitle.text = dataBean.name
        holder.itemView.tvNickName.text = dataBean.userName
        holder.itemView.tvDesc.text = dataBean.comment
        holder.itemView.tvViewNum.text = dataBean.browseQty.toString()
        GlideUtils.setAvatarCircle(holder.itemView.context, dataBean.userImgUrl, holder.itemView.ivAvatar)

        val state = WannaLiveUtils.getHomeModelItemColor(dataBean.status, dataBean.type)
        val cl = ColorStateList(arrayOf(IntArray(0)), intArrayOf(state.second))
        holder.itemView.vBottomDivider.setBackgroundColor(state.second)
        holder.itemView.tvItemType.backgroundTintList = cl
        holder.itemView.tvItemType.text = state.first

        //处理中间布局
        when (WannaLiveUtils.homeItemShowMiddleType(dataBean)) {
            1 -> {
                //显示服务费
                holder.itemView.ivVideoPlay.visibility = View.INVISIBLE
                holder.itemView.gMoney.visibility = View.VISIBLE

                val infoPrice = dataBean.infoPrice
                val span = RelativeSizeSpan(0.7f)
                val spannableString = SpannableString("波币\n$infoPrice")
                spannableString.setSpan(span, 0, 2, Spanned.SPAN_INCLUSIVE_EXCLUSIVE)
                holder.itemView.tvMoney.text = spannableString
            }
            2 -> {
                //显示播放
                holder.itemView.ivVideoPlay.visibility = View.VISIBLE
                holder.itemView.gMoney.visibility = View.INVISIBLE
            }
            else -> {
                //全部隐藏
                holder.itemView.ivVideoPlay.visibility = View.INVISIBLE
                holder.itemView.gMoney.visibility = View.INVISIBLE
            }
        }



    }
}
