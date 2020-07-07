package com.zjta.wyb.ui.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.PushingWindowShopAdapter
import com.zjta.wyb.api.LivePlayerApi
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.entity.WindowShopGoods
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.HttpHelper
import com.zjta.wyb.http.RxSchedulers
import kotlinx.android.synthetic.main.framgnt_pushing_win_shop_edit.*

/**
 * 直播间-编辑商品
 */
class PushingWinShopEditFragment : ViewPagerLazyFragment(), View.OnClickListener {

    private val mData = ArrayList<WindowShopGoods>()
    private val mAdapter = PushingWindowShopAdapter(mData)

    override fun onCreateView(
        original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_pushing_win_shop_edit, container, false)
    }

    override fun lazyInit() {
        val empty = layoutInflater.inflate(R.layout.layout_base_list_empty_view, emptyViewContainer, true)
        rvContent.setEmptyView(empty)

        rvContent.adapter = mAdapter
        rvContent.layoutManager = LinearLayoutManager(rvContent.context)

        rflContent.setOnRefreshListener {
            loadData()
        }
        rflContent.autoRefresh()
    }

    override fun onClick(v: View) {


    }

    private fun loadData() {
        HttpHelper.create(LivePlayerApi::class.java)
            .liveGoodsList(arguments?.getInt("EXTRA_ID") ?: 0)
            .compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<ArrayList<WindowShopGoods>>() {
                override fun onHandleSuccess(t: ArrayList<WindowShopGoods>?, message: String) {
                    if (t == null) return
                    mData.clear()
                    mData.addAll(t)
                    mAdapter.notifyDataSetChanged()
                    rflContent.finishRefresh()
                }

                override fun onHandleError(code: Int, message: String) {
                    super.onHandleError(code, message)
                    rflContent.finishRefresh(false)
                }

            })
    }

    fun newInstance(liveRoomId: Int): PushingWinShopEditFragment {
        val args = Bundle()
        val fragment = PushingWinShopEditFragment()
        args.putInt("EXTRA_ID", liveRoomId)
        fragment.arguments = args
        return fragment
    }

}