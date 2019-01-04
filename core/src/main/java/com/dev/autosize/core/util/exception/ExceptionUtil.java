package com.dev.autosize.core.util.exception;

import com.dev.autosize.core.util.LogUtil;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * author: lhl
 * time  : 2017/10/22
 * desc  : 异常信息处理工具类
 */

public final class ExceptionUtil {

    private ExceptionUtil(){
        //empty
    }

    public static void handleException(Throwable e){
        //得到详细的异常信息:
        StringWriter stringWriter=new StringWriter();
        PrintWriter printWriter=new PrintWriter(stringWriter);
        //把异常信息打印到printWtriter,再打到stringWriter
        e.printStackTrace(printWriter);
        //str就是详细的异常信息
        String str=stringWriter.toString();

        LogUtil.e("详细异常信息:\n"+str);

        //接下来,可以对异常信息做联网发送:
    }
}
