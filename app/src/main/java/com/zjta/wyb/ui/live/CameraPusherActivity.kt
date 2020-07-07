package com.zjta.wyb.ui.live

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory.decodeResource
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import com.tencent.rtmp.ITXLivePushListener
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXLivePushConfig
import com.tencent.rtmp.TXLivePusher
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.logd
import com.zjta.wyb.kotlin.loge
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.LogUtils
import kotlinx.android.synthetic.main.activity_camera_pusher.*
import java.util.*

class CameraPusherActivity : BaseActivity(), ITXLivePushListener, TXLivePusher.OnBGMNotify {

    /**
     * SDK 提供的类
     */
    private val mLivePushConfig by lazy { TXLivePushConfig().apply { this.setVideoEncodeGop(5) } } // SDK 推流 config
    private val mLivePusher by lazy { TXLivePusher(this) } // SDK 推流类
    private var mIsPushing = false // 当前是否正在推流
    private var mCurrentVideoResolution = TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION // 推流分辨率 默认高清
    private val mPushScene = arrayListOf(Pair("蓝光", TXLiveConstants.VIDEO_QUALITY_ULTRA_DEFINITION),/*1080p*/
                                         Pair("超清", TXLiveConstants.VIDEO_QUALITY_SUPER_DEFINITION),/*720p*/
                                         Pair("高清", TXLiveConstants.VIDEO_QUALITY_HIGH_DEFINITION),/*540p*/
                                         Pair("标清", TXLiveConstants.VIDEO_QUALITY_STANDARD_DEFINITION))/*360p*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera_pusher)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        initUI()
        initPusher()        //初始化推流器
        startRTMPPush(intent.getStringExtra(EXTRA_STREAM_URL) ?: "") //开始推流

    }


    /**
     * init ui
     */
    private fun initUI() {
        titleBar.titleRightBtn.click { onBackPressed() }
        vToggleCamera.click { mLivePusher.switchCamera() }

    }


    /**
     * 初始化 SDK 推流器
     */
    private fun initPusher() {
        mLivePusher.config = mLivePushConfig
    }

    /**
     * 开始 RTMP 推流
     *
     * 推荐使用方式：
     * 1. 配置好 {@link TXLivePushConfig} ， 配置推流参数
     * 2. 调用 {@link TXLivePusher#setConfig(TXLivePushConfig)} ，设置推流参数
     * 3. 调用 {@link TXLivePusher#startCameraPreview(TXCloudVideoView)} ， 开始本地预览
     * 4. 调用 {@link TXLivePusher#startPusher(String)} ， 发起推流
     *
     * 注：步骤 3 要放到 2 之后，否则横屏推流、聚焦曝光、画面缩放功能配置不生效
     */
    private fun startRTMPPush(pushUrlRtmp: String) {
        LogUtils.e(pushUrlRtmp)
        if (TextUtils.isEmpty(pushUrlRtmp) || !pushUrlRtmp.trim { it <= ' ' }.toLowerCase(
                Locale.getDefault()).startsWith("rtmp://")
        ) {
            "推流地址不合法，目前支持rtmp推流!".toast()
            return
        }

        // 添加播放回调
        mLivePusher.setPushListener(this)
        mLivePusher.setBGMNofify(this)

        // 添加后台垫片推流参数
        // 添加后台垫片推流参数
        val bitmap: Bitmap = decodeResource(resources, R.mipmap.pause_publish)
        mLivePushConfig.setPauseImg(bitmap)
        mLivePushConfig.setPauseImg(300, 5)
        mLivePushConfig.setPauseFlag(TXLiveConstants.PAUSE_FLAG_PAUSE_VIDEO) // 设置暂停时，只停止画面采集，不停止声音采集。
        mLivePushConfig.setVideoResolution(mCurrentVideoResolution) // 设置推流分辨率
        mLivePusher.setMute(false) // 开启麦克风推流相关  是否静音
        mLivePushConfig.setTouchFocus(true) // 是否打开曝光对焦
        mLivePusher.config = mLivePushConfig      // 设置推流配置

        // 设置场景  参数一:分辨率  参数二:宽带自适应  参数三:码率自适应
        mLivePusher.setVideoQuality(mCurrentVideoResolution, true, false)
        mLivePushConfig.setHardwareAcceleration(TXLiveConstants.ENCODE_VIDEO_HARDWARE) // 是否开启硬件加速
        mLivePusher.config = mLivePushConfig

        mLivePusher.startCameraPreview(mPusherView) // 设置本地预览View

        // 发起推流
        val ret = mLivePusher.startPusher(pushUrlRtmp.trim { it <= ' ' })
        if (ret == -5) {
            "License 校验失败".toast()
            stopRTMPPush()
            return
        }
        mIsPushing = true
        loge("正在推流")
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopRTMPPush()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopRTMPPush()
    }

    /**
     * 停止 RTMP 推流
     */
    private fun stopRTMPPush() { // 清除log状态
        // 停止BGM
        mLivePusher.stopBGM()
        mLivePusher.stopCameraPreview(true)
        // 移除监听
        mLivePusher.setPushListener(null)
        // 停止推流
        mLivePusher.stopPusher()
        // 隐藏本地预览的View
        mPusherView.visibility = View.GONE
        // 移除垫片图像
        mLivePushConfig.setPauseImg(null)
        // 关闭隐私模式
        mIsPushing = false
    }

    companion object {
        private const val EXTRA_STREAM_URL = "stream_url"
        fun start(context: Context, streamUrl: String) {
            val intent = Intent(context, CameraPusherActivity::class.java)
            intent.putExtra(EXTRA_STREAM_URL, streamUrl)
            context.startActivity(intent)
        }
    }


    //--------------  监听回调 --------------

    /**
     * 添加播放回调
     */
    override fun onNetStatus(status: Bundle) {
        logd("Current status, CPU:" + status.getString(TXLiveConstants.NET_STATUS_CPU_USAGE) + ", RES:" + status.getInt(
            TXLiveConstants.NET_STATUS_VIDEO_WIDTH) + "*" + status.getInt(
            TXLiveConstants.NET_STATUS_VIDEO_HEIGHT) + ", SPD:" + status.getInt(
            TXLiveConstants.NET_STATUS_NET_SPEED) + "Kbps" + ", FPS:" + status.getInt(
            TXLiveConstants.NET_STATUS_VIDEO_FPS) + ", ARA:" + status.getInt(
            TXLiveConstants.NET_STATUS_AUDIO_BITRATE) + "Kbps" + ", VRA:" + status.getInt(
            TXLiveConstants.NET_STATUS_VIDEO_BITRATE) + "Kbps")
    }

    /**
     * 推流器状态回调
     */
    override fun onPushEvent(event: Int, param: Bundle) {

    }

    /**
     * mLivePusher.setBGMNofify(this)
     */
    override fun onBGMProgress(p0: Long, p1: Long) {
    }

    /**
     *  mLivePusher.setBGMNofify(this)
     */
    override fun onBGMStart() {
    }

    /**
     *  mLivePusher.setBGMNofify(this)
     */
    override fun onBGMComplete(p0: Int) {
    }

}
