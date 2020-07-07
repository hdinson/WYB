package com.zjta.wyb.adapter

import com.zjta.wyb.R
import com.zjta.wyb.entity.RechargeDetail
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_recharge_detail.view.*


/**
 * 充值明细
 */
class RechargeDetailAdapter(dataList: List<RechargeDetail>) : CommonAdapter<RechargeDetail>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_recharge_detail

    override fun convert(holder: CommonViewHolder, dataBean: RechargeDetail, position: Int) {
        holder.itemView.tvRechargeName.text = dataBean.name
        holder.itemView.tvBalanceDetail.text = dataBean.balanceCount
        holder.itemView.tvBalance.text = dataBean.balance
        holder.itemView.tvTime.text = dataBean.time
    }
}
