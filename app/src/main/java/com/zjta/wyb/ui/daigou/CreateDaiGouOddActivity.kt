package com.zjta.wyb.ui.daigou

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.zjta.wyb.R
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.utils.glide.GlideEngine
import kotlinx.android.synthetic.main.activity_create_dai_gou_odd.*
import java.io.File

class CreateDaiGouOddActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_dai_gou_odd)

        initUI()
    }

    private fun initUI() {

        ivSelectImg.click { showPicSelectorActivity() }
    }

    /**
     * 跳转图片选择界面
     */
    @SuppressLint("SourceLockedOrientationActivity")
    private fun showPicSelectorActivity() {
        PictureSelector.create(this).openGallery(PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
            //.theme()//主题样式(不设置为默认样式) 也可参考demo values/styles下 例如：R.style.picture.white.style
            .maxSelectNum(1) // 最大图片选择数量 int
            .loadImageEngine(GlideEngine.createGlideEngine())
            .minSelectNum(1) // 最小选择数量 int
            .imageSpanCount(4) // 每行显示个数 int
            .selectionMode(PictureConfig.SINGLE) // 多选 or 单选 PictureConfig.MULTIPLE or PictureConfig.SINGLE
            .previewImage(false) // 是否可预览图片 true or false
            //.previewVideo()// 是否可预览视频 true or false
            //.enablePreviewAudio() // 是否可播放音频 true or false
            .isCamera(true) // 是否显示拍照按钮 true or false
            .imageFormat(PictureMimeType.PNG) // 拍照保存图片格式后缀,默认jpeg
            .isZoomAnim(true) // 图片列表点击 缩放效果 默认true
            //.setOutputCameraPath("/CustomPath")// 自定义拍照保存路径,可不填
            .enableCrop(false) // 是否裁剪 true or false
            .compress(false) // 是否压缩 true or false
            //.withAspectRatio()// int 裁剪比例 如16:9 3:2 3:4 1:1 可自定义
            //.hideBottomControls()// 是否显示uCrop工具栏，默认不显示 true or false
            .isGif(false) // 是否显示gif图片 true or false
            //.compressSavePath(getPath())//压缩图片保存地址
            //.freeStyleCropEnabled()// 裁剪框是否可拖拽 true or false
            //.circleDimmedLayer()// 是否圆形裁剪 true or false
            //.showCropFrame()// 是否显示裁剪矩形边框 圆形裁剪时建议设为false   true or false
            //.showCropGrid()// 是否显示裁剪矩形网格 圆形裁剪时建议设为false    true or false
            //.openClickSound()// 是否开启点击声音 true or false
            //.selectionMedia()// 是否传入已选图片 List<LocalMedia> list
            //.previewEggs()// 预览图片时 是否增强左右滑动图片体验(图片滑动一半即可看到上一张是否选中) true or false
            //.cropCompressQuality()// 裁剪压缩质量 默认90 int
            //.minimumCompressSize(100)// 小于100kb的图片不压缩
            //.synOrAsy(true)//同步true或异步false 压缩 默认同步
            //.cropWH()// 裁剪宽高比，设置如果大于图片本身宽高则无效 int
            //.rotateEnabled() // 裁剪是否可旋转图片 true or false
            //.scaleEnabled()// 裁剪是否可放大缩小图片 true or false
            //.videoQuality()// 视频录制质量 0 or 1 int
            //.videoMaxSecond(15)// 显示多少秒以内的视频or音频也可适用 int
            //.videoMinSecond(10)// 显示多少秒以内的视频or音频也可适用 int
            //.recordVideoSecond()//视频秒数录制 默认60s int
            //.isDragFrame(false)// 是否可拖动裁剪框(固定)
            .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            .forResult(PictureConfig.CHOOSE_REQUEST) //结果回调onActivityResult code

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    // 例如 LocalMedia 里面返回三种path
                    // 1.media.getPath(); 为原图path
                    // 2.media.getCutPath();为裁剪后path，需判断media.isCut();是否为true
                    // 3.media.getCompressPath();为压缩后path，需判断media.isCompressed();是否为true
                    // 如果裁剪并压缩了，以取压缩路径为准，因为是先裁剪后压缩的
                    val path = selectList[0].path
                    ivSelectImg.setImageURI(Uri.fromFile(File(path)))
                }
            }
        }

    }

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CreateDaiGouOddActivity::class.java))
        }
    }
}
