package com.zjta.wyb.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.widget.CheckBox
import com.zjta.wyb.R
import com.zjta.wyb.download.listener.OnNumberChangeListener
import com.zjta.wyb.entity.WindowShopGoods
import com.zjta.wyb.event.WindowShopEv
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.CommonViewHolder
import kotlinx.android.synthetic.main.item_pushing_window_shop.view.*
import org.greenrobot.eventbus.EventBus


/**
 * 推流端橱窗商品 - 列表
 */
class PushingWindowShopAdapter(dataList: List<WindowShopGoods>) : CommonAdapter<WindowShopGoods>(dataList) {

    override fun getLayoutId(viewType: Int) = R.layout.item_pushing_window_shop

    private val colorGrey = Color.parseColor("#F7F7F7")
    private val colorWhite = Color.parseColor("#FFFFFF")

    @SuppressLint("SetTextI18n")
    override fun convert(holder: CommonViewHolder, dataBean: WindowShopGoods, position: Int) {
        holder.itemView.rootView.setBackgroundColor(if (position % 2 == 0) colorWhite else colorGrey)
        holder.itemView.tvGoodsName.text = dataBean.name
        holder.itemView.tvGoodsDesc.text = appendGoodsDesc(dataBean)

        holder.itemView.tvMoney.text = "¥${dataBean.activityPrice}"
        holder.itemView.cbGoods.isChecked = dataBean.isSelected

        holder.itemView.cbGoods.setOnClickListener {
            val checkBox = it as CheckBox
            dataBean.isSelected = checkBox.isChecked
            EventBus.getDefault()
                .post(WindowShopEv.getInstance(true))
        }
        GlideUtils.loadImage(holder.itemView.context,dataBean.imgUrl,into = holder.itemView.ivGoods)
    }

    private fun appendGoodsDesc(bean: WindowShopGoods): String {
        val builder = StringBuilder()
        if (StringUtils.isNotEmpty(bean.brand)) {
            builder.append("品牌: ${bean.brand}\n")
        }
        if (StringUtils.isNotEmpty(bean.specification)) {
            builder.append("规格: ${bean.specification}\n")
        }
        if (StringUtils.isNotEmpty(bean.manufacturer)) {
            builder.append("生产商: ${bean.manufacturer}\n")
        }
        if (StringUtils.isNotEmpty(bean.netWeight)) {
            builder.append("净含量: ${bean.netWeight}")
        }
        return builder.toString()
    }


}
