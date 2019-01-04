package com.dev.autosize.core.base.mvp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.autosize.core.BaseApplication;
import com.dev.autosize.core.base.IPresenter;
import com.dev.autosize.core.base.IView;
import com.dev.autosize.core.base.MvpCallback;
import com.dev.autosize.core.base.bean.BaseEventBean;
import com.dev.autosize.core.base.mvc.BaseMvcActivity;
import com.dev.autosize.core.util.LogUtil;
import com.gyf.barlibrary.ImmersionBar;
import com.squareup.leakcanary.RefWatcher;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2018-12-28.
 * MVP模式的Base fragment
 */

public abstract class BaseMvpFragment<V extends IView, P extends IPresenter<V>> extends Fragment implements MvpCallback<V, P>, IView {

    private Unbinder unbinder;
    protected Context mContext;
    protected boolean regEvent;
    protected P mPresenter;
    protected V mView;
    private BaseMvcActivity mBaseActivity;
    public ImmersionBar mImmersionBar;
    private CompositeDisposable compositeDisposable;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mContext = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        unbinder = ButterKnife.bind(this, view);
        initImmersionBar();
        onViewCreated();
        initTitle();
        if (regEvent){
            EventBus.getDefault().register(this);
        }
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);
        initView();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i("当前运行的fragment:" + getClass().getName());
    }

    /**
     * 回传view
     * @param view
     */
    protected void init(View view){};

    private void initImmersionBar() {
        if (mImmersionBar == null) {
            mImmersionBar = ImmersionBar.with(this);
            mImmersionBar.init();
        }
    }

    /**
     * 初始化presenter
     */
    public void onViewCreated() {
        mView = createView();
        if (getPresenter() == null) {
            mPresenter = createPresenter();
            getLifecycle().addObserver(mPresenter);
        }
        mPresenter = getPresenter();
        //在这个时候才attach view是因为这个时候view的初始化已经基本完成,在Presenter中调用view的域也不会为空
        mPresenter.attachView(getMvpView());
    }

    /**
     * eventBut
     * @param event
     */
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
        setPresenter(null);
        setMvpView(null);
        if (regEvent) {
            EventBus.getDefault().unregister(this);
        }
        //leakCanary 监控
        RefWatcher refWatcher = BaseApplication.getRefWatcher(mContext);
        refWatcher.watch(this);
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

    /**
     * view 填充之前，获取 Intent 数据，绑定 Presenter 等
     * 注意：获取 intent 的数据需要在 super 之前,否则如果创建 Presenter 使用到这些数据的话，这些数据在使用时还未被赋值
     *
     * @param saveInstanceState
     */
    protected void init(Bundle saveInstanceState) {
    }

}
