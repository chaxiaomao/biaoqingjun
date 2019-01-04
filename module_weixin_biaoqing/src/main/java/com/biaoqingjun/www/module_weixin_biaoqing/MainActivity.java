package com.biaoqingjun.www.module_weixin_biaoqing;

import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.biaoqingjun.www.module_weixin_biaoqing.helper.EmoticonGenerater;
import com.dev.autosize.core.util.LogUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2019-01-04.
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.displayTv)
    TextView displayTv;
    @BindView(R2.id.coverBtn)
    Button coverBtn;
    @BindView(R2.id.contentEt)
    EditText contentEt;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ButterKnife.bind(this);
        coverBtn = findViewById(R.id.coverBtn);
        contentEt = findViewById(R.id.contentEt);
        coverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EmoticonGenerater generater = new EmoticonGenerater();
                generater.drawText(contentEt.getText().toString());
            }
        });
    }
}
