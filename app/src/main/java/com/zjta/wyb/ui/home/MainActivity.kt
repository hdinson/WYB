package com.zjta.wyb.ui.home

import android.Manifest
import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zjta.wyb.BuildConfig
import com.zjta.wyb.R
import com.zjta.wyb.adapter.CommonFragmentPagerAdapter
import com.zjta.wyb.api.LivePlayerApi
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.LivePusherRoom
import com.zjta.wyb.entity.TestStreamPusher
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.ui.TestActivity
import com.zjta.wyb.ui.daigou.CreateDaiGouOddActivity
import com.zjta.wyb.ui.live.LivePlayerActivity
import com.zjta.wyb.ui.live.PreparePusherActivity
import com.zjta.wyb.ui.login.LoginActivity
import com.zjta.wyb.ui.usercenter.VManagerActivity
import com.zjta.wyb.utils.DateUtils
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.widget.bottomrarlayout.OnOtherViewClickListener
import com.zjta.wyb.widget.dialog.DialogCancelConfirm
import com.zjta.wyb.widget.dialog.OnOperationClickListener
import com.zjta.wyb.widget.smartpopupwindow.SmartPopupWindow
import com.zjta.wyb.widget.smartpopupwindow.VerticalPosition.ABOVE
import com.zjta.wyb.widget.smartpopupwindow.VerticalPosition.CENTER
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), OnOtherViewClickListener, PopupWindow.OnDismissListener,
    View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()
    }

    private fun initUI() {
        //设置ViewPager
        val fragmentList = arrayListOf<Fragment>(
            HomeFragment(),
            AppointmentFragment(),
            MessageFragment(),
            MeFragment()
        )
        val mViewPagerAdapter = CommonFragmentPagerAdapter(fragmentList, supportFragmentManager)
        vpContent.adapter = mViewPagerAdapter
        vpContent.overScrollMode = View.OVER_SCROLL_NEVER
        vpContent.offscreenPageLimit = fragmentList.size
        bottomBarLayout.setViewPager(vpContent)
        bottomBarLayout.setOnOtherViewClickListener(this)

        bottomBarLayout.setOnItemClickedListener { v, _, currentPosition ->
            //首页无访问受限
            if (currentPosition != 0) {
                val isLogin = checkUserLogin()
                if (isLogin.not()) {
                    LoginActivity.startActivityForResult(this@MainActivity, currentPosition)
                    return@setOnItemClickedListener false
                }
                if (currentPosition == 3) {
                    VManagerActivity.start(v.context)
                    return@setOnItemClickedListener false
                }
            }
            true
        }
    }

    override fun finishWithAnim() = false

    private var exitTime: Long = 0 // 退出时间
    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) { // 判断间隔时间 大于2秒就退出应用
            if (System.currentTimeMillis() - exitTime > 2000) {
                "再按一次退出".toast()
                exitTime = System.currentTimeMillis()
            } else {
                onBackPressed()
            }
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

    private val mMiddlePopWindow: SmartPopupWindow by lazy {
        val popView = layoutInflater.inflate(R.layout.pop_home_middle, FrameLayout(this))
        val popupWindow = SmartPopupWindow.Builder.build(this, popView)
            .setSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            .setAnimationStyle(R.style.pop_home_bottom).createPopupWindow()
        popupWindow.isOutsideTouchable = true
        popupWindow.setOnDismissListener(this)

        popView.findViewById<View>(R.id.vPopVideo).setOnClickListener(this)
        popView.findViewById<View>(R.id.vPopPingTuan).setOnClickListener(this)
        popView.findViewById<View>(R.id.vPopDaiGou).setOnClickListener(this)
        popView.findViewById<View>(R.id.vPopDaiYou).setOnClickListener(this)
        popView.findViewById<View>(R.id.vPopFindGoods).setOnClickListener(this)
        popupWindow
    }

    override fun onOtherViewClick(poi: Int, view: View) {
        toggleMiddleMode(view)
    }

    private var mBottomBarMiddleView: View? = null  //底部bar中间的view，如果新增一个另类的，需要重构

    override fun onDismiss() {
        if (mBottomBarMiddleView != null) startBottomMiddleAnim(mBottomBarMiddleView!!)
    }

    private fun toggleMiddleMode(view: View) {
        startBottomMiddleAnim(view)
        if (mMiddlePopWindow.isShowing) {
            mMiddlePopWindow.dismiss()
        } else {
            mMiddlePopWindow.showAtAnchorView(bottomBarLayout, ABOVE, CENTER)
        }
    }

    private fun startBottomMiddleAnim(view: View) {
        mBottomBarMiddleView = view
        val rotation = ObjectAnimator.ofFloat(view, "rotation", view.rotation + 45f)
        rotation.duration = 200
        rotation.interpolator = AccelerateDecelerateInterpolator()
        rotation.start()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        when (requestCode) {
            1 -> bottomBarLayout.currentItem = 1
            2 -> bottomBarLayout.currentItem = 2
            3 -> VManagerActivity.start(this)
            else -> {
            }
        }
    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.vPopDaiYou -> {

                RxPermissions(this).request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                    Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE
                )
                    .subscribe {
                        if (it) {
                            //测试拉流
                            val et = EditText(this)
                            et.hint = "请输入流名称"
                            AlertDialog.Builder(this).setTitle("流设置").setView(et)
                                .setPositiveButton("确定") { dialog, _ ->
                                    dialog.dismiss()

                                    val streamName = et.text.toString()
                                    if (StringUtils.isEmpty(streamName)) return@setPositiveButton
                                    HttpHelper.create(LivePlayerApi::class.java)
                                        .getPullFlowText(streamName)
                                        .compose(RxSchedulers.io_main())
                                        .subscribe(object :
                                            BaseObserver<TestStreamPusher>(v.context) {
                                            override fun onHandleSuccess(
                                                t: TestStreamPusher?,
                                                message: String
                                            ) {
                                                if (t == null || StringUtils.isEmpty(t.playUrlRtmp)) "服务器错误".toast()
                                                LivePlayerActivity.start(v.context, t!!.playUrlRtmp)
                                            }
                                        })
                                }.setNegativeButton("取消", null).show()
                        } else {
                            DialogCancelConfirm(this).setMessage("未授权权限，部分功能不能使用，请前往设置打开权限")
                                .setButtonText("取消", "前往")
                                .setOperationListener(OnOperationClickListener { dialog, isLeft ->
                                    if (isLeft) {
                                        dialog.dismiss()
                                    } else {
                                        val intent =
                                            Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                        intent.data =
                                            Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                                        startActivity(intent)
                                    }
                                }).show()
                        }
                    }
            }
            R.id.vPopPingTuan -> {
                //测试推流
                val room = LivePusherRoom()
                room.id = 323232938
                room.openTime = DateUtils.str2long("2020-06-04 13:59:00", "yyyy-MM-dd HH:mm:ss")
                PreparePusherActivity.start(v.context, room)
            }
            R.id.vPopDaiGou -> CreateDaiGouOddActivity.start(v.context)
            R.id.vPopVideo -> startActivity(
                Intent(
                    this@MainActivity,
                    LivePlayerActivity::class.java
                )
            )
            R.id.vPopFindGoods -> v.context.startActivity(
                Intent(
                    v.context,
                    TestActivity::class.java
                )
            )
            else -> {
            }
        }
        mMiddlePopWindow?.dismiss()
    }
}
