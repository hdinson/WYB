package com.zjta.wyb.http.manager

import android.content.Context
import android.net.ConnectivityManager
import android.text.TextUtils.substring
import com.zjta.wyb.base.GlobalApplication
import com.zjta.wyb.entity.UserInfo
import com.zjta.wyb.kotlin.logv
import com.zjta.wyb.utils.*
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.net.URLDecoder

/**
 *  日志拦截器+缓存策略
 *  缓存策略：有网络时，根据Cache-Control决定要不要网络请求
 *           无网络时，从本地缓存取数据
 */
class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response? {

        logv("\t┌${"─".times(120)}", showLine = false)
        val original = chain.request()

        val userToken = SPUtils.getUserInfo()
        val request = if (userToken != null) {
            val code = buildDynamicCode(userToken)
            val url = original.url()
                .newBuilder()
                .addQueryParameter("dynamicKey", userToken.id)
                .addQueryParameter("dynamicCode", code)
                .build()
            original.newBuilder()
                .url(url)
                .build()
        } else original
        logv("\t│ Request{method= ${original.method()}, url=${request.url()}}", showLine = false)
        request.body()?.apply {
            logv("\t│ Body: ${Buffer().let {
                this.writeTo(it)
                 it.readUtf8()
            }}", showLine = false)
        }
        /*when {
        isNetworkAvailable() -> chain.request()
            else -> {
                logv("\t│ Loaded From LocalCache - Network error", showLine = false)
                //强制使用缓存
                chain.request().newBuilder().cacheControl(CacheControl.FORCE_CACHE).build()
            }
        }*/

        val t1 = System.nanoTime()
        val response = chain.proceed(request)
        val t2 = System.nanoTime()


        logv("\t│ Received response for in ${(t2 - t1) / 1e6}ms", showLine = false)

        val mediaType = response.body()!!.contentType()
        val content = response.body()!!.string()
        if (content.isNotEmpty()) logv("\t│ ${content.replace("\n", "")}", showLine = false)
        logv("\t└${"─".times(120)}", showLine = false)
        return response.newBuilder()
            .removeHeader("Pragma")
            .body(okhttp3.ResponseBody.create(mediaType, content))
            .build()
    }

    private fun buildDynamicCode(info: UserInfo): String {
        val time = DateUtils.getCurrentDateTime("yyyy-MM-dd HH:mm")
        val md5 = MD5.encode("${info.id}$time${info.saltValue}")
        val re = Regex("[a-zA-Z]")
        var num = re.replace(md5, "")
        while (num.length < 8) {
            num += num
        }
        return substring(num, 0, 8)
    }


    /**
     * 判断网络是否连接
     *
     * 需添加权限 `<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>`
     *
     * @return `true`: 是<br></br>`false`: 否
     */
    private fun isNetworkAvailable(): Boolean {
        val info = (GlobalApplication.getContext().getSystemService(
            Context.CONNECTIVITY_SERVICE) as ConnectivityManager).activeNetworkInfo
        return info != null && info.isConnected
    }

    /**
     *   字符串相关的扩展方法
     */
    fun String.times(count: Int): String {
        return (1..count).fold(StringBuilder()) { acc, _ ->
            acc.append(this)
        }
            .toString()
    }
}


