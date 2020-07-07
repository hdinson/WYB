package com.zjta.wyb.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import com.zjta.wyb.R
import com.zjta.wyb.entity.AddressEntity
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_address_manager.view.*


/**
 * 收货地址
 */
class AddressManagerAdapter(dataList: List<AddressEntity>) : CommonAdapter<AddressEntity>(dataList) {

    private val mColorGey = Color.parseColor("#FAFBFC")
    private val mColorYellow = Color.parseColor("#FFFAF5")

    override fun getLayoutId(viewType: Int) = R.layout.item_address_manager

    @SuppressLint("SetTextI18n")
    override fun convert(holder: CommonViewHolder, dataBean: AddressEntity, position: Int) {
        holder.itemView.tvItemAddress.text =
            "${dataBean.consignee}   ${dataBean.phone}\n${dataBean.province}${dataBean.city}${dataBean.county}${dataBean.address}   ${dataBean.zipCode}"
        holder.itemView.tvItemAddress.setBackgroundColor(if (position % 2 == 0) mColorYellow else mColorGey)
    }
}
