package com.zjta.wyb.widget

import android.app.AlertDialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.core.content.ContextCompat.startActivity
import com.zjta.wyb.R
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.PlatformUtils


/**
 * 分享的弹窗
 */
class ShareWindowDialog @JvmOverloads constructor(
    context: Context, private val shareString: String
) : AlertDialog(context, R.style.AppDialog), View.OnClickListener {

    init {
        setCancelable(false)
        val view = layoutInflater.inflate(R.layout.pop_share_window, FrameLayout(this.context), true)
        setView(view)
        view.findViewById<View>(R.id.tvShareLink)
            .setOnClickListener(this)
        view.findViewById<View>(R.id.tvShareCancel)
            .setOnClickListener(this)
        view.findViewById<View>(R.id.tvShareWechat)
            .setOnClickListener(this)
        view.findViewById<View>(R.id.tvShareQQ)
            .setOnClickListener(this)

        window?.apply {
            setGravity(Gravity.BOTTOM)

            decorView.setPadding(0, 0, 0, 0)
            val attr = attributes
            attr.width = WindowManager.LayoutParams.MATCH_PARENT
            attr.height = WindowManager.LayoutParams.WRAP_CONTENT
            attributes = attr
        }

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tvShareLink -> shareString.toast()
            R.id.tvShareWechat -> shareWechatFriend(shareString)
            R.id.tvShareQQ -> shareToQQFriend(shareString)
        }
        dismiss()
    }

    //分享文本 到QQ好友（微信，朋友圈同理,这里分享文本不涉及访问文件就不用判断安卓是否大于7.0了）
    private fun shareToQQFriend(text: String) {
        if (PlatformUtils.isInstallApp(context, PlatformUtils.PACKAGE_MOBILE_QQ)) {
            val intent = Intent()
            val componentName = ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity")
            intent.component = componentName
            intent.action = Intent.ACTION_SEND
            intent.type = "text/*"
            intent.putExtra(Intent.EXTRA_TEXT, text)
            startActivity(context, intent, null)
        } else {
            "您需要安装QQ客户端".toast()
        }
    }

    /**
     * 直接分享文本到微信好友
     */
    private fun shareWechatFriend(content: String) {
        if (PlatformUtils.isInstallApp(context, PlatformUtils.PACKAGE_WECHAT)) {
            val intent = Intent()
            val cop = ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI")
            intent.component = cop
            intent.action = Intent.ACTION_SEND
            intent.putExtra("android.intent.extra.TEXT", content)
            intent.putExtra("Kdescription", if (!TextUtils.isEmpty(content)) content else "")
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        } else {
            "您需要安装微信客户端".toast()
        }
    }
}