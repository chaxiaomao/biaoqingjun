package com.dev.autosize.core;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v7.widget.ContentFrameLayout;

import com.dev.autosize.core.util.exception.CatchHandler;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

/**
 * Created by Administrator on 2018-12-27.
 */

public class BaseApplication extends Application {

    private static BaseApplication mBaseApplication;
    private RefWatcher refWatcher;
    // Activity管理
    private ActivityControl mActivityControl;

    // SmartRefreshLayout 有三种方式,请参考:https://github.com/scwang90/SmartRefreshLayout
    // static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(android.R.color.holo_orange_light, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initException();
        initLeakCanary();
        mActivityControl = new ActivityControl();
    }

    private void initLeakCanary() {
        //leakcanary初始化
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);

    }

    private void initException() {
        CatchHandler handler = new CatchHandler();
        Thread thread = Thread.currentThread();
        thread.setUncaughtExceptionHandler(handler);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        mBaseApplication = this;
        // MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    public static BaseApplication getAppContext() {
        return mBaseApplication;
    }

    public static RefWatcher getRefWatcher(Context context) {
        BaseApplication leakApplication = (BaseApplication) context.getApplicationContext();
        return leakApplication.refWatcher;
    }

    public ActivityControl getActivityControl() {
        return mActivityControl;
    }

    /**
     * 程序终止的时候执行
     */
    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
    }

    public void exitApp() {
        mActivityControl.finishAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}
