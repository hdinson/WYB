package com.zjta.wyb.base;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.tencent.rtmp.TXLiveBase;
import com.zjta.wyb.BuildConfig;
import com.zjta.wyb.entity.UserInfo;
import com.zjta.wyb.utils.SPUtils;


public class GlobalApplication extends Application {

    public static final Boolean IS_DEBUG = BuildConfig.DEBUG;

    public static UserInfo user;

    @SuppressLint("StaticFieldLeak")
    private static Context context;


    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        //CrashHandler.INSTANCE.init(ConstantsUtils.INSTANCE.getSDCARD_PRIVATE());
        String licenceURL = "http://license.vod2.myqcloud.com/license/v1/595db1c8195d94cf4d72ec6c255e1502/TXLiveSDK.licence"; // 获取到的 licence url
        String licenceKey = "b2d8640cd5288ab934f0257394b311e4"; // 获取到的 licence key
        TXLiveBase.getInstance().setLicence(this, licenceURL, licenceKey);
    }


    public static Context getContext() {
        return context;
    }

}
