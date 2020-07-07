package com.zjta.wyb.ui.usercenter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zjta.wyb.R
import com.zjta.wyb.adapter.SimpleTextAdapter
import com.zjta.wyb.base.ViewPagerLazyFragment
import com.zjta.wyb.kotlin.click
import com.zjta.wyb.utils.GlideUtils
import com.zjta.wyb.utils.SPUtils
import kotlinx.android.synthetic.main.framgnt_home_city.*
import kotlinx.android.synthetic.main.framgnt_v_manager_assets.*

/**
 * v 管理-资产
 */
class VManagerAssetsFragment : ViewPagerLazyFragment(), View.OnClickListener {

    override fun onCreateView(
        original: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return original.inflate(R.layout.framgnt_v_manager_assets, container, false)
    }

    override fun lazyInit() {
        resetUser()
        vSettings.setOnClickListener(this)
        vAddress.setOnClickListener(this)
        vCollecting.setOnClickListener(this)
        vEditUser.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        resetUser()
    }

    /**
     * 用户信息重置
     */
    private fun resetUser() {
        val user = SPUtils.getUserInfo()
        if (user != null) {
            GlideUtils.setAvatarImageWithBorder(ivAvatar.context, user.imgUrl, ivAvatar)
            tvNickName.text = user.name
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.vSettings -> UserSettingActivity.start(v.context)
            R.id.vAddress -> AddressManagerActivity.start(v.context)
            R.id.vCollecting -> CollectingActivity.start(v.context)
            R.id.vEditUser -> SPUtils.getUserInfo()?.let { UpdateUserInfoActivity.start(v.context, it) }
        }
    }


}