package com.biaoqingjun.app;


import com.dev.autosize.core.BaseApplication;
import com.dev.autosize.projectcode.constant.RouterConfig;

/**
 * Created by Administrator on 2018-12-26.
 */

public class BiaoqingJunApplication extends BaseApplication {

    private static BiaoqingJunApplication myApplication;
    // 初始化
    public static BiaoqingJunApplication getInstance() {
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
