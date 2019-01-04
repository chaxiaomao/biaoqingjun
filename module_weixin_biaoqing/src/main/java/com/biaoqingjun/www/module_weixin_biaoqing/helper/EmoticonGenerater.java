package com.biaoqingjun.www.module_weixin_biaoqing.helper;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Environment;
import android.support.v4.graphics.BitmapCompat;
import android.util.Log;

import com.biaoqingjun.www.module_weixin_biaoqing.MyApplication;
import com.biaoqingjun.www.module_weixin_biaoqing.R;
import com.dev.autosize.core.util.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019-01-04.
 */

public class EmoticonGenerater {

    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mTextPaint = new Paint();
    private int mWidth  = 360;
    private int mHeight  = 240;

    public void drawText(String content) {
        mBitmap = Bitmap.createBitmap(mWidth, mHeight, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);
        mCanvas.drawColor(Color.WHITE);
        mCanvas.drawBitmap(mBitmap, 0.0f, 0.0f, mTextPaint);
        mTextPaint.setColor(Color.BLACK);
        mTextPaint.setTextSize(20);
        mCanvas.drawText(content, 0, 20, mTextPaint);
        mCanvas.save(Canvas.ALL_SAVE_FLAG);
        mCanvas.restore();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/test.gif");
        LogUtil.d(file.toString());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
            mBitmap.compress(Bitmap.CompressFormat.WEBP, 60, fos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }



}
