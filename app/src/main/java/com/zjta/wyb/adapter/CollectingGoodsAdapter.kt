package com.zjta.wyb.adapter

import com.zjta.wyb.R
import com.zjta.wyb.entity.CollectingGoods
import com.zjta.wyb.entity.RechargeDetail
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_recharge_detail.view.*


/**
 * 收藏夹
 */
class CollectingGoodsAdapter(dataList: List<CollectingGoods>) : CommonAdapter<CollectingGoods>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_recharge_detail

    override fun convert(holder: CommonViewHolder, dataBean: CollectingGoods, position: Int) {

    }
}
