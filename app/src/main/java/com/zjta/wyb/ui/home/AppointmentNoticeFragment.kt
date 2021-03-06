package com.zjta.wyb.ui.home

import com.zjta.wyb.adapter.HomeModelNormalAdapter
import com.zjta.wyb.api.HomeModelApi
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.LiveVideo
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.widget.recycleview.CommonAdapter
import io.reactivex.Observable

/**
 * 有约-我的@播
 */
class AppointmentNoticeFragment : HomeModelFragment() {

    override fun initAdapter(): CommonAdapter<LiveVideo> {
        return HomeModelNormalAdapter(mData)
    }

    override fun initObservable(longTime: Long, limit: Int): Observable<HttpResult<ArrayList<LiveVideo>>> {
        return HttpHelper.create(HomeModelApi::class.java)
            .loadAppointmentNotice(longTime, 0, limit)
    }
}