package com.zjta.wyb.ui.home

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.CommonFragmentPagerAdapter
import com.zjta.wyb.adapter.HomeHistoryHeaderAdapter
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.entity.HomeHistoryHeader
import com.zjta.wyb.event.HomeAppbarEv
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.listener.SlidingTabSelectorScale
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.utils.SystemBarModeUtils
import com.zjta.wyb.widget.recycleview.OnRvItemClickListener
import com.zjta.wyb.widget.recycleview.RvItemClickSupport
import kotlinx.android.synthetic.main.home_fragment.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * 首页
 */
class HomeFragment : ViewPagerLazyFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.home_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        SystemBarModeUtils.setPaddingTop(view.context, viewContainer)
    }

    override fun lazyInit() {
        LogUtils.v("HomeFragment lazyInit")
        val titles = arrayOf("预约", "发现", "现在", "关注")
        val fragmentList =
            arrayListOf(HomeRequireFragment(), HomeDiscoverFragment(), HomeNowFragment(), HomeAttentionFragment())
        vpHomeContent.offscreenPageLimit = fragmentList.size
        if (fragmentManager !== null) {
            val mViewPagerAdapter = CommonFragmentPagerAdapter(fragmentList, childFragmentManager)
            vpHomeContent.adapter = mViewPagerAdapter
        }
        vpHomeContent.overScrollMode = View.OVER_SCROLL_NEVER
        stlTopTeb.setViewPager(vpHomeContent, titles)
        vpHomeContent.addOnPageChangeListener(SlidingTabSelectorScale(stlTopTeb))


        //初始化足迹
        val list = arrayListOf(HomeHistoryHeader("http://dinson.dujc.cn/FvqOjFv6Vt3uW1LHdCzNksREmv3S.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/Fi8e4rUvYUC3tp5UzU6mUQh4SkTK.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/2d4bf309b03e8340cf0178b558b15dad.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/FnFicLrus5vXqAw5oElFFXL1-zb3.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/FghQpL5VATXt6Wtlm575wiDseyvI.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/FoPzP9JbDTqxMlhWCRvxPUo24IRn.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/FtnPm05owdbcJC_7mf53nbOcq2Gy.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/FuAVy3cV-_oNaNbqhidirKgpR_Yd.webp"),
                               HomeHistoryHeader("http://dinson.dujc.cn/FkLRL9du9H1_jy2C1yFTmCGswQSZ.webp"))
        vpHomeContent.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}

            override fun onPageScrolled(a: Int, b: Float, c: Int) {}

            override fun onPageSelected(position: Int) {
                showHistory(false)
            }
        })

        rvHistoryHeader.apply {
            val manager = LinearLayoutManager(context)
            manager.orientation = LinearLayoutManager.HORIZONTAL
            layoutManager = manager
            adapter = HomeHistoryHeaderAdapter(list)
            RvItemClickSupport.addTo(this)
                .setOnItemClickListener(OnRvItemClickListener { _, _, position ->
                    "${list[position]}".toast()
                })
        }
    }


    /**
     * 控制足迹
     */
    private fun showHistory(show: Boolean) {
        if (show) {
            if (topIsShow().not()) {
                val anim = ValueAnimator.ofInt(0, 299)
                    .setDuration(300)
                anim.addUpdateListener {
                    val height = it.animatedValue as Int
                    val layoutParams = rvHistoryHeader.layoutParams
                    layoutParams.height = height
                    rvHistoryHeader.layoutParams = layoutParams
                }
                anim.interpolator = DecelerateInterpolator()
                anim.start()
            }
        } else {
            if (topIsShow()) {
                val anim = ValueAnimator.ofInt(299, 0)
                    .setDuration(300)
                anim.addUpdateListener {
                    val height = it.animatedValue as Int
                    val layoutParams = rvHistoryHeader.layoutParams
                    layoutParams.height = height
                    rvHistoryHeader.layoutParams = layoutParams
                }
                anim.interpolator = DecelerateInterpolator()
                anim.start()
            }
        }
    }

    private fun topIsShow(): Boolean {
        return rvHistoryHeader.layoutParams.height != 0
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: HomeAppbarEv) {
        showHistory(event.isOpen)
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
}