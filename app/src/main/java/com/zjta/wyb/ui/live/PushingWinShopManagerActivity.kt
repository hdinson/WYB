package com.zjta.wyb.ui.live

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.CommonFragmentPagerAdapter
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.closeKeyboard
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.listener.SlidingTabSelectorScale
import com.zjta.wyb.utils.SystemBarModeUtils
import kotlinx.android.synthetic.main.activity_pushing_win_shop_manager.*

class PushingWinShopManagerActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pushing_win_shop_manager)
        SystemBarModeUtils.darkMode(this, true)
        SystemBarModeUtils.setPaddingTop(this, llTopBar)

        initUI()
    }

    private fun initUI() {
        ivTitleBack.click { onBackPressed() }
        val titles = arrayOf("添加", "商品导入", "添加商品")
        val liveRoomId = intent.getIntExtra(EXTRA_LIVE_ROOM_ID, -1)
        if (liveRoomId < 0) {
            "直播间异常".toast()
            onBackPressed()
            return
        }
        val fragmentList: ArrayList<Fragment> = arrayListOf(PushingWinShopAddFragment().newInstance(liveRoomId),
                                                            PushingWinShopImpFragment().newInstance(liveRoomId),
                                                            PushingWinShopEditFragment().newInstance(liveRoomId))
        vpLivingWinShop.offscreenPageLimit = fragmentList.size
        val mViewPagerAdapter = CommonFragmentPagerAdapter(fragmentList, supportFragmentManager)
        vpLivingWinShop.adapter = mViewPagerAdapter
        vpLivingWinShop.currentItem = intent.getIntExtra(EXTRA_INDEX, 1)
        vpLivingWinShop.overScrollMode = View.OVER_SCROLL_NEVER
        stlLivingWinShop.setViewPager(vpLivingWinShop, titles)
        vpLivingWinShop.addOnPageChangeListener(SlidingTabSelectorScale(stlLivingWinShop))
        vpLivingWinShop.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(a: Int, b: Float, c: Int) {}

            override fun onPageSelected(position: Int) {
                closeKeyboard(vpLivingWinShop)
            }
        })
    }


    companion object {
        private const val EXTRA_INDEX = "extra_index"
        private const val EXTRA_LIVE_ROOM_ID = "extra_ive_room_id"
        fun start(context: Context, index: Int, liveRoomId: Int) {
            val intent = Intent(context, PushingWinShopManagerActivity::class.java)
            intent.putExtra(EXTRA_INDEX, index)
            intent.putExtra(EXTRA_LIVE_ROOM_ID, liveRoomId)
            context.startActivity(intent)
        }
    }
}
