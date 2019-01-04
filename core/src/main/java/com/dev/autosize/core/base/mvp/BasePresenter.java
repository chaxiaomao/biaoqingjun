package com.dev.autosize.core.base.mvp;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.view.View;

import com.dev.autosize.core.base.IModel;
import com.dev.autosize.core.base.IView;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018-12-28.
 * v泛型p,实现v,就是v,构造p调用p层方法;p泛型v,v调用v的方法,
 */

public abstract class BasePresenter<V extends IView, M extends IModel> {

    protected V mView;
    protected M mModel;
    // 管理事件订阅的生命周期
    protected CompositeDisposable compositeDisposable;

    @CallSuper
    public void attachView(V view) {
        this.mView = view;
        if (mModel == null) {
            mModel = createModel();
        }
    }

    public void detachView() {
        if (mModel != null) {
            clearPool();
        }
        mView = null;
        mModel = null;
    }

    public Context getContext() {
        return mView.getContext();
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

    public void clearPool() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

    public M getModel() {return mModel;}

    protected abstract M createModel();

}
