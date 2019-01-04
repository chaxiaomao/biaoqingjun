package com.dev.autosize.projectcode.constant;

/**
 * Created by Administrator on 2018-12-27.
 */

public interface Constant {
    String TAG = "demo";

    String RESULT_CODE = "000";

    String HEADER_KEY_AUTH = "Authorization";

    String AUTH_TOKEN_PRE = "Bearer ";

    String ENCTYPE = "charset=utf-8"; // 编码方式

    String CONTENT_TYPE_JSON = "application/json; " + ENCTYPE; // application/json 类型的 Content-Type

    String CONTENT_TYPE_TEXT = "application/name; " + ENCTYPE; // application/json 类型的Content-Type

    String CONTENT_TYPE_FORM = "application/name; " + ENCTYPE; // application/json 类型的Content-Type
}
