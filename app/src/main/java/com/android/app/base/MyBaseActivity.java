package com.android.app.base;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.tools.LogUtil;

import zhuoxinzhiqu.news2.R;


/**
 * @author zhangzibin
 * Created by zhangzibin on 2016/10/19.
 */
public class MyBaseActivity extends FragmentActivity implements View.OnClickListener{
    protected int screen_w,screen_h;
    private Toast toast;
    public Dialog dialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        screen_w=getWindowManager().getDefaultDisplay().getWidth();
        screen_h=getWindowManager().getDefaultDisplay().getHeight();
        LogUtil.d(getClass().getSimpleName()+"onCreate()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.d(getClass().getSimpleName()+"onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.d(getClass().getSimpleName()+"onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        LogUtil.d(getClass().getSimpleName()+"onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        LogUtil.d(getClass().getSimpleName()+"onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.d(getClass().getSimpleName()+"onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.d(getClass().getSimpleName()+"onDestroy()");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.d( getClass().getSimpleName()+"onSaveInstanceState() ") ;
    }
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        LogUtil.d( getClass().getSimpleName()+"onRestoreInstanceState() ") ;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LogUtil.d( getClass().getSimpleName()+"onConfigurationChanged() ") ;
    }

    /**页面跳转*/
    public void jumpActivity(Class<?> cls){
        Intent intent = new Intent();
        intent.setClass(this,cls);
        startActivity(intent);
    }

    /**携带数据跳转*/
    public void jumpActivityWithData(Class<?> cls,Bundle bundle){
        Intent intent = new Intent(this,cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    /**携带Bundle，Uri数据跳转*/
    public void jumpActivityWithDataAndUri(Class<?> cls, Bundle bundle, Uri uri){
        Intent intent = new Intent(this,cls);
        if(bundle ==null ){
            intent.putExtras(bundle);
        }
        if (uri!=null){
            intent.setData(uri);
        }
        startActivity(intent);
    }

    /**带动画跳转页面*/
    public void jumpActivityWithAnim(Class<?> cls,int SecondAnim,int FirstAnim ){
        Intent intent = new Intent();
        intent.setClass(this,cls);
        startActivity(intent);
        overridePendingTransition(SecondAnim,FirstAnim);
    }




    /**设置监听，子类需重写该方法*/
    public void onClick(View v){

    }

    public void showToast(int resId){
        showToast(getString(resId));
    }
    public void showToast(String msg){
        if(toast==null)
            toast=Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setText(msg);
        toast.show();
    }

    public void openActivity(Class<?> pClass){
        openActivity(pClass, null, null);
    }
    public void openActivity(Class<?> pClass,Bundle bundle){
        openActivity(pClass, bundle, null);
    }

    public void openActivity(Class<?> pClass,Bundle bundle,Uri uri){
        Intent intent=new Intent(this, pClass);
        if(bundle!=null)
            intent.putExtras(bundle);
        if(uri!=null)
            intent.setData(uri);
        startActivity(intent);
        //增加动画=======
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_bottom_out);
    }

    public void openActivity(String action){
        openActivity(action, null, null);
    }
    public void openActivity(String action,Bundle bundle){
        openActivity(action, bundle, null);
    }
    public void openActivity(String action,Bundle bundle,Uri uri){
        Intent intent=new Intent(action);
        if(bundle!=null)
            intent.putExtras(bundle);
        if(uri!=null)
            intent.setData(uri);
        startActivity(intent);
        //增加动画
        overridePendingTransition(R.anim.anim_activity_right_in, R.anim.anim_activity_bottom_out);
    }

    public void myFinish() {
        super.finish();
        overridePendingTransition(R.anim.anim_activity_bottom_in, R.anim.anim_activity_right_out);
    }

    /**
     * @Title: showLoadingDialog
     * @Description: 显示一个等待对话框
     * @param msg
     *            消息
     * @param cancelable
     *            是否可取消
     * @return 返回Dialog这个对象
     *
     * @author hj
     */
    public void showLoadingDialog(Context context, String msg, boolean cancelable) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.dialog_loading, null);// 得到加载view
        LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局
        //自定义图片
        ImageView iv_img = (ImageView) v.findViewById(R.id.iv_dialogloading_img);
        // 提示文字
        TextView tv_msg = (TextView) v.findViewById(R.id.tv_dialogloading_msg);
        // 加载动画
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.loading_animation);
        // 使用ImageView显示动画
        iv_img.startAnimation(animation);
        if(null != msg) {
            // 设置加载信息
            tv_msg.setText(msg);
        }
        // 创建自定义样式dialog
        dialog = new Dialog(context, R.style.loading_dialog);
        // 不可以用“返回键”取消
        dialog.setCancelable(cancelable);
        // 设置布局
        dialog.setContentView(layout, new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        //显示dialog
        dialog.show();
    }

    /**
     * @Title: cancelDialog
     * @Description: 取消dialog显示
     * @author hj
     */
    public void cancelDialog() {
        if(null != dialog) {
            dialog.dismiss();
        }
    }



}


