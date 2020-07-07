package com.zjta.wyb.api

import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.LiveVideo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.ArrayList

interface HomeModelApi {

    /**
     * 首页-预约
     */
    @GET("wybDynamicInfo/visitor/topRequireList")
    fun loadTopRequireList(
        @Query("createTimeLong") createTimeLong: Long, @Query("filterType") filterType: Int, @Query("limit") limit: Int
    ): Observable<HttpResult<ArrayList<LiveVideo>>>

    /**
     * 首页-现在
     */
    @GET("wybDynamicInfo/visitor/topNowList")
    fun loadTopNowList(
        @Query("createTimeLong") createTimeLong: Long, @Query("filterType") filterType: Int, @Query("limit") limit: Int
    ): Observable<HttpResult<ArrayList<LiveVideo>>>

    /**
     * 首页-发现
     */
    @GET("wybDynamicInfo/visitor/topDiscoverList")
    fun loadTopDiscoverList(
        @Query("createTimeLong") createTimeLong: Long, @Query("filterType") filterType: Int, @Query("limit") limit: Int
    ): Observable<HttpResult<ArrayList<LiveVideo>>>

    /**
     * 首页-关注
     */
    @GET("wybDynamicInfo/topFollowList")
    fun loadTopFollowList(
        @Query("createTimeLong") createTimeLong: Long, @Query("filterType") filterType: Int, @Query("limit") limit: Int
    ): Observable<HttpResult<ArrayList<LiveVideo>>>

    /**
     * 有约-到点提醒
     */
    @GET("wybDynamicInfo/appointmentRemindList")
    fun loadAppointmentRemind(
        @Query("executeStartTimeLong") createTimeLong: Long, @Query("filterType") filterType: Int, @Query(
            "limit") limit: Int
    ): Observable<HttpResult<ArrayList<LiveVideo>>>



    /**
     * 有约-我的@播
     */
    @GET("wybDynamicInfo/appointmentNoticeList")
    fun loadAppointmentNotice(
        @Query("executeStartTimeLong") createTimeLong: Long, @Query("filterType") filterType: Int, @Query(
            "limit") limit: Int
    ): Observable<HttpResult<ArrayList<LiveVideo>>>


    /**
     * 有约-我的发单
     */
    @GET("wybDynamicInfo/appointmentRequireList")
    fun loadAppointmentRequire(
        @Query("createTimeLong") createTimeLong: Long, @Query("filterType") filterType: Int, @Query(
            "limit") limit: Int
    ): Observable<HttpResult<ArrayList<LiveVideo>>>


}