package com.zjta.wyb.adapter

import com.zjta.wyb.R
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_choose_area_code.view.*


/**
 * 选择国家区号
 */
class ChooseAreaCodeAdapter(dataList: List<String>) :
    CommonAdapter<String>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_choose_area_code

    override fun convert(holder: CommonViewHolder, dataBean: String, position: Int) {
        val data = dataBean.split("#")
        holder.itemView.tvName.text = data[0]
        holder.itemView.tvAreaCode.text = data[1]
    }
}
