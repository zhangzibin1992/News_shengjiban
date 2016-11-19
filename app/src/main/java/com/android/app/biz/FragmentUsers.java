package com.android.app.biz;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.app.adapter.LoginLogAdapter;
import com.android.app.entity.BaseEntity;
import com.android.app.entity.Loginlog;
import com.android.app.entity.Register;
import com.android.app.entity.User;
import com.android.app.parser.ParserUser;
import com.android.app.tools.CommonUtil;
import com.android.app.tools.LoadImage;
import com.android.app.tools.MyActionBar;
import com.android.app.tools.SharedPreferenceUtils;
import com.android.app.tools.SystemUtils;
import com.android.app.tools.UserManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;
import zhuoxinzhiqu.news2.HomeActivity;
import zhuoxinzhiqu.news2.R;

/**
 * Created by 张梓彬 on 2016/11/9.
 */
public class FragmentUsers extends Fragment implements View.OnClickListener{
    private View view;
    private MyActionBar myActionBar;
    private ImageView img_user2;
    private TextView tv_dengluming,tv_jifeng,tv_num_gentie;
    private Button btn_tuichu;
    private ListView  lv_user;
    private String token;
    private String localpath;
    private LinearLayout photo_take,photo_sel;
    private PopupWindow popupWindow;
    private LinearLayout layout;
    private Bitmap alterBitmap,bitmap;
    private SharedPreferences sp;
    private LoadImage loadImage;
    private LoginLogAdapter adapter;
    private File file;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_users,null);
        findId();
        return view;
    }

    private void findId() {
        myActionBar = (MyActionBar) view.findViewById(R.id.actionbar3);
        img_user2 = (ImageView) view.findViewById(R.id.img_user2);
        tv_dengluming = (TextView) view.findViewById(R.id.tv_dengluming);
        tv_jifeng = (TextView) view.findViewById(R.id.tv_jifeng);
        btn_tuichu = (Button) view.findViewById(R.id.btn_tuichu);
        lv_user = (ListView) view.findViewById(R.id.lv_user);
        tv_num_gentie = (TextView) view.findViewById(R.id.tv_num_gentie);
        layout = (LinearLayout) view.findViewById(R.id.layout);

        setAction();
        initData();

        sp = getActivity().getSharedPreferences("userinfo", getActivity().MODE_PRIVATE);
        String username = sp.getString("username", "游客");
        tv_dengluming.setText(username);
        localpath = sp.getString("localpath", "");
        if(alterBitmap!=null){
            img_user2.setImageBitmap(bitmap);
        }else
        if(!localpath.equals("")){
            bitmap = BitmapFactory.decodeFile(localpath);
            img_user2.setImageBitmap(bitmap);
        }
        popWindows();
    }

    private void setAction() {
        adapter = new LoginLogAdapter(getActivity());
        lv_user.setAdapter(adapter);
        loadImage = new LoadImage(getActivity(),loadImageListener);
        btn_tuichu.setOnClickListener(this);
        myActionBar.setActionBar("我的账户",R.drawable.back,-1,listener);
        img_user2.setOnClickListener(this);
    }

    private LoadImage.ImageLoadListener loadImageListener = new LoadImage.ImageLoadListener() {
        @Override
        public void imageLoadOK(Bitmap bitmap, String url) {
            img_user2.setImageBitmap(bitmap);
        }
    };

    private void popWindows(){
        View contentView = View.inflate(getActivity(), R.layout.item_pop_selectpic, null);
        popupWindow = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        photo_take = (LinearLayout) contentView.findViewById(R.id.photo_take);
        photo_sel = (LinearLayout) contentView.findViewById(R.id.photo_sel);
        photo_sel.setOnClickListener(this);
        photo_take.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_tuichu:
                SharedPreferenceUtils.clearUser(getActivity());
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
                break;
            case R.id.img_user2:
                popupWindow.showAtLocation(layout, Gravity.BOTTOM,0,0);
                break;
            case R.id.photo_sel:
                popupWindow.dismiss();
                gallary();
                break;
            case R.id.photo_take:
                popupWindow.dismiss();
                takePhoto();
                break;
        }
    }


    private void takePhoto(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,100);
    }

    private void save(Bitmap bitmap){
        if(bitmap==null){
            return;
        }
        roundPic();
        img_user2.setImageBitmap(alterBitmap);
        File rootDir = Environment.getExternalStorageDirectory();
        File dir = new File(rootDir,"azynews");
        dir.mkdirs();
        file = new File(dir,"userpic.jpg");
        try {
            OutputStream os = new FileOutputStream(file);
            if(alterBitmap.compress(Bitmap.CompressFormat.JPEG,100,os)){
                UserManager.getInstance(getActivity()).changePhoto(getActivity(),token,file,successListener2,errorListener);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("localpath", file.getAbsolutePath());
                editor.commit();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Response.Listener<String> successListener2 = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            BaseEntity<Register> entity = ParserUser.parserRegister(response);
            if(entity.getData().getResult().equals("0")){
                img_user2.setImageBitmap(bitmap);
            }
        }
    };

    private void roundPic() {

        Bitmap backBp = BitmapFactory.decodeResource(getResources(),R.drawable.userbg);
        alterBitmap = Bitmap.createBitmap(backBp.getWidth(), backBp.getHeight(), backBp.getConfig());
        Canvas canvas = new Canvas(alterBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        //画背景
        canvas.drawBitmap(backBp,new Matrix(),paint);
        //画用户头像图片
        //模式是SRC_IN的时候有一个难看的黑框
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OVER));
        bitmap = Bitmap.createScaledBitmap(bitmap,backBp.getWidth(),backBp.getHeight(),true);
        canvas.drawBitmap(bitmap,new Matrix(),paint);
    }

    public void gallary(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent,300);
    }

    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
//        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 80);
        intent.putExtra("outputY", 80);

        intent.putExtra("outputFormat", "JPEG");// 图片格式
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);
        // 开启一个带有返回值的Activity，请求码为400
        startActivityForResult(intent, 400);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            bitmap = (Bitmap) bundle.get("data");
            save(bitmap);

        }else if(requestCode==300){
            if(data!=null){
                Uri uri = data.getData();
                crop(uri);
            }
        }else if(requestCode==400){
            bitmap = data.getParcelableExtra("data");
            save(bitmap);
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.imv_left:

                 getActivity().finish();

                    break;
            }
        }
    };

    private void initData(){
        token = SharedPreferenceUtils.getToken(getActivity());
        UserManager.getInstance(getActivity()).getUserInfo(getActivity(),successListener,errorListener, CommonUtil.VERSION_CODE+"",token, SystemUtils.getIMEI(getActivity()));

    }

    private Response.Listener<String> successListener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            BaseEntity<User> entity = ParserUser.parserUser(response);
            if (Integer.parseInt(entity.getStatus())!=0){
                    return;
            }
            SharedPreferenceUtils.saveUser(getActivity(),entity);
            User user = entity.getData();
            tv_dengluming.setText("登录名:"+user.getUid());
            if(!TextUtils.isEmpty(user.getPortrait())&&localpath.equals("")){
                loadImage.getBitmap(user.getPortrait(),img_user2);
            }
            tv_jifeng.setText("积分:"+user.getIntegration());
            tv_num_gentie.setText("跟帖统计数:"+user.getComnum());
            List<Loginlog> loginlog = user.getLoginlog();
            adapter.appendData(loginlog,true);
            adapter.update();
        }
    };



    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity(),"网络连接异常",Toast.LENGTH_SHORT).show();
        }
    };
}
