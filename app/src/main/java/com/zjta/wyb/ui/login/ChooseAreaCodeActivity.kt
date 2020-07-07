package com.zjta.wyb.ui.login

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zjta.wyb.R
import com.zjta.wyb.adapter.ChooseAreaCodeAdapter
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.utils.SystemBarModeUtils
import com.zjta.wyb.widget.recycleview.OnRvItemClickListener
import com.zjta.wyb.widget.recycleview.RvItemClickSupport
import kotlinx.android.synthetic.main.activity_choose_area_code.*

class ChooseAreaCodeActivity : BaseActivity() {

    private val mData = arrayListOf("中国香港特别行政区#+852", "中国台湾#+886", "中国大陆#+86", "中国澳门特别行政区#+853")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_area_code)
        SystemBarModeUtils.darkMode(this,true)

        titleBar.titleLeftBtn.click { onBackPressed() }
        rvAreaCode.layoutManager = LinearLayoutManager(this)
        rvAreaCode.adapter = ChooseAreaCodeAdapter(mData)
        RvItemClickSupport.addTo(rvAreaCode).setOnItemClickListener(OnRvItemClickListener { _: RecyclerView, _: View, poi: Int ->
            val intent = Intent()
            intent.putExtra(RESULT_DATE_CODE, mData[poi].split("#")[1])
            setResult(Activity.RESULT_OK, intent)
            finish()
        })
    }

    companion object {
        const val RESULT_DATE_CODE = ""
        const val REQUEST_CODE = 0x1001
        fun startActivityForResult(act: Activity) {
            act.startActivityForResult(Intent(act, ChooseAreaCodeActivity::class.java), REQUEST_CODE)
        }
    }
}
