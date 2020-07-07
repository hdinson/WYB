package com.zjta.wyb.ui.live

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zjta.wyb.R
import com.zjta.wyb.base.ViewPagerLazyFragment

/**
 * 直播间-导入商品
 */
class PushingWinShopImpFragment : ViewPagerLazyFragment(), View.OnClickListener {

    fun newInstance(liveRoomId: Int): PushingWinShopImpFragment {
        val args = Bundle()
        val fragment = PushingWinShopImpFragment()
        args.putInt("EXTRA_ID", liveRoomId)
        fragment.arguments = args
        return fragment
    }

    override fun onCreateView(
        original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_pushing_win_shop_imp, container, false)
    }

    override fun lazyInit() {

    }

    override fun onClick(v: View) {


    }


}