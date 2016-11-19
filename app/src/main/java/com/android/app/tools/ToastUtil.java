package com.android.app.tools;

import android.content.Context;
import android.widget.Toast;

/**
 * @author zhangzibin
 * Created by zhangzibin on 2016/10/19.
 */
public class ToastUtil {
    private static boolean isRunning = true;
    public static void startToast(Context context, CharSequence text){
        if (isRunning){
            Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
        }
    }
}
