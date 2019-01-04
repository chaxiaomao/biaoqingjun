package com.dev.autosize.core.base.bean;

/**
 * Created by Administrator on 2018-12-28.
 */

public class BaseEventBean<T> {
    private int type;
    private T obj;

    public BaseEventBean(int type, T obj) {
        this.type = type;
        this.obj = obj;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }
}
