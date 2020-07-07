package com.zjta.wyb.widget.banner.holder

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.zjta.wyb.R
import com.zjta.wyb.entity.UserInfo


/**
 * 主界面的广告轮播图
 */
class MainBannerHolder : BannerViewHolder<UserInfo.WybVnoBean> {

    private lateinit var mImageView: ImageView
    private lateinit var mOptions: RequestOptions
    private lateinit var mTransitionOptions: DrawableTransitionOptions

    override fun createView(context: Context): View {
        mImageView = ImageView(context).apply {
            layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            scaleType = ImageView.ScaleType.CENTER_CROP
        }
        mOptions = RequestOptions()
            .error(R.drawable.picture_icon_def).diskCacheStrategy(DiskCacheStrategy.DATA)
        mTransitionOptions = DrawableTransitionOptions().crossFade(500)

        return mImageView
    }

    override fun onBind(context: Context, position: Int, data: UserInfo.WybVnoBean) {
        Glide.with(context).load(data.imgUrl).transition(mTransitionOptions)
            .apply(mOptions).into(mImageView)
    }

}
