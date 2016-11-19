package com.android.app.biz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.app.entity.BaseEntity;
import com.android.app.entity.Register;
import com.android.app.parser.ParserUser;
import com.android.app.tools.CommonUtil;
import com.android.app.tools.SharedPreferenceUtils;
import com.android.app.tools.UserManager;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;
import zhuoxinzhiqu.news2.HomeActivity;
import zhuoxinzhiqu.news2.R;
import zhuoxinzhiqu.news2.UserActivity;

/**
 * Created by 张梓彬 on 2016/11/9.
 */
public class FragmentDenglu extends Fragment implements View.OnClickListener{
    private View view;
    private EditText edt_user,edt_password;
    private Button btn_zhuce,btn_wangji,btn_denglu;
    private UserManager userManger;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_denglu,null);
        findId();
        return view;
    }

    private void findId() {
        edt_user = (EditText) view.findViewById(R.id.edt_user);
        edt_password = (EditText) view.findViewById(R.id.edt_password);
        btn_zhuce = (Button) view.findViewById(R.id.btn_zhuce);
        btn_wangji = (Button) view.findViewById(R.id.btn_wangji);
        btn_denglu = (Button) view.findViewById(R.id.btn_denglu);

        setAction();
    }

    private void setAction() {
        btn_zhuce.setOnClickListener(this);
        btn_wangji.setOnClickListener(this);
        btn_denglu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_zhuce:
                ((HomeActivity)getActivity()).showZhuCe();
                break;
            case R.id.btn_wangji:
                ((HomeActivity)getActivity()).showZhaohui();
                break;
            case R.id.btn_denglu:
                String nicheng = edt_user.getText().toString().trim();
                String mima = edt_password.getText().toString().trim();

                if (nicheng==null||nicheng.equals("")){
                    Toast.makeText(getActivity(),"昵称不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mima.length()<6||mima.length()>22){
                    Toast.makeText(getActivity(),"密码需在6到16位字符之间",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (userManger==null){
                    userManger = UserManager.getInstance(getActivity());
                }
                userManger.login(getActivity(),listener,errorListener, CommonUtil.VERSION_CODE+"",nicheng,mima,0+"");

                break;
        }

    }

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity(), "登陆异常", Toast.LENGTH_SHORT).show();
        }
    };

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            BaseEntity<Register> entity = ParserUser.parserRegister(response);
            int status = Integer.parseInt(entity.getStatus());
            String result = "";
            if(status==0){
                result = "登陆成功！";
                SharedPreferenceUtils.saveRegister(getActivity(),entity);

                startActivity(new Intent(getActivity(), UserActivity.class));
            }else if(status==-3){
                result = "用户或密码错误";

            }else{
                result = "登陆失败";
            }

            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();
        }
    };
}
