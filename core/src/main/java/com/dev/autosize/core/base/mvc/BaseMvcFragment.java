package com.dev.autosize.core.base.mvc;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.autosize.core.BaseApplication;
import com.dev.autosize.core.base.IView;
import com.dev.autosize.core.base.bean.BaseEventBean;
import com.dev.autosize.core.util.LogUtil;
import com.dev.autosize.core.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018-12-28.
 */

public abstract class BaseMvcFragment extends Fragment implements IView {

    private Unbinder unbinder;
    protected Context mContext;
    protected boolean regEvent;
    public ImmersionBar mImmersionBar;

    //管理事件流订阅的生命周期CompositeDisposable
    private CompositeDisposable compositeDisposable;

    public IView mView = this;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this , view);
        initImmersionBar();
        initTitle();
        if (regEvent){
            EventBus.getDefault().register(this);
        }
        return view ;
    }

    /**
     * 返回view
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view,@Nullable Bundle savedInstanceState) {
        super.onViewCreated(view,savedInstanceState);
        init(view);
        initView();
    }

    protected void init(View view){};

    /**
     * rxjava管理订阅者
     */
    protected void addDisposable(Disposable disposable) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        compositeDisposable.add(disposable);
    }

    private void initImmersionBar() {
        if (mImmersionBar == null){
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.init();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("当前运行的fragment:" + getClass().getName());
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
     * 提示网络请求错误信息
     * @param msg
     * @param code
     */
    @Override
    public void showError(String msg, String code) {
        String mCode ="-1";
        if (mCode.equals(code)){
            ToastUtils.showShort(mContext, msg);
            showError();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(BaseEventBean event) {
        onEvent(event);
    }

    protected void onEvent(BaseEventBean event) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (unbinder != null) {
            unbinder.unbind();
        }
        if (mImmersionBar != null){
            mImmersionBar.destroy();
        }
        if (regEvent) {
            EventBus.getDefault().unregister(this);
        }
        // leakCanary 监控
        RefWatcher refWatcher = BaseApplication.getRefWatcher(mContext);
        refWatcher.watch(this);
    }


    /**
     * 获取当前Activity的UI布局
     *
     * @return 布局id
     */
    protected abstract int getLayout();

    /**
     * 初始化标题
     */
    protected abstract void initTitle();

    /**
     * 初始化数据
     */
    protected abstract void initView();

}
