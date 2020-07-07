package com.zjta.wyb.ui.live

import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.PopupWindow
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.core.content.ContextCompat
import com.anbetter.danmuku.model.DanMuModel
import com.anbetter.danmuku.model.utils.DimensionUtil
import com.tencent.rtmp.ITXLivePlayListener
import com.tencent.rtmp.TXLiveConstants
import com.tencent.rtmp.TXLivePlayConfig
import com.tencent.rtmp.TXLivePlayer
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.logd
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.widget.ShareWindowDialog
import io.reactivex.Observable
import kotlinx.android.synthetic.main.activity_live_player.*
import java.util.concurrent.TimeUnit


class LivePlayerActivity : BaseActivity(), ITXLivePlayListener {

    //private val mDanmakuManager = DanmakuManager.getInstance()
    private val mPlayConfig by lazy { TXLivePlayConfig() }
    private val mLivePlayer by lazy { TXLivePlayer(this) }
    private var mIsPlaying = false
    private var mPlayType = TXLivePlayer.PLAY_TYPE_LIVE_RTMP // player 播放链接类型
    private val mHWDecode = false // 是否使用硬解码
    private val mCurrentRenderMode = TXLiveConstants.RENDER_MODE_ADJUST_RESOLUTION // player 渲染模式 平铺或者全屏
    private val mCurrentRenderRotation = TXLiveConstants.RENDER_ROTATION_PORTRAIT // player 渲染角度 横竖屏


    private val CACHE_TIME_FAST = 1.0f
    private val CACHE_TIME_SMOOTH = 5.0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_player)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        initUI()
        initPlayer()
        startPlay(intent.getStringExtra(EXTRA_STREAM_URL) ?: "")
    }

    /**
     * 初始化拉流器
     */
    private fun initPlayer() {

        //设置缓存策略为自动
        mPlayConfig.setAutoAdjustCacheTime(true)
        mPlayConfig.setMaxAutoAdjustCacheTime(CACHE_TIME_SMOOTH)
        mPlayConfig.setMinAutoAdjustCacheTime(CACHE_TIME_FAST)
        mLivePlayer.setConfig(mPlayConfig)


    }

    private fun initUI() {
        titleBar.titleLeftBtn.click { onBackPressed() }
        //启动弹幕引擎
        danmakuContainerBroadcast.prepare()
        //mDanmakuManager.init(this,flDanmaku)
        tvDanmaku.click { showEditTextPop(it) }
        actionShare.click { showSharePopWindow() }
        jumpToWindowShop.click { LivingWindowShopActivity.start(it.context) }
    }

    /**
     * 开始播放
     *
     * @return
     */
    private fun startPlay(playUrl: String): Boolean {

        checkPlayUrl(playUrl)  //检查播放地址

        mLivePlayer.setPlayerView(mPlayerView) //设置播放视图
        mLivePlayer.setPlayListener(this) //监听回调
        // 硬件加速在1080p解码场景下效果显著，但细节之处并不如想象的那么美好：
        // (1) 只有 4.3 以上android系统才支持
        // (2) 兼容性我们目前还仅过了小米华为等常见机型，故这里的返回值您先不要太当真
        mLivePlayer.enableHardwareDecode(mHWDecode)
        mLivePlayer.setRenderRotation(mCurrentRenderRotation)
        mLivePlayer.setRenderMode(mCurrentRenderMode)
        //设置播放器缓存策略
        //这里将播放器的策略设置为自动调整，调整的范围设定为1到4s，您也可以通过setCacheTime将播放器策略设置为采用
        //固定缓存时间。如果您什么都不调用，播放器将采用默认的策略（默认策略为自动调整，调整范围为1到4s）
        //mLivePlayer.setCacheTime(5);
        // HashMap<String, String> headers = new HashMap<>();
        // headers.put("Referer", "qcloud.com");
        // mPlayConfig.setHeaders(headers);
        mPlayConfig.setEnableMessage(true)
        mLivePlayer.setConfig(mPlayConfig)
        // result返回值：0 success;  -1 empty url; -2 invalid url; -3 invalid playType;
        val result = mLivePlayer.startPlay(playUrl, mPlayType)
        if (result != 0) {
            LogUtils.e("播放错误: $result")
            return false
        }
        logd("开始播放")
        return true
    }

    private fun stopPlay() {
        mLivePlayer.stopRecord()
        mLivePlayer.setPlayListener(null)
        mLivePlayer.stopPlay(true)
        mIsPlaying = false
    }

    override fun onBackPressed() {
        super.onBackPressed()
        stopPlay()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopPlay()
    }

    /**
     * 显示
     */
    private fun showSharePopWindow() {
        ShareWindowDialog(this, "这是分享的内容").show()
    }


    private var mPopWindow: PopupWindow? = null

    /**
     * 显示popWindow
     */
    private fun showEditTextPop(view: View) {
        //设置contentView
        if (mPopWindow == null) {
            val contentView = LayoutInflater.from(this)
                .inflate(R.layout.pop_live_bottom_editext, FrameLayout(view.context))
            mPopWindow =
                PopupWindow(contentView, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT, true)
            mPopWindow!!.isOutsideTouchable = false
            // 设置这两个属性
            mPopWindow!!.isFocusable = false
            mPopWindow!!.contentView = contentView
            //防止PopupWindow被软件盘挡住（可能只要下面一句，可能需要这两句）
            //        mPopWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
            mPopWindow!!.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE

        }

        //设置各个控件的点击响应
        val sendDanmaku = mPopWindow!!.contentView.findViewById<TextView>(R.id.tvDoSend)
        sendDanmaku.click {
            val editText = mPopWindow!!.contentView.findViewById<EditText>(R.id.etDanmakuText)
            sendDanmaku(editText.text.toString())
            editText.setText("")
        }
        //是否具有获取焦点的能力
        mPopWindow!!.isFocusable = true
        mPopWindow!!.softInputMode = WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE;
        mPopWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)
        val editText = mPopWindow!!.contentView.findViewById<EditText>(R.id.etDanmakuText)
        editText.requestFocus()
        popupInputMethodWindow()
    }


    /**
     * 异步弹出软键盘
     */
    private fun popupInputMethodWindow() {
        Observable.just("")
            .delay(1, TimeUnit.MICROSECONDS)
            .compose(RxSchedulers.io_main())
            .subscribe {
                val imm = this@LivePlayerActivity.getSystemService(Service.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
            }
            .addToManaged()
    }

    /**
     * 发送弹幕
     */
    private fun sendDanmaku(text: String) {
        if (StringUtils.isEmpty(text)) return
        val danMuView = DanMuModel()
        danMuView.displayType = DanMuModel.RIGHT_TO_LEFT
        danMuView.priority = DanMuModel.NORMAL
        danMuView.marginLeft = DimensionUtil.dpToPx(this, 16)

        // 显示的文本内容
        danMuView.textSize = DimensionUtil.spToPx(this, 16)
            .toFloat()
        danMuView.textColor = ContextCompat.getColor(this, R.color.white)
        val paddingStart = DimensionUtil.dpToPx(this, 13)
        val paddingBottom = DimensionUtil.dpToPx(this, 4)
        danMuView.textBackgroundPaddingTop = paddingBottom
        danMuView.textBackgroundPaddingBottom = paddingBottom
        danMuView.textBackgroundMarginLeft = paddingStart
        danMuView.textBackgroundPaddingRight = paddingStart


        /* if (entity.getRichText() != null) {
             danMuView.text = RichTextParse.parse(mContext, entity.getRichText(), DimensionUtil.spToPx(mContext, 18), false)
         } else {
             danMuView.text = entity.getText()
         }*/

        danMuView.text = text
        // 弹幕文本背景
        danMuView.textBackground = ContextCompat.getDrawable(this, R.drawable.live_danmaku_bg)
        danmakuContainerBroadcast.add(danMuView)
    }

    /**
     * 检验播放地址
     */
    private fun checkPlayUrl(playUrl: String) {
        if (TextUtils.isEmpty(playUrl) || !playUrl.startsWith("http://") && !playUrl.startsWith(
                "https://") && !playUrl.startsWith("rtmp://") && !playUrl.startsWith("/")
        ) {
            "播放地址不合法，直播目前仅支持rtmp,flv播放方式!".toast()
            onBackPressed()
            return
        }

        mPlayType = if (playUrl.startsWith("rtmp://")) {
            TXLivePlayer.PLAY_TYPE_LIVE_RTMP
        } else if ((playUrl.startsWith("http://") || playUrl.startsWith("https://")) && playUrl.contains(".flv")) {
            TXLivePlayer.PLAY_TYPE_LIVE_FLV
        } else {
            "播放地址不合法，直播目前仅支持rtmp,flv播放方式!".toast()
            onBackPressed()
            return
        }
    }

    companion object {
        private const val EXTRA_STREAM_URL = "stream_url"
        fun start(context: Context, streamUrl: String) {
            val intent = Intent(context, LivePlayerActivity::class.java)
            intent.putExtra(EXTRA_STREAM_URL, streamUrl)
            context.startActivity(intent)
        }
    }

    override fun onPlayEvent(p0: Int, p1: Bundle?) {


    }

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

}
