package com.zjta.wyb.adapter

import com.zjta.wyb.R
import com.zjta.wyb.entity.HomeHistoryHeader
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_home_history_header.view.*


/**
 * 首页足迹列表
 */
class HomeHistoryHeaderAdapter(dataList: List<HomeHistoryHeader>) :
    CommonAdapter<HomeHistoryHeader>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_home_history_header

    override fun convert(holder: CommonViewHolder, dataBean: HomeHistoryHeader, position: Int) {
        GlideUtils.setImage(holder.itemView.context, dataBean.imageUrl, holder.itemView.ivImg)
    }
}
