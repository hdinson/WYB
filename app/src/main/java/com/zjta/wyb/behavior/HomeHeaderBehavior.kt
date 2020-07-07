package com.zjta.wyb.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import com.zjta.wyb.utils.LogUtils


class HomeHeaderBehavior @JvmOverloads constructor(context: Context? = null, attrs: AttributeSet? = null) : AppBarLayout.ScrollingViewBehavior() {

    private var startY = 0f

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

        //dependency  is  appbar
        return super.onDependentViewChanged(parent, child, dependency)

    }

    override fun onNestedFling(coordinatorLayout: CoordinatorLayout, child: View, target: View, velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        LogUtils.e("    onNestedFling <--------")
        return super.onNestedFling(coordinatorLayout, child, target, velocityX, velocityY, consumed)
    }

    /* override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {

     }*/

}