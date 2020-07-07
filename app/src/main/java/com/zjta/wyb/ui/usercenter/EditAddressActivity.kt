package com.zjta.wyb.ui.usercenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.AddressEntity
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.forbiddenEnter
import com.zjta.wyb.kotlin.forbiddenSpace
import kotlinx.android.synthetic.main.activity_edit_address.*


class EditAddressActivity : BaseActivity() {

    private var mAddress: AddressEntity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_address)

        initUI()
    }

    @SuppressLint("SetTextI18n")
    private fun initUI() {
        mAddress = intent.getParcelableExtra(EXTRA_ADDRESS)
        mAddress?.let {
            etAddressName.setText(it.consignee)
            etAddressPhone.setText(it.phone)
            etAddressCode.setText(it.zipCode)
            tvAddressCity.text = "${it.province}/${it.city}/${it.county}"
            etAddressDetail.setText(it.address)
        }

        vPostAddress.click { post2Server() }
        tvAddressCity.click { showAddressSelector() }
        etAddressDetail.forbiddenEnter().forbiddenSpace()
    }


    /**
     * 地址选择器
     */
    private fun showAddressSelector() {


    }

    /**
     * 上传地址
     */
    private fun post2Server() {
        //todo
    }


    companion object {

        private const val EXTRA_ADDRESS = "address"
        fun start(context: Context, address: AddressEntity? = null) {
            val intent = Intent(context, EditAddressActivity::class.java)
            intent.putExtra(EXTRA_ADDRESS, address)
            context.startActivity(intent)
        }
    }
}
