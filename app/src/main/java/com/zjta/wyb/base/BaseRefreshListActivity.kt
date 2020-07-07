package com.zjta.wyb.base

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.zjta.wyb.R
import com.zjta.wyb.entity.HttpResult
import com.zjta.wyb.http.BaseObserver
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.utils.SystemBarModeUtils
import com.zjta.wyb.widget.recycleview.CommonAdapter
import com.zjta.wyb.widget.titlebar.CommonTitleBar
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base_refresh_list.*

abstract class BaseRefreshListActivity<T> : BaseActivity() {

    val mAdapter by lazy {
        initAdapter()
    }

    val mData = ArrayList<T>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base_refresh_list)

        initUI()

    }

    private fun initUI() {
        SystemBarModeUtils.darkMode(this, true)
        commonTitleBar.titleTextTv.text = setActivityTitle()
        commonTitleBar.titleLeftBtn.click { onBackPressed() }


        val empty = layoutInflater.inflate(setEmptyLayoutId(), emptyViewContainer, true)
        rvContent.setEmptyView(empty)

        rvContent.adapter = mAdapter
        rvContent.layoutManager = LinearLayoutManager(this)

        rflContent.setOnRefreshListener {
            loadData(true)
        }
        rflContent.setOnLoadMoreListener {
            loadData(false)
        }
    }

    private var mPage = 1
    private val mPageSize = 20
    private fun loadData(reload: Boolean) {
        if (reload) mPage = 1
        initObservable(reload, mPage).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : BaseObserver<ArrayList<T>>() {
                override fun onHandleSuccess(t: ArrayList<T>?, message: String) {
                    if (t == null) return
                    if (reload) {
                        mData.clear()
                        addDefaultData(mData)
                    }
                    if (t.isNotEmpty()) mPage++
                    mData.addAll(t)
                    mAdapter.notifyDataSetChanged()
                    if (t.size < mPageSize) {
                        if (reload) {
                            rflContent.finishRefreshWithNoMoreData()
                        } else {
                            rflContent.finishLoadMore()
                        }
                    } else {
                        rflContent.finishRefresh()
                    }
                }

                override fun onHandleError(code: Int, message: String) {
                    super.onHandleError(code, message)
                    rflContent.finishRefresh(false)
                }
            })

    }

    open fun setActivityTitle() = ""
    open fun setEmptyLayoutId() = R.layout.layout_base_list_empty_view
    protected open fun addDefaultData(data: ArrayList<T>) {}

    abstract fun initAdapter(): CommonAdapter<T>
    abstract fun initObservable(reload: Boolean, page: Int): Observable<HttpResult<ArrayList<T>>>


    public fun getRefreshLayout(): SmartRefreshLayout = rflContent
    public fun getTitleBarView(): CommonTitleBar = commonTitleBar
}