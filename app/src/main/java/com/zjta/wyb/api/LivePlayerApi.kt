package com.zjta.wyb.api

import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.TestStreamPusher
import com.zjta.wyb.entity.WindowShopGoods
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface LivePlayerApi {
    /**
     * 测试用（推拉流）
     */
    @POST("wybLiveRequireVno/visitor/getPullflowText")
    fun getPullFlowText(
        @Query("streamName") streamName: String
    ): Observable<HttpResult<TestStreamPusher>>


    /**
     * 直播间商品橱窗手动添加
     */
    @Multipart
    @POST("wybLiveInfoGoods/add")
    fun liveInfoGoodsAdd(
        @PartMap goods: HashMap<String,RequestBody>
    ): Observable<HttpResult<Void>>

    /**
     * 直播间商品橱窗手动添加
     */
    @Multipart
    @FormUrlEncoded
    @POST("wybLiveInfoGoods/add")
    fun liveInfoGoodsAdd2(
        @Part("file\";filename=\"1.jpg") file: RequestBody, @Field("liveInfo") liveInfo: Int, @Field(
            "name") name: String, @Field("brand") brand: String, @Field("specification") specification: String, @Field(
            "manufacturer") manufacturer: String, @Field("netWeight") netWeight: String, @Field(
            "introduce") introduce: String, @Field("packaging") packaging: String, @Field("price") price: Double
    ): Observable<HttpResult<Void>>

    /**
     * 获取直播间商品橱窗
     */
    @GET("wybLiveInfoGoods/liveGoodsList")
    fun liveGoodsList(@Query("liveId") liveId: Int): Observable<HttpResult<ArrayList<WindowShopGoods>>>


    /**
     * 直播间商品橱窗手动添加
     */
    @Multipart
    @POST("wybLiveInfoGoods/add")
    fun liveInfoGoodsAdd3(
        @Part("imgFile\";filename=\"1.jpg") file: RequestBody, @Part("liveInfo") liveInfo: Int, @Part(
            "name") name: String, @Part("brand") brand: String, @Part("specification") specification: String, @Part(
            "manufacturer") manufacturer: String, @Part("netWeight") netWeight: String, @Part(
            "introduce") introduce: String, @Part("packaging") packaging: String, @Part("price") price: Double, @Part(
            "activityPrice") activityPrice: Double
    ): Observable<HttpResult<Void>>

}