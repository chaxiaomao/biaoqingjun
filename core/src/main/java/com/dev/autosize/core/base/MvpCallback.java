package com.dev.autosize.core.base;

/**
 * Created by Administrator on 2018-12-28.
 * mvp模式抽象vp的解绑和绑定过程
 */

public interface MvpCallback<V extends IView, P extends IPresenter<V>> {

    //创建Presenter  调用在init中
    P createPresenter();

    //创建View
    V createView();

    void setPresenter(P presenter);

    P getPresenter();

    void setMvpView(V view);

    V getMvpView();
}
