/*
 Copyright © 2015, 2016 Jenly Yu <a href="mailto:jenly1314@gmail.com">Jenly</a>

 Licensed under the Apache License, Version 2.0 (the "License");
 you may not use this file except in compliance with the License.
 You may obtain a copy of the License at

 	http://www.apache.org/licenses/LICENSE-2.0

 Unless required by applicable law or agreed to in writing, software
 distributed under the License is distributed on an "AS IS" BASIS,
 WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 See the License for the specific language governing permissions and
 limitations under the License.

 */
package com.dev.autosize.core.util;

import android.util.Log;

import com.dev.autosize.core.BuildConfig;


/**
 * author: lhl
 * time  : 2017/10/22
 * desc  : 日志工具类，所有log输出必须使用此工具类
 */

public final class LogUtil {
	private final static String TAG = BuildConfig.APPLICATION_ID;
	/**
	 * 类名
	 */
	private static String className;
	/**
	 * 方法名
	 */
	private static String methodName;
	/**
	 *行数
	 */
	private static int lineNumber;

	private LogUtil(){
		//empty
	}

	private static String createLog(String log ) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(methodName);
		buffer.append("()");
		buffer.append("(").append(className).append(":").append(lineNumber).append(")\n");
		buffer.append(log);
		return buffer.toString();
	}

	private static void getMethodNames(StackTraceElement[] sElements){
		className = sElements[1].getFileName();
		methodName = sElements[1].getMethodName();
		lineNumber = sElements[1].getLineNumber();
	}


	public static void e(String message){
		if (BuildConfig.DEBUG){
			// Throwable instance must be created before any methods
			getMethodNames(new Throwable().getStackTrace());
			Log.e(TAG, createLog(message));
		}

	}

	public static void e(Object object){
		if (BuildConfig.DEBUG){
			// Throwable instance must be created before any methods
			getMethodNames(new Throwable().getStackTrace());
			Log.e(TAG, createLog(object.toString()));
		}

	}

	public static void i(String message){
		if (BuildConfig.DEBUG){
			getMethodNames(new Throwable().getStackTrace());
			Log.i(TAG, createLog(message));
		}
	}

	public static void d(String message){
		if (BuildConfig.DEBUG){
			getMethodNames(new Throwable().getStackTrace());
			Log.d(TAG, createLog(message));
		}

	}

	public static void v(String message){
		if (BuildConfig.DEBUG){
			getMethodNames(new Throwable().getStackTrace());
			Log.v(TAG, createLog(message));
		}

	}

	public static void w(String message){
		if (BuildConfig.DEBUG){
			getMethodNames(new Throwable().getStackTrace());
			Log.w(TAG, createLog(message));
		}
	}

	public static void wtf(String message){
		if (BuildConfig.DEBUG){
			getMethodNames(new Throwable().getStackTrace());
			Log.wtf(TAG, createLog(message));
		}
	}


}
