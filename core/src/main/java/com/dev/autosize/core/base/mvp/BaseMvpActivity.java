package com.dev.autosize.core.base.mvp;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.dev.autosize.core.BaseApplication;
import com.dev.autosize.core.R;
import com.dev.autosize.core.base.IPresenter;
import com.dev.autosize.core.base.IView;
import com.dev.autosize.core.base.MvpCallback;
import com.dev.autosize.core.base.bean.BaseEventBean;
import com.dev.autosize.core.helper.HUDFactory;
import com.dev.autosize.core.util.LogUtil;
import com.dev.autosize.core.util.StatuBarCompat;
import com.dev.autosize.core.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2018-12-28.
 * MVP模式的Base Activity
 */

public abstract class BaseMvpActivity<V extends IView, P extends IPresenter<V>> extends AppCompatActivity implements MvpCallback<V, P>, IView {

    protected P mPresenter;
    protected V mView;
    private Unbinder unbinder;
    protected boolean regEvent;
    public BaseMvpActivity mActivity;
    public ImmersionBar mImmersionBar;
    private CompositeDisposable compositeDisposable;
    public KProgressHUD kProgressHUD;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        BaseApplication.getAppContext().getActivityControl().addActivity(this);
        initImmersionBar();
        mActivity = this;
        onViewCreated();
        initTitle();
        initView();
        initListener();
        if (regEvent) {
            EventBus.getDefault().register(this);
        }
    }

    protected void initListener() {
        mPresenter.attachView(getMvpView());
    }

    private void initImmersionBar() {
        mImmersionBar = ImmersionBar.with(this);
        // 所有子类都将继承这些相同的属性
        // mImmersionBar.init();
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

    /**
     * 沉浸式状态栏
     */
    protected void setImmersionStatusBar() {
        StatuBarCompat.setImmersiveStatusBar(true, Color.WHITE, this);
    }

    public void onViewCreated() {
        mView = createView();
        if (mPresenter == null) {
            mPresenter = createPresenter();
            getLifecycle().addObserver(mPresenter);
        }
        mPresenter = getPresenter();
        mPresenter.attachView(getMvpView());
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

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d("当前activity：" + getClass().getName());
    }

    public void back(V v) {
        finish();
    }

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        setPresenter(null);
        setMvpView(null);
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

    @Override
    public P getPresenter() {
        return mPresenter;
    }

    @Override
    public void setPresenter(P presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void setMvpView(V view) {
        this.mView = view;
    }

    @Override
    public V getMvpView() {
        return this.mView;
    }

    /**
     * 提示网络请求错误信息
     * @param msg
     * @param code
     */
    @Override
    public void showError(String msg, String code) {
        String mCode ="-1";
        if (mCode.equals(code)){
            ToastUtils.showShort(mActivity, msg);
        }

    }

    /**
     * 空界面显示
     */
    @Override
    public void showNormal() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showEmptyView() {

    }

    @Override
    public void showError() {

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

    /**
     * 加载数据
     */
    protected  void loadData(){};

}
