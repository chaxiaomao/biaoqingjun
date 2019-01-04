package com.dev.autosize.core.base.mvc;

import android.support.v4.widget.SwipeRefreshLayout;

/**
 * Created by Administrator on 2018-12-28.
 */

public abstract class BaseMvcListActivity extends BaseMvcActivity implements SwipeRefreshLayout.OnRefreshListener {

    protected SwipeRefreshLayout mRefreshLayout;

    @Override
    protected void initView() {
        if (mRefreshLayout != null) {
            mRefreshLayout.setOnRefreshListener(this);
        }
    }

    @Override
    public void onRefresh() {

    }
}
