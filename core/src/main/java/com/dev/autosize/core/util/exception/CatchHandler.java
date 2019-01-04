package com.dev.autosize.core.util.exception;

/**
 * author: lhl
 * time  : 2017/10/22
 * desc  : 捕获全局异常，程序任何地方只要出了异常，又没有加try catch,异常就会出现在uncaughtException（）
 */

public class CatchHandler implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //调用工具类处理异常信息
        ExceptionUtil.handleException(e);
        //程序非正常退出,1
        System.exit(1);
    }
}
