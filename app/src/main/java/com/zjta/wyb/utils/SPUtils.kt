package com.zjta.wyb.utils

import android.content.Context
import com.google.gson.Gson
import com.zjta.wyb.BuildConfig
import com.zjta.wyb.base.GlobalApplication
import com.zjta.wyb.entity.UserInfo

/**
 * SharedPreferences工具类
 */
object SPUtils {

    /**
     * 是否第一次安装
     */
    fun setFirstInstall(context: Context, isFirst: Boolean) {
        putBoolean(context, "config", BuildConfig.VERSION_CODE.toString(), isFirst)
    }

    /**
     * 是否第一次安装
     */
    fun isFirstInstall(context: Context): Boolean {
        return getBoolean(context, "config", BuildConfig.VERSION_CODE.toString(), true)
    }


    /**
     * 保存用户数据
     */
    fun setUserInfo(info: UserInfo) {
        GlobalApplication.user = info
        val json = Gson().toJson(info)
        val encrypt = AESUtils.encrypt(BuildConfig.APPLICATION_ID, json)
        putString(GlobalApplication.getContext(), "app", "info", encrypt)
    }

    /**
     * 保存用户数据
     */
    fun getUserInfo(): UserInfo? {
        if (GlobalApplication.user != null) return GlobalApplication.user
        val deString = getString(GlobalApplication.getContext(), "app", "info", "")
        if (StringUtils.isEmpty(deString)) return null
        val json = AESUtils.decrypt(BuildConfig.APPLICATION_ID, deString)
        val bean = Gson().fromJson<UserInfo>(json, UserInfo::class.java)
        GlobalApplication.user=bean
        return bean
    }


    /******************************************************************************************************/
    /**                             内部实现                                                              **/
    /******************************************************************************************************/
    fun getBoolean(ctx: Context, fileName: String, key: String, defValue: Boolean): Boolean {
        val sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getBoolean(key, defValue)
    }

    fun putBoolean(ctx: Context, fileName: String, key: String, value: Boolean) {
        val sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sp.edit()
            .putBoolean(key, value)
            .apply()
    }

    fun putString(ctx: Context, fileName: String, key: String, value: String) {
        val sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sp.edit()
            .putString(key, value)
            .apply()
    }

    fun getString(ctx: Context, fileName: String, key: String, defValue: String): String {
        val sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getString(key, defValue) ?: ""
    }

    fun putInt(ctx: Context, fileName: String, key: String, value: Int) {
        val sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        sp.edit()
            .putInt(key, value)
            .apply()
    }

    fun getInt(ctx: Context, fileName: String, key: String, defValue: Int): Int {
        val sp = ctx.getSharedPreferences(fileName, Context.MODE_PRIVATE)
        return sp.getInt(key, defValue)
    }
}