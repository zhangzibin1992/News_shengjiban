package com.android.app.tools;

import android.content.Context;
import android.content.SharedPreferences;

import com.android.app.entity.BaseEntity;
import com.android.app.entity.Register;
import com.android.app.entity.User;


/**
 * Created by Justin on 2016/6/12.
 */
public class SharedPreferenceUtils {


    /**
     * 获取token
     * @param context
     * @return
     */
    public static String getToken(Context context){
        SharedPreferences sp = context.getSharedPreferences("register", Context.MODE_PRIVATE);
        String token = sp.getString("token", "");
        return token;
    }

    /**
     * 保存注册信息
     * @param context
     * @param entity
     */
    public static void saveRegister(Context context, BaseEntity<Register> entity) {
        Register data = entity.getData();
        SharedPreferences sp = context.getSharedPreferences("register", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("message", entity.getMessage());
        editor.putInt("status", Integer.parseInt(entity.getStatus()));
        editor.putString("result", data.getResult());
        editor.putString("token", data.getToken());
        editor.putString("explain", data.getExplain());
        editor.commit();

    }

    /**
     * 获取用户名和头像信息
     * @param context
     * @return
     */
    public static String[] getUserIconAndName(Context context){
        String[] str = null;
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        String uid = sp.getString("uid", "");
        String portrait = sp.getString("portrait","");
        str = new String[]{uid, portrait};
        return str;

    }

    /**
     * 保存用户信息
     * @param context
     * @param entity
     */
    public static void saveUser(Context context, BaseEntity<User> entity){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        User user = entity.getData();
        editor.putString("uid", user.getUid());
        editor.putString("portrait", user.getPortrait());
        editor.commit();
    }

    /**
     * 清空用户信息
     * @param context
     */
    public static void clearUser(Context context){
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        editor.commit();
    }

    /**
     * 保存用户头像本地路径
     *
     * @param context
     * @param path
     */
    public static void saveUserLocalIcon(Context context, String path) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("imagePath", path);
        editor.commit();
    }

    /**
     * 获取保存的本地头像路径
     *
     * @param context
     * @return
     */
    public static String getUserLocalIcon(Context context) {
        SharedPreferences sp = context.getSharedPreferences("user", Context.MODE_PRIVATE);
        return sp.getString("imagePath", null);
    }





}
