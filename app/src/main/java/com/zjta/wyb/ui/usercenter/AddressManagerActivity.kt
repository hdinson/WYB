package com.zjta.wyb.ui.usercenter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.datepicker.MaterialDatePicker
import com.zjta.wyb.R
import com.zjta.wyb.adapter.AddressManagerAdapter
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.AddressEntity
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.dip
import com.zjta.wyb.utils.SystemBarModeUtils
import com.zjta.wyb.widget.recycleview.LinearItemDecoration
import com.zjta.wyb.widget.recycleview.OnRvItemClickListener
import com.zjta.wyb.widget.recycleview.RvItemClickSupport
import kotlinx.android.synthetic.main.activity_address_manager.*


/**
 * 收货地址管理
 */
class AddressManagerActivity : BaseActivity() {

    private val mData = ArrayList<AddressEntity>()
    private val mAdapter = AddressManagerAdapter(mData)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_address_manager)
        SystemBarModeUtils.darkMode(this, true)
        actionAddAddress.click { EditAddressActivity.start(this, null) }


        initUI()
    }

    private fun initUI() {

        //todo create
        for (index in 0..10) {
            val entity = AddressEntity()
            entity.consignee = "Moumou $index"
            entity.address = "江滨南路中央运营区6号楼"
            entity.phone = "13502021326"
            entity.zipCode = "362000"
            entity.province = "福建省"
            entity.city = "泉州市"
            entity.county = "晋江市"
            mData.add(entity)
        }



        rvAddressContent.adapter = mAdapter
        rvAddressContent.layoutManager = LinearLayoutManager(this)
        RvItemClickSupport.addTo(rvAddressContent)
            .setOnItemClickListener(OnRvItemClickListener { _, _, poi ->
                EditAddressActivity.start(this@AddressManagerActivity, mData[poi])
            })
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, AddressManagerActivity::class.java))
        }
    }
}
