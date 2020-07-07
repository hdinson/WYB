package com.zjta.wyb.ui.home

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.zjta.wyb.adapter.AppointmentRequireAdapter
import com.zjta.wyb.api.HomeModelApi
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.LiveVideo
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.LinearItemDecoration
import io.reactivex.Observable

/**
 * 有约-我的发单
 */
class AppointmentRequireFragment : HomeModelFragment() {

    override fun initRecycleView(recyclerView: RecyclerView) {
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.addItemDecoration(LinearItemDecoration(this.context, 0f))
    }

    override fun initAdapter(): CommonAdapter<LiveVideo> {
        return AppointmentRequireAdapter(mData)
    }

    override fun initObservable(longTime: Long, limit: Int): Observable<HttpResult<ArrayList<LiveVideo>>> {
        return HttpHelper.create(HomeModelApi::class.java)
            .loadAppointmentRequire(longTime, 0, limit)
    }
}