package com.zjta.wyb.api

import com.zjta.wyb.entity.CollectingGoods
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.UserInfo
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.*

/**
 * 用户操作接口数据
 */
interface UserActionApi {
    /*收藏夹*/
    @GET("http://")
    fun loadCollectingGoods(): Observable<HttpResult<ArrayList<CollectingGoods>>>


    /**
     *获取验证码
     *验证码类型：1注册，2登录
     */
    @POST("wybUserAccount/visitor/getVerificationCode")
    fun queryVerificationCode(
        @Query("areaCode") areaCode: String, @Query("account") account: String, @Query("type") type: Int
    ): Observable<HttpResult<Void>>

    /**
     *登录
     */
    @POST("wybUserAccount/visitor/login?clientType=20")
    fun doLogin(
        @Query("areaCode") areaCode: String, @Query("account") account: String, @Query("code") code: String, @Query(
            "type") type: Int, @Query("longTime") longTime: String
    ): Observable<HttpResult<UserInfo>>

    /**
     *登出
     */
    @POST("wybUserAccount/logout")
    fun doLogout(
        @Query("areaCode") areaCode: String, @Query("account") account: String, @Query("longTime") longTime: String
    ): Observable<HttpResult<Void>>

    /**
     *注册
     */
    @POST("wybUserAccount/register?clientType=20")
    fun doRegister(
        @Query("areaCode") areaCode: String, @Query("account") account: String, @Query("code") code: String, @Query(
            "longTime") longTime: String
    ): Observable<HttpResult<UserInfo>>


    /**
     * 修改用户昵称
     */
    @POST("wybUserAccount/upName")
    fun updateUserName(@Query("name") newName: String): Observable<HttpResult<Void>>


    /**
     * 修改头像
     */
    @Multipart
    @POST("wybUserAccount/upImg")
    fun updateUserAvatar(
        @Part("file\";filename=\"1.jpg") file: RequestBody
    ): Observable<HttpResult<Void>>

    /**
     * 修改头像
     */
    @Multipart
    @POST("wybUserAccount/upImg")
    fun updateUserAvatar2(
        @Part partList: List<MultipartBody.Part>
    ): Observable<HttpResult<Void>>


}