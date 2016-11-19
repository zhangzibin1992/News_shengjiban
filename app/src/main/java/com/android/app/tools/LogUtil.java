package com.android.app.tools;

import android.util.Log;

/**
 * @author zhangzibin
 * Created by zhangzibin on 2016/10/19.
 */
public class LogUtil {
    private static final String TAG = "新闻随意看";
    private static boolean isDebug = true;

    public static void d(String tag,String msg){
        if(isDebug){
            Log.d(tag,msg);
        }
    }

    public static void d(String msg){
        if (isDebug){
            Log.d(LogUtil.TAG,msg);
        }
    }
}
