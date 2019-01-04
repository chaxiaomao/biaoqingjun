package com.dev.autosize.core.base.mvc;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;

import com.dev.autosize.core.BaseApplication;
import com.dev.autosize.core.R;
import com.dev.autosize.core.base.IView;
import com.dev.autosize.core.base.bean.BaseEventBean;
import com.dev.autosize.core.helper.HUDFactory;
import com.dev.autosize.core.util.LogUtil;
import com.dev.autosize.core.util.StatuBarCompat;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018-12-28.
 * MVC模式的Base Activity
 */

public abstract class BaseMvcActivity extends AppCompatActivity implements IView {

    private Unbinder unbinder;
    public ImmersionBar mImmersionBar;
    public BaseMvcActivity mActivity;
    protected boolean regEvent;
    //管理事件流订阅的生命周期CompositeDisposable
    private CompositeDisposable compositeDisposable;
    public KProgressHUD kProgressHUD;
    public IView mView = this;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        BaseApplication.getAppContext().getActivityControl().addActivity(this);
        initImmersionBar();
        mActivity = this;
        initTitle();
        initView();
        if (regEvent) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void showLoadingProgress(String msg) {
        if (TextUtils.isEmpty(msg)){
            msg = getString(R.string.loading);
        }
        kProgressHUD = HUDFactory.getInstance().creatHUD(this);
        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(TextUtils.isEmpty(msg) ? getString(R.string.loading) : msg)
                // .setLabel(null)
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.3f).show();
    }

    @Override
    public void dismissLoadingProgress() {
        if (null != kProgressHUD && kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        }
    }

    private void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        // 所有子类都将继承这些相同的属性,暂时先不加,会导入全部状态栏都一致
        // mImmersionBar.fitsSystemWindows(true).statusBarColor(R.color.bar_grey).init();
    }

    /**
     * 沉浸式状态栏
     */
    protected void setImmersionStatusBar() {
        StatuBarCompat.setImmersiveStatusBar(true, Color.WHITE, this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEventBean event) {
        onEvent(event);
    }

    protected void onEvent(BaseEventBean event) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * rxjava管理订阅者
     */
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("当前Activity：" + getClass().getName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 解除订阅关系
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }

        if (unbinder != null) {
            unbinder.unbind();
        }
        if (regEvent) {
            EventBus.getDefault().unregister(this);
        }
        // 必须调用该方法，防止内存泄漏
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
        dismissLoadingProgress();
        BaseApplication.getAppContext().getActivityControl().removeActivity(this);
    }

    /**
     * 销毁
     * @param view
     */
    public void back(View view) {
        finish();
    }

    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 初始化数据
     */
    protected abstract void initView();

}
