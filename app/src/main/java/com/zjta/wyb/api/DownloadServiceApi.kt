package com.zjta.wyb.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Streaming
import retrofit2.http.Url

/**
 * service统一接口数据
 */
interface DownloadServiceApi {
    /*断点续传下载接口*/
    @Streaming
    /*大文件需要加入这个判断，防止下载过程中写入到内存中*/
    @GET
    fun download(
        @Header("RANGE") start: String, @Url url: String
    ): Observable<ResponseBody>
}