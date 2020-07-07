package com.zjta.wyb.ui.recharge

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.RechargeDetailAdapter
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.RechargeDetail
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.utils.SystemBarModeUtils
import com.zjta.wyb.widget.recycleview.LinearItemDecoration
import kotlinx.android.synthetic.main.activity_recharge_detail.*

class RechargeDetailActivity : BaseActivity() {

    private val mData = ArrayList<RechargeDetail>()
    private val mAdapter = RechargeDetailAdapter(mData)
    private var mPage = 0
    private var mPageSize = 20

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recharge_detail)

        initUI()
    }

    private fun initUI() {
        SystemBarModeUtils.darkMode(this, true)
        commonTitleBar.titleLeftBtn.click { onBackPressed() }

        for (index in 0..mPageSize) {
            val element = RechargeDetail()
            element.name = "test $index"
            element.balanceCount = "余额：${index + 1}"
            element.time = "2020-02-02"
            element.balance = "￥ 100${index}"
            mData.add(element)
        }

        rvContent.adapter = mAdapter
        rvContent.layoutManager = LinearLayoutManager(this)
        rflRechargeDetail.setOnRefreshListener {
            mPage++
            mData.clear()
            for (index in 0..mPageSize) {
                val element = RechargeDetail()
                element.name = "test $index - $mPage"
                element.balanceCount = "余额：${index + 1}"
                element.time = "2020-02-02"
                element.balance = "￥ 100${index}"
                mData.add(element)
            }
            mAdapter.notifyDataSetChanged()
            rflRechargeDetail.finishRefresh(2000)
        }
        rflRechargeDetail.setOnLoadMoreListener {
            val element = RechargeDetail()
            element.name = "test new"
            element.balanceCount = "余额：$01"
            element.time = "2020-02-04"
            element.balance = "￥ 100"
            mData.add(element)
            mAdapter.notifyDataSetChanged()
            rflRechargeDetail.finishLoadMore(2000)
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, RechargeDetailActivity::class.java))
        }
    }

}
