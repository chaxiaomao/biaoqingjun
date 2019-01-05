package com.biaoqingjun.www.module_weixin_biaoqing.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;

import com.bumptech.glide.gifencoder.AnimatedGifEncoder;
import com.dev.autosize.core.util.LogUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019-01-04.
 */

public class EmoticonGenerater {

    private static int mWidth = 400;
    private static int mFrontSize = 40;
    private static int mDistance = 50;
    private static int mPreLineTextCount = 10;
    private static int mPreLineHeight = 60;

    public static File drawImage(String content) {

        double lines = (double) content.length() / (double) mPreLineTextCount;
        int height = (int) (mPreLineHeight * Math.ceil(lines));

        Bitmap bitmap = Bitmap.createBitmap(mWidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(mFrontSize);

        if (Math.ceil(lines) == 1) {
            canvas.drawText(content, 0, mDistance, paint);
        } else {
            for (int i = 0; i < Math.floor(lines); i++) {
                canvas.drawText(content.substring(i * mPreLineTextCount, (i + 1) * mPreLineTextCount), 0, (i + 1) * mDistance, paint);
            }
            canvas.drawText(content.substring((mPreLineTextCount * (int) (Math.floor(lines))), content.length()), 0, (float) (Math.ceil(lines) * mDistance), paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/biaoqingjun"
                + System.currentTimeMillis() + ".gif");
        try {
            FileOutputStream fos = new FileOutputStream(file);
            encoder.start(fos);
            encoder.addFrame(bitmap);
            encoder.setRepeat(0);
            encoder.setDelay(1000);
            encoder.finish();
            fos.close();
            return file;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
