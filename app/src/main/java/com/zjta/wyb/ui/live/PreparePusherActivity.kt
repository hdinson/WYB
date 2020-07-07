package com.zjta.wyb.ui.live

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.appcompat.app.AlertDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zjta.wyb.BuildConfig
import com.zjta.wyb.R
import com.zjta.wyb.api.LivePlayerApi
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.LivePusherRoom
import com.zjta.wyb.entity.TestStreamPusher
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.*
import com.zjta.wyb.utils.DateUtils
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.utils.RxHelper
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.widget.dialog.DialogCancelConfirm
import com.zjta.wyb.widget.dialog.OnOperationClickListener
import com.zjta.wyb.widget.smartpopupwindow.SmartPopupWindow
import com.zjta.wyb.widget.smartpopupwindow.VerticalPosition.ABOVE
import com.zjta.wyb.widget.smartpopupwindow.VerticalPosition.CENTER
import kotlinx.android.synthetic.main.activity_prepare_pusher.*
import kotlin.math.abs


class PreparePusherActivity : BaseActivity(), View.OnClickListener, PopupWindow.OnDismissListener {

    private var mLiveRoomId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prepare_pusher)

        val liveRoom = intent.getParcelableExtra<LivePusherRoom>(EXTRA_LIVE_ROOM)
        if (liveRoom == null) {
            "直播间数据异常".toast()
            onBackPressed()
            return
        }
        mLiveRoomId = liveRoom.id
        initUI(liveRoom)

    }

    private var overTimeModeShowed = false
    private fun initUI(livePusherRoom: LivePusherRoom) {
        //处理界面倒计时
        val dTimeSeconds = (livePusherRoom.openTime - DateUtils.getCurrentTimeMillis13()) / 1000
        LogUtils.e("time: $dTimeSeconds")

        RxHelper.timer(dTimeSeconds.toInt())
            .compose(bindToLifecycle())
            .subscribe {
                if (it < 0) {
                    if (overTimeModeShowed.not()) {
                        //超时了
                        tvTimeDesc.text = "已超时"
                        val textColor = Color.parseColor("#FC5051")
                        tvTimeHour.setTextColor(textColor)
                        tvTimeMinutes.setTextColor(textColor)
                        tvTimeSeconds.setTextColor(textColor)
                        overTimeModeShowed = true
                    }
                }
                val calcTimeInFormat = DateUtils.calcTimeInFormat(abs(it))
                val split = calcTimeInFormat.split(":")
                if (split.size == 3) {
                    tvTimeHour.text = split[0]
                    tvTimeMinutes.text = split[1]
                    tvTimeSeconds.text = split[2]
                    LogUtils.v("format : ${split[0]}:${split[1]}:${split[2]}")
                }
            }
            .addToManaged()

        vStartPush.click {
            //权限检查
            RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
                                        Manifest.permission.RECORD_AUDIO, Manifest.permission.READ_PHONE_STATE)
                .subscribe {
                    if (it) {
                        startPush()
                    } else {
                        DialogCancelConfirm(this).setMessage("未授权权限，部分功能不能使用，请前往设置打开权限")
                            .setButtonText("取消", "前往")
                            .setOperationListener(OnOperationClickListener { dialog, isLeft ->
                                if (isLeft) {
                                    dialog.dismiss()
                                } else {
                                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                                    intent.data = Uri.parse("package:${BuildConfig.APPLICATION_ID}")
                                    startActivity(intent)
                                }
                            })
                            .show()
                    }
                }
        }

        vShowWinShop.setOnClickListener(this)
    }

    private fun startPush() {
        val et = EditText(this)
        et.hint = "请输入流名称"
        AlertDialog.Builder(this)
            .setTitle("流设置")
            .setView(et)
            .setPositiveButton("确定") { dialog, _ ->
                dialog.dismiss()

                val streamName = et.text.toString()
                if (StringUtils.isEmpty(streamName)) return@setPositiveButton
                HttpHelper.create(LivePlayerApi::class.java)
                    .getPullFlowText(streamName)
                    .compose(RxSchedulers.io_main())
                    .subscribe(object : BaseObserver<TestStreamPusher>(this@PreparePusherActivity) {
                        override fun onHandleSuccess(t: TestStreamPusher?, message: String) {
                            if (t == null || StringUtils.isEmpty(t.pushUrlRtmp)) "服务器错误".toast()
                            CameraPusherActivity.start(this@PreparePusherActivity, t!!.pushUrlRtmp)
                        }
                    })
            }
            .setNegativeButton("取消", null)
            .show()
    }

    companion object {
        private const val EXTRA_LIVE_ROOM = "extra_live_room"
        fun start(context: Context, livePusherRoom: LivePusherRoom) {
            val intent = Intent(context, PreparePusherActivity::class.java)
            intent.putExtra(EXTRA_LIVE_ROOM, livePusherRoom)
            context.startActivity(intent)
        }
    }

    private val mPopWindow: SmartPopupWindow by lazy {
        val view = layoutInflater.inflate(R.layout.pop_live_room_shop_manager, FrameLayout(this))
        view.findViewById<View>(R.id.ivPusherEdit)
            .setOnClickListener(this)
        view.findViewById<View>(R.id.ivPusherAdd)
            .setOnClickListener(this)
        view.findViewById<View>(R.id.ivPusherImp)
            .setOnClickListener(this)
        val popupWindow = SmartPopupWindow.Builder.build(this, view)
            .setAlpha(0.4f)                   //背景灰度     默认全透明
            .createPopupWindow()
        popupWindow.setOnDismissListener(this@PreparePusherActivity)
        popupWindow
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.vShowWinShop -> {
                vShowWinShop.isEnabled = false
                if (mPopWindow.isShowing) mPopWindow.dismiss()
                else mPopWindow.showAtAnchorView(vShowWinShop, ABOVE, CENTER)
            }
            R.id.ivPusherAdd -> {
                if (mPopWindow.isShowing) mPopWindow.dismiss()
                PushingWinShopManagerActivity.start(v.context, 0, mLiveRoomId)
            }
            R.id.ivPusherImp -> {
                if (mPopWindow.isShowing) mPopWindow.dismiss()
                PushingWinShopManagerActivity.start(v.context, 1, mLiveRoomId)
            }
            R.id.ivPusherEdit -> {
                if (mPopWindow.isShowing) mPopWindow.dismiss()
                PushingWinShopManagerActivity.start(v.context, 2, mLiveRoomId)
            }
        }

    }

    override fun onDismiss() {
        vShowWinShop.isEnabled = true
    }
}
