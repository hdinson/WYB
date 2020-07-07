package com.zjta.wyb.ui

import android.app.Instrumentation
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.HomeHistoryHeaderAdapter
import com.zjta.wyb.base.BaseActivity
import com.zjta.wyb.entity.HomeHistoryHeader
import com.zjta.wyb.kotlin.loge
import com.zjta.wyb.kotlin.logi
import com.zjta.wyb.kotlin.logv
import com.zjta.wyb.kotlin.toast
import com.zjta.wyb.utils.LogUtils
import com.zjta.wyb.widget.recycleview.OnRvItemClickListener
import com.zjta.wyb.widget.recycleview.RvItemClickSupport
import kotlinx.android.synthetic.main.activity_test.*
import kotlinx.coroutines.*

class TestActivity : BaseActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        "主线程id：${mainLooper.thread.id}".loge()
        "协程执行开始 -- 线程id：${Thread.currentThread().id}".loge()

        GlobalScope.launch {
            val result1 = GlobalScope.async {
                getResult1()
            }
            val result2 = GlobalScope.async { getResult2(4000) }
            val plus = result2.await() + result1.await() + result2.await()
            plus.loge(plus.toString())
        }
        repeat(8) {
            "主线程执行$it".logv()
        }

        GlobalScope.launch {

            withContext(Dispatchers.IO){

            }
        }




    }


    private suspend fun getResult1(): Int {
        delay(3000)
        "getresult1 ".loge()
        "协程执行结束 -- 线程id：${Thread.currentThread().id}".loge()
        return 1
    }

    private suspend fun getResult2(time: Long): Int {
        delay(time)
        "getresult2 ".loge()
        "协程执行结束 -- 线程id：${Thread.currentThread().id}".loge()
        return 2
    }

    fun <T> CoroutineScope.retrofit() {
        this.launch {

        }
    }
}
