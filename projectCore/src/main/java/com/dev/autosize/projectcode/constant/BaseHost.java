package com.dev.autosize.projectcode.constant;

/**
 * Created by Administrator on 2018-12-28.
 */

public class BaseHost {

    public static String getHost(int hostType) {
        String host = "";
        switch (hostType) {
            case 1:
                host = "https://fe-api-zdt011-staging.zdt6.com";
                break;
        }
        return host;
    }
}
