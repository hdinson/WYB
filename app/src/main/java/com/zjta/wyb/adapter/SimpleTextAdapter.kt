package com.zjta.wyb.adapter

import com.zjta.wyb.R
import com.zjta.wyb.entity.HomeHistoryHeader
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_home_history_header.view.*
import kotlinx.android.synthetic.main.item_simple_text.view.*


/**
 * 首页足迹列表
 */
class SimpleTextAdapter(dataList: List<String>) :
    CommonAdapter<String>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_simple_text

    override fun convert(holder: CommonViewHolder, dataBean: String, position: Int) {
        holder.itemView.tvText.text = dataBean
    }
}
