package com.biaoqingjun.www.module_weixin_biaoqing.helper;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Environment;

import com.bumptech.glide.gifencoder.AnimatedGifEncoder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Administrator on 2019-01-04.
 * 文字表情生成类
 * paint.setTextSize参数区别参考https://www.jianshu.com/p/7f2941dbfb17
 */

public class TextGifGenerator {

    private final static int mWidth = 400; // 背景宽
    private final static int mFrontSize = 40; // 字体大小sp
    private final static int mDistance = 50; // 距离画布顶部 像素
    private final static int mPreLineTextCount = 10; // 每行字数
    private final static int mPreLineHeight = 60; // 每行高度

    public static File drawImage(String content) {

        double lines = (double) content.length() / (double) mPreLineTextCount;
        int ceilLine = (int) Math.ceil(lines); // 向上取整
        int height = mPreLineHeight * ceilLine;

        Bitmap bitmap = Bitmap.createBitmap(mWidth, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);

        Paint paint = new Paint();
        canvas.drawBitmap(bitmap, 0.0f, 0.0f, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(mFrontSize);

        if (ceilLine == 1) {
            canvas.drawText(content, 0, mDistance, paint);
        } else {
            int floorLine = (int) Math.floor(lines); // 向下取整，防止出界
            for (int i = 0; i < floorLine; i++) {
                canvas.drawText(content.substring(i * mPreLineTextCount, (i + 1) * mPreLineTextCount),
                        0, (i + 1) * mDistance, paint);
            }
            canvas.drawText(content.substring((mPreLineTextCount * floorLine), content.length()),
                    0, ceilLine * mDistance, paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        AnimatedGifEncoder encoder = new AnimatedGifEncoder();
        File file = new File(Environment.getExternalStorageDirectory().getPath() + "/biaoqingjun",
                System.currentTimeMillis() + ".gif");
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
