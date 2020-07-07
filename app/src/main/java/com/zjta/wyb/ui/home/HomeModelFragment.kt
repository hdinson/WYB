package com.zjta.wyb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.SCROLL_STATE_DRAGGING
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.zjta.wyb.R
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.entity.LiveVideo
import com.zjta.wyb.event.HomeAppbarEv
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.http.RxSchedulers
import com.zjta.wyb.kotlin.dip
import com.zjta.wyb.kotlin.loge
import com.zjta.wyb.utils.DateUtils
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.recycleview.StaggeredItemDecoration
import io.reactivex.Observable
import kotlinx.android.synthetic.main.framgnt_home_city.rflContent
import kotlinx.android.synthetic.main.framgnt_home_model.*
import org.greenrobot.eventbus.EventBus


/**
 * 首页-四模块向上抽取
 */
abstract class HomeModelFragment : ViewPagerLazyFragment() {

    val mAdapter by lazy {
        initAdapter()
    }

    val mData = ArrayList<LiveVideo>()
    private val mPageSize = 20  //分页数据


    override fun lazyInit() {
        val empty = layoutInflater.inflate(setEmptyLayoutId(), emptyViewContainer, true)
        rvContent.setEmptyView(empty)

        rvContent.adapter = mAdapter
        initRecycleView(rvContent)

        rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    SCROLL_STATE_DRAGGING -> {
                        EventBus.getDefault()
                            .post(HomeAppbarEv.getInstance(false))
                    }
                }
            }
        })


        rflContent.setOnRefreshListener {
            loadData(true)
        }
        rflContent.setOnLoadMoreListener {
            loadData(false)
        }

        //进来就加载数据,无数据禁止记载更多
        rflContent.setEnableAutoLoadMore(true)
        rflContent.setEnableLoadMore(false)
        val autoRefresh = rflContent.autoRefresh()
        loge("lazyInit  ..  $autoRefresh")

        twoLevelHeader.setOnTwoLevelListener {
            EventBus.getDefault()
                .post(HomeAppbarEv.getInstance(true))
            false
        }
    }

    override fun onResume() {
        super.onResume()
        if (mData.isEmpty()){
            autoRefresh()
        }
    }

    private var lastDataTime = DateUtils.getCurrentTimeMillis13()
    private fun loadData(reload: Boolean) {
        if (reload) lastDataTime = DateUtils.getCurrentTimeMillis13()
        initObservable(lastDataTime, mPageSize).compose(RxSchedulers.io_main())
            .subscribe(object : BaseObserver<ArrayList<LiveVideo>>() {
                override fun onHandleSuccess(t: ArrayList<LiveVideo>?, message: String) {
                    if (t == null) return
                    if (t.isNotEmpty()) lastDataTime = t.last()
                        .createTimeLong

                    if (reload) {
                        mData.clear()
                        mData.addAll(t)
                        mAdapter.notifyDataSetChanged()

                        rflContent.finishRefresh()
                        rflContent.resetNoMoreData()
                    } else {
                        val start = mData.size
                        mData.addAll(t)
                        mAdapter.notifyItemRangeInserted(start, t.size)
                        rflContent.finishLoadMore()
                    }

                    rflContent.setEnableLoadMore(mData.isNotEmpty())

                    if (t.size < mPageSize) {
                        rflContent.finishLoadMoreWithNoMoreData()
                    }
                }

                override fun onHandleError(code: Int, message: String) {
                    super.onHandleError(code, message)
                    rflContent.finishRefresh(false)
                    rflContent.finishLoadMore(false)
                }
            })
    }

    override fun onCreateView(
        original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_home_model, container, false)
    }

    protected open fun addDefaultData(data: ArrayList<LiveVideo>) {}
    open fun setEmptyLayoutId() = R.layout.layout_base_list_empty_view
    abstract fun initAdapter(): CommonAdapter<LiveVideo>
    abstract fun initObservable(longTime: Long, limit: Int): Observable<HttpResult<ArrayList<LiveVideo>>>

    /**
     * 自动刷新
     */
    public fun autoRefresh(): Boolean {
        return rflContent.autoRefresh()
    }

    /**
     * 获取列表控件
     */
    public fun getRecycleView() = rvContent

    open fun initRecycleView(recyclerView: RecyclerView) {
        val manager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        manager.gapStrategy = StaggeredGridLayoutManager.GAP_HANDLING_NONE
        manager.invalidateSpanAssignments()
        recyclerView.itemAnimator = null
        recyclerView.layoutManager = manager
        recyclerView.addItemDecoration(StaggeredItemDecoration(dip(2), dip(0), dip(2), 0))
    }

}