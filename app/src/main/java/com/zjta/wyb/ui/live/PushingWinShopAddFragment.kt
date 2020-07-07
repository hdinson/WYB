package com.zjta.wyb.ui.live

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.luck.picture.lib.PictureSelector
import com.luck.picture.lib.config.PictureConfig
import com.luck.picture.lib.config.PictureMimeType
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.zjta.wyb.R
import com.zjta.wyb.api.LivePlayerApi
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.entity.WindowShopGoods
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.*
import com.zjta.wyb.utils.SpanUtils
import com.zjta.wyb.utils.StringUtils
import com.zjta.wyb.utils.glide.GlideEngine
import kotlinx.android.synthetic.main.framgnt_pushing_win_shop_add.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * 直播间-添加商品橱窗
 */
class PushingWinShopAddFragment : ViewPagerLazyFragment(), View.OnClickListener {

    fun newInstance(liveRoomId: Int): PushingWinShopAddFragment {
        val args = Bundle()
        val fragment = PushingWinShopAddFragment()
        args.putInt("EXTRA_ID", liveRoomId)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_pushing_win_shop_add, container, false)
    }


    override fun lazyInit() {
        formatGoodsDesc(tvDescPicture, "商品图片")
        formatGoodsDesc(tvDescName, "商品名称")
        formatGoodsDesc(tvDescBrand, "品牌")
        formatGoodsDesc(tvDescSpec, "规格")
        formatGoodsDesc(tvDescPrice, "价格")

        vToSave.setOnClickListener(this)
        ivSelectImg.setOnClickListener(this)

        //必填项
        etGoodsName.addEmptySelectTrue()
        etGoodsBrand.addEmptySelectTrue()
        etGoodsSpec.addEmptySelectTrue()
        etGoodsPrice.addEmptySelectTrue()
            .onlyMoney()

        etGoodsIntroduce.forbiddenEnter()
    }


    private fun verifyGoodsInput(): WindowShopGoods? {
        if (StringUtils.isEmpty(mGoodsPath)) {
            "请选择一张图片".toast()
            return null
        }
        if (StringUtils.isEmpty(etGoodsName.text.toString())) {
            "请输入请输入商品名称".toast()
            etGoodsName.isSelected = true
            return null
        }
        if (StringUtils.isEmpty(etGoodsBrand.text.toString())) {
            "请输入商品所属品牌".toast()
            etGoodsBrand.isSelected = true
            return null
        }
        if (StringUtils.isEmpty(etGoodsSpec.text.toString())) {
            "请输入商品的规格".toast()
            etGoodsSpec.isSelected = true
            return null
        }
        if (StringUtils.isEmpty(etGoodsPrice.text.toString())) {
            "请输入商品价格".toast()
            etGoodsPrice.isSelected = true
            return null
        }
        return WindowShopGoods().apply {
            name = etGoodsName.text.toString()
            brand = etGoodsBrand.text.toString()
            packaging = etGoodsPackaging.text.toString()
            specification = etGoodsSpec.text.toString()
            manufacturer = etGoodsFactory.text.toString()
            netWeight = etGoodsNetWeigh.text.toString()
            price = etGoodsPrice.text.toString()
                .toDouble()
            introduce = etGoodsIntroduce.text.toString()
            liveInfo = arguments?.getInt("EXTRA_ID") ?: 0
        }
    }

    /**
     * 带*号表示必填
     */
    private fun formatGoodsDesc(tv: TextView, desc: String) {
        SpanUtils.with(tv)
            .append("*")
            .setForegroundColor(Color.parseColor("#FF993F"))
            .append(desc)
            .create()
    }

    @SuppressLint("SourceLockedOrientationActivity")
    override fun onClick(v: View) {

        when (v.id) {
            R.id.ivSelectImg -> PictureSelector.create(this).openGallery(
                PictureMimeType.ofImage()) //全部.PictureMimeType.ofAll()、图片.ofImage()、视频.ofVideo()、音频.ofAudio()
                .maxSelectNum(1) // 最大图片选择数量 int
                .loadImageEngine(GlideEngine.createGlideEngine()).minSelectNum(1) // 最小选择数量 int
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
                .setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT).forResult(
                    PictureConfig.CHOOSE_REQUEST) //结果回调onActivityResult code
            R.id.vToSave -> {
                val goods = verifyGoodsInput()
                if (goods != null) {
                    post2Server(v.context, goods)
                }
            }
        }
    }

    private fun post2Server(context: Context, goods: WindowShopGoods) {
        val requestFile = RequestBody.create(MediaType.parse("image/jpg"), File(mGoodsPath))
        val formData = HashMap<String, String>()
        formData["liveInfo"] = goods.liveInfo.toString()
        formData["name"] = goods.name
        formData["brand"] = goods.brand
        formData["specification"] = goods.specification
        formData["manufacturer"] = goods.manufacturer
        formData["netWeight"] = goods.netWeight
        formData["introduce"] = goods.introduce
        formData["packaging"] = goods.packaging
        formData["price"] = goods.price.toString()
        formData["activityPrice"] = goods.activityPrice.toString()
        val body = generateRequestBody(formData)
        body["file\";filename=\"1.jpg"] = requestFile

        loge(body.toString())

        HttpHelper.create(LivePlayerApi::class.java)
//            .liveInfoGoodsAdd3(requestFile, goods.liveInfo, goods.name, goods.brand, goods.specification,
//                               goods.manufacturer, goods.netWeight, goods.introduce, goods.packaging, goods.price,
//                               goods.activityPrice)
            .liveInfoGoodsAdd(body)
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<Void>(context) {
                override fun onHandleSuccess(t: Void?, message: String) {
                    message.toast()
                    resetGoodsInput()
                }
            })
    }

    private fun generateRequestBody(requestDataMap: Map<String, String>): HashMap<String, RequestBody> {
        val requestBodyMap = HashMap<String, RequestBody>()
        for (key in requestDataMap.keys) {
            val requestBody = RequestBody.create(MediaType.parse(""), requestDataMap[key] ?: "")
            requestBodyMap[key] = requestBody
        }
        return requestBodyMap
    }


    /**
     * 重置输入状态
     */
    private fun resetGoodsInput() {
        mGoodsPath = ""
        etGoodsName.setText("")
        etGoodsBrand.setText("")
        etGoodsSpec.setText("")
    }


    /**
     * 选择图片回调
     */

    private var mGoodsPath = ""

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RxAppCompatActivity.RESULT_OK) {
            when (requestCode) {
                PictureConfig.CHOOSE_REQUEST -> {
                    val selectList = PictureSelector.obtainMultipleResult(data)
                    if (selectList.isEmpty()) return
                    mGoodsPath = if (selectList[0].isCompressed) selectList[0].compressPath else selectList[0].path
                    ivSelectImg.setImageURI(Uri.fromFile(File(mGoodsPath)))
                }
            }
        }
    }


}