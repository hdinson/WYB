package com.zjta.wyb.widget.bottomrarlayout;

import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * @author chaychan
 * @date 2017/3/7  17:19
 */
public class UIUtils {
    /**
     * dip-->px
     */
    public static int dip2Px(Context context,int dip) {
        float density = context.getResources().getDisplayMetrics().density;
        int px = (int) (dip * density + 0.5f);
        return px;
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue
     * @return
     */
    public static int sp2px(Context context,float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getColor(Context context,int colorId){
        return context.getResources().getColor(colorId);
    }

    public static Drawable getDrawable(Context context,int resId){
        return  context.getResources().getDrawable(resId);
    }
}
