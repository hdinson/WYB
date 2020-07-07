package com.zjta.wyb.ui.live

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.UserInfo
import com.zjta.wyb.entity.WindowShopGoods
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.ui.usercenter.AddressManagerActivity
import com.zjta.wyb.utils.*
import kotlinx.android.synthetic.main.activity_living_order_pay.*

class LivingOrderPayActivity : BaseActivity() {

    private val mGoodsList by lazy {
        intent.getParcelableArrayListExtra<WindowShopGoods>(EXTRA_GOODS_LIST)
    }

    private var mUserInfo: UserInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_living_order_pay)
        SystemBarModeUtils.darkMode(this, true)

        initUI()
    }

    /**
     * 初始化
     */
    private fun initUI() {
        buildOrderGoods(mGoodsList)
        calculateGoods(mGoodsList)

        mUserInfo = SPUtils.getUserInfo()
        mUserInfo?.let {
            GlideUtils.setAvatarCircle(this, it.imgUrl, ivAvatar)
        }
        tvAddressLocation.click { AddressManagerActivity.start(it.context) }
    }


    @SuppressLint("SetTextI18n")
    private fun buildOrderGoods(goodsList: ArrayList<WindowShopGoods>) {
        goodsList.forEach {
            val view = layoutInflater.inflate(R.layout.item_pay_order_goods, llOrderGoodsContainer, false)
            val ivGoods = view.findViewById<ImageView>(R.id.ivGoods)
            GlideUtils.setImage(this@LivingOrderPayActivity, it.imageUrl, ivGoods)
            view.findViewById<TextView>(R.id.tvGoodsName)
                .text = it.name
            view.findViewById<TextView>(R.id.tvMoney)
                .text = "¥ ${StringUtils.formatMoney(it.activityPrice)}"
            view.findViewById<TextView>(R.id.tvCount)
                .text = "x${it.selectGoodsNum}"
            llOrderGoodsContainer.addView(view)
        }
    }

    /**
     * 计算选中的橱窗商品
     */
    private fun calculateGoods(goodsList: ArrayList<WindowShopGoods>) {
        var countNum = 0
        var countMoney = 0.0
        goodsList.forEach {
            if (it.isSelected) {
                countNum += it.selectGoodsNum
                countMoney += it.selectGoodsNum * it.activityPrice
            }
        }
        formatMoneyCount(countNum, countMoney)
    }


    /**
     * 格式化商品总计
     */
    private fun formatMoneyCount(num: Int, money: Double) {
        val formatMoney = StringUtils.formatMoney(money)
        SpanUtils.with(tvMoneyCount)
            .append("共 $num 件\n合计: ")
            .append("¥ $formatMoney")
            .setBold()
            .setForegroundColor(Color.parseColor("#292929"))
            .create()
    }

    companion object {

        private const val EXTRA_GOODS_LIST = "goods_list"
        fun start(context: Context, goodsList: ArrayList<WindowShopGoods>) {
            val intent = Intent(context, LivingOrderPayActivity::class.java)
            intent.putParcelableArrayListExtra(EXTRA_GOODS_LIST, goodsList)
            context.startActivity(intent)
        }
    }
}
