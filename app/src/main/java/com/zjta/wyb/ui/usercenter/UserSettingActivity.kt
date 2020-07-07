package com.zjta.wyb.ui.usercenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity

class UserSettingActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_setting)
    }


    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context, UserSettingActivity::class.java))
        }
    }
}
