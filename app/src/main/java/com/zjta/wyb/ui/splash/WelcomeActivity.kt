package com.zjta.wyb.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.zjta.wyb.R
import com.zjta.wyb.adapter.CommonFragmentPagerAdapter
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.ui.home.MainActivity
import com.zjta.wyb.utils.SPUtils
import kotlinx.android.synthetic.main.activity_welcome.*

class WelcomeActivity : BaseActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        //设置ViewPager
        val welcomeFragment3 = WelcomeFragment3()
        welcomeFragment3.setOnClickEnterListener(this)
        val fragmentList = arrayListOf<Fragment>(
            WelcomeFragment1(),
            WelcomeFragment2(),
            welcomeFragment3
        )
        val mViewPagerAdapter = CommonFragmentPagerAdapter(fragmentList, supportFragmentManager)
        vpContainer.adapter = mViewPagerAdapter
        circleIndicator.setViewPager(vpContainer)
    }




    companion object{
        fun start(context: Context){
            context.startActivity(Intent(context,WelcomeActivity::class.java))
        }
    }

    override fun onClick(view: View?) {
        SPUtils.setFirstInstall(this,false)
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
