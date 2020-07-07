package com.zjta.wyb.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.PictureDrawable
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.zjta.wyb.R
import com.zjta.wyb.utils.glide.GlideCircleTransForm
import com.zjta.wyb.utils.glide.GlideCircleWithBorder

/**
 * glide工具类
 */
object GlideUtils {

    private val avatarBorderOptions by lazy {
        RequestOptions().error(R.mipmap.uc_avatar_default)
            .placeholder(R.mipmap.uc_avatar_default)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .transform(GlideCircleWithBorder())
    }

    fun setAvatarImageWithBorder(context: Context, url: String, into: ImageView) {
        val transforms = Glide.with(context)
            .load(R.mipmap.uc_avatar_default)
            .circleCrop()
            .transform(GlideCircleWithBorder())
        Glide.with(context)
            .load(url)
            .thumbnail(transforms)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .transform(GlideCircleWithBorder())
            .into(into)
    }

    private val avatarOptions by lazy {
        RequestOptions().error(R.mipmap.uc_avatar_default)
            .placeholder(R.mipmap.uc_avatar_default)
            .centerCrop()
            .transform(GlideCircleTransForm())
            .diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    fun setAvatarCircle(context: Context, url: String, into: ImageView) {
        val transforms = Glide.with(context)
            .load(R.mipmap.uc_avatar_default)
            .circleCrop()
        Glide.with(context)
            .load(url)
            .thumbnail(transforms)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .into(into)
    }

    /**
     * 加载图片
     */
    fun loadImage(context: Context, url: String, def: Int = R.mipmap.pic_load_square_default, into: ImageView) {
        val transforms = Glide.with(context)
            .load(def)
        Glide.with(context)
            .load(url)
            .thumbnail(transforms)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(into)
    }


    //                .placeholder(R.drawable.def_img)
    //                .error(R.drawable.def_img)
    //---------------------------------------------------
    val defaultOptions: RequestOptions
        get() = RequestOptions() //                .placeholder(R.drawable.def_img)
            //                .error(R.drawable.def_img)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC).centerCrop().dontAnimate()

    fun setCircleImage(
        context: Context?, url: String?, view: ImageView?
    ) {
        val options = RequestOptions() //                .placeholder(R.drawable.def_img_round_holder)
            //                .error(R.drawable.def_img_round_error)
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .circleCrop()
            .dontAnimate()
        Glide.with(context!!)
            .load(url)
            .apply(options)
            .into(view!!)
    }

    fun setImage(context: Context, url: String, view: ImageView) {
        if (url.endsWith(".svg") || url.endsWith(".SVG")) {
            setSvgImage(context, url, view)
            return
        }
        Glide.with(context)
            .load(url)
            .apply(defaultOptions)
            .into(view)
    }

    private fun setSvgImage(
        context: Context, url: String, view: ImageView
    ) {
        Glide.with(context)
            .`as`(PictureDrawable::class.java) //                .error(R.drawable.def_img)
            .load(url)
            .into(view)
    } /*Glide储存本地文件*/ /*Single.just(mHeadData[position].data.hp_img_url)
                .map { s ->
                    Glide.with(this@MainActivity).downloadOnly().load(s).downloadOnly(Target.SIZE_ORIGINAL,
                        Target.SIZE_ORIGINAL).get().path
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { path ->
                    debug("CurrentItem:" + position + " img:" + mHeadData[position].data.hp_img_url + "  imgPath:" + path)
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, view, "dailyPic")
                    DailyPicActivity.start(this@MainActivity, mHeadData[position].data, path, options)
                }*/
}