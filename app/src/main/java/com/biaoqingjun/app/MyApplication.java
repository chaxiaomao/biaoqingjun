package com.biaoqingjun.app;


import android.app.Application;

import com.dev.autosize.core.BaseApplication;
import com.dev.autosize.projectcode.constant.RouterConfig;

/**
 * Created by Administrator on 2018-12-26.
 */

public class MyApplication extends BaseApplication {

    private static MyApplication myApplication;
    // 初始化
    public static MyApplication getInstance() {
        return myApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        myApplication = this ;
        // ARouter路由初始化
        RouterConfig.init(this, BuildConfig.DEBUG);
    }

}
