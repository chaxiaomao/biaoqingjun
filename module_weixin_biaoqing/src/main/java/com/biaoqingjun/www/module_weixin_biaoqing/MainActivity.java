package com.biaoqingjun.www.module_weixin_biaoqing;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.NinePatch;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.biaoqingjun.www.module_weixin_biaoqing.helper.EmoticonGenerater;
import com.dev.autosize.core.base.mvc.BaseMvcActivity;
import com.dev.autosize.core.util.KeyboardUtils;
import com.dev.autosize.core.util.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019-01-04.
 */

public class MainActivity extends BaseMvcActivity implements Toolbar.OnMenuItemClickListener{

    @BindView(R2.id.coverBtn)
    Button coverBtn;
    @BindView(R2.id.contentEt)
    EditText contentEt;
    @BindView(R2.id.toolbar)
    Toolbar toolbar;
    @BindView(R2.id.display)
    ImageView display;

    @Override
    protected int getLayoutId() {
        return R.layout.main_activity;
    }

    @Override
    protected void initTitle() {
        toolbar.setTitle(R.string.app_name);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        toolbar.inflateMenu(R.menu.text_biaoqing_menu);
        toolbar.setOnMenuItemClickListener(this);
    }

    @Override
    protected void initView() {
        coverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                transform();
            }
        });
    }

    private File mFile = null;

    private void transform() {
        if ("".equals(contentEt.getText().toString())) {
            ToastUtils.showShort(this, R.string.tip_text_empty);
            return;
        }
        KeyboardUtils.hideSoftInput(contentEt);
        mFile = EmoticonGenerater.drawImage(contentEt.getText().toString().trim());
        if (mFile != null) {
            ToastUtils.showShort(this, R.string.transform_success);
            display.setImageBitmap(BitmapFactory.decodeFile(mFile.getPath()));
        }
    }

    @Override
    public void showError(String msg, String code) {

    }

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        KeyboardUtils.hideSoftInput(contentEt);
        if (item.getItemId() == R.id.clear) {
            contentEt.setText("");
        } else if (item.getItemId() == R.id.share){
            share();
        }
        return false;
    }

    private void share() {
        if (mFile == null) {
            ToastUtils.showShort(this, R.string.tip_image_empty);
            return;
        }
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(mFile));
        startActivity(Intent.createChooser(intent, getResources().getString(R.string.share)));
    }

}
