package com.zjta.wyb.ui.usercenter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.zjta.wyb.R
import com.zjta.wyb.api.UserActionApi
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.base.GlobalApplication
import com.zjta.wyb.entity.UserInfo
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.kotlin.hide
import com.zjta.wyb.kotlin.show
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.utils.SPUtils
import com.zjta.wyb.utils.SystemBarModeUtils
import com.zjta.wyb.utils.glide.GlideEngine
import kotlinx.android.synthetic.main.activity_update_user_info.*
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

/**
 * 用户信息修改（v号和账号）
 */
class UpdateUserInfoActivity : BaseActivity() {


    private var mUserInfo: UserInfo? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user_info)
        SystemBarModeUtils.darkMode(this, true)

        initUI()
    }

    private fun initUI() {
        mUserInfo = intent.getParcelableExtra(EXTRA_USER)
        mUserInfo?.apply {
            etUserName.setText(this.name)
            GlideUtils.setAvatarCircle(this@UpdateUserInfoActivity, this.imgUrl, ivAvatar)
        }


        etUserName.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                mUserInfo?.apply {
                    if (s.toString() == this.name) vConfirm.hide() else vConfirm.show()
                }
            }
        })
        vConfirm.click { doUpdateUserName(etUserName.text.toString()) }
        ivAvatar.click { showPicSelector() }
        vUpdateAvatar.click { showPicSelector() }
        vCreateVno.click{ }
    }

    /**
     * 选择图片组件
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private fun showPicSelector() {
        PictureSelector.create(this)
            .openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            .maxSelectNum(1) // 最大图片选择数量 int
            .loadImageEngine(GlideEngine.createGlideEngine())
            .minSelectNum(1) // 最小选择数量 int
            .imageSpanCount(4) // 每行显示个数 int
            .selectionMode(PictureConfig.SINGLE) // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            .previewImage(false) // 是否可预览图片 true or false
            .isCamera(true) // 是否显示拍照按钮 true or false
            .imageFormat(PictureMimeType.PNG) // 拍照保存图片格式后缀,默认jpeg
            .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
            .enableCrop(true) // 是否裁剪 true or false
            .compress(true) // 是否压缩 true or false
            .withAspectRatio(1, 1) // int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            .isGif(false) // 是否显示gif图片 true or false
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .forResult(PictureConfig.CHOOSE_REQUEST) //结果回调onActivityResult code
    }

    /**
     * 选择图片回调
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.isEmpty()) return
                    val path = if (selectList[0].isCompressed) selectList[0].compressPath else selectList[0].path
                    postToServer(File(path))
                }
            }
        }
    }

    /**
     * 上传到服务器
     */
    private fun postToServer(file: File) {
        //多图片上传
        /* val builder = MultipartBody.Builder()
         //val requestFile = RequestBody.create(MediaType.parse("image/jpg"), file)
         val imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)
         LogUtils.e("---> ${file.path}")
         builder.addFormDataPart("file", file.name, imageBody)
         val parts = builder.build().parts()*/


        val requestFile = RequestBody.create(MediaType.parse("image/jpg"), file)
        HttpHelper.create(UserActionApi::class.java)
            .updateUserAvatar(requestFile)
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<Void>() {
                override fun onHandleSuccess(t: Void?, message: String) {
                    ivAvatar.setImageURI(Uri.fromFile(file))
                }
            })
    }


    /**
     * 修改昵称
     */
    private fun doUpdateUserName(userName: String) {
        //名字校验
        val containSpecial = containSpecial(userName)
        if (containSpecial) {
            "含有特殊字符，请重新输入".toast()
            return
        }

        vConfirm.isEnabled = false
        HttpHelper.create(UserActionApi::class.java)
            .updateUserName(userName)
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<Void>() {
                override fun onHandleSuccess(t: Void?, message: String) {
                    vConfirm.hide()
                    message.toast()
                    GlobalApplication.user.name = userName
                    SPUtils.setUserInfo(GlobalApplication.user)
                    etUserName.clearFocus()
                }

                override fun onHandleComplete() {
                    vConfirm.isEnabled = true
                }
            })
    }

    /**
     * 是否包含特殊字符
     * @return true 包含 false 不包含
     */
    private fun containSpecial(name: String): Boolean {
        return name.replace("[\u4e00-\u9fa5]*[a-z]*[A-Z]*\\d*-*_*\\s*", "")
            .isNotEmpty()
    }


    companion object {

        private const val EXTRA_USER = "user"

        fun start(context: Context, userInfo: UserInfo) {
            val intent = Intent(context, UpdateUserInfoActivity::class.java)
            intent.putExtra(EXTRA_USER, userInfo)
            context.startActivity(intent)
        }
    }
}
