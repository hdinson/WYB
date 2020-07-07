package com.zjta.wyb.ui.live

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.CheckBox
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.LivingWindowShopAdapter
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.WindowShopGoods
import com.zjta.wyb.event.WindowShopEv
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.SpanUtils
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.utils.SystemBarModeUtils
import kotlinx.android.synthetic.main.activity_living_window_shop.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class LivingWindowShopActivity : BaseActivity() {

    private val mData = ArrayList<WindowShopGoods>()
    private val mAdapter = LivingWindowShopAdapter(mData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_living_window_shop)
        SystemBarModeUtils.darkMode(this, true)

        initUI()
    }

    private fun initUI() {
        formatMoneyCount(0, 0.00)
        cbSelectAll.setOnClickListener { view ->
            val checkBox = view as CheckBox
            mData.forEach { it.isSelected = checkBox.isChecked }
            mAdapter.notifyDataSetChanged()
            calculateGoods()
        }

        for (index in 1..20) {
            val element = WindowShopGoods()
            element.name = "钟薛高甜蜜系列丝绒可可*10巧克$index"
            element.brand = "钟薛高$index"
            element.activityPrice = 1221.20 + index
            element.manufacturer = "福建省漳州市龙海市某某工业区"
            element.netWeight = "128g"
            mData.add(element)
        }

        rvWindowShop.layoutManager = LinearLayoutManager(this)
        rvWindowShop.adapter = mAdapter

        //下单
        vPayFor.click {
            mData.filter { it.isSelected }
                .apply {
                    if (this.isNotEmpty()) {
                        LivingOrderPayActivity.start(it.context, this as ArrayList<WindowShopGoods>)
                    } else "请选择商品再下单".toast()
                }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: WindowShopEv) {
        calculateGoods()
    }

    /**
     * 计算选中的橱窗商品
     */
    private fun calculateGoods() {
        var isSelectAll = true
        var countNum = 0
        var countMoney = 0.0
        mData.forEach {
            if (it.isSelected) {
                countNum += it.selectGoodsNum
                countMoney += it.selectGoodsNum * it.activityPrice
            } else isSelectAll = false
        }
        cbSelectAll.isChecked = isSelectAll
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault()
            .register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault()
            .unregister(this)
    }


    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, LivingWindowShopActivity::class.java))
        }
    }
}
