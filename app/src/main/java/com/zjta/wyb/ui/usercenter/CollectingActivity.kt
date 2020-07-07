package com.zjta.wyb.ui.usercenter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.zjta.wyb.adapter.CollectingGoodsAdapter
import com.zjta.wyb.api.DownloadServiceApi
import com.zjta.wyb.api.UserActionApi
import com.zjta.wyb.base.BaseRefreshListActivity
import com.zjta.wyb.entity.CollectingGoods
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.RechargeDetail
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.widget.recycleview.CommonAdapter
import io.reactivex.Observable
import okhttp3.ResponseBody

/**
 * 我的收藏列表
 */
class CollectingActivity : BaseRefreshListActivity<CollectingGoods>() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)


        mData.add(CollectingGoods())
        mData.add(CollectingGoods())
        mData.add(CollectingGoods())
        mData.add(CollectingGoods())

        mAdapter.notifyDataSetChanged()

    }

    override fun setActivityTitle(): String {
        return "收藏夹"
    }

    override fun initAdapter(): CommonAdapter<CollectingGoods> = CollectingGoodsAdapter(mData)

    companion object {
        fun start(context: Context) {
            context.startActivity(Intent(context, CollectingActivity::class.java))
        }
    }

    override fun initObservable(reload: Boolean, page: Int): Observable<HttpResult<ArrayList<CollectingGoods>>> {
        return HttpHelper.create(UserActionApi::class.java)
            .loadCollectingGoods()
    }
}