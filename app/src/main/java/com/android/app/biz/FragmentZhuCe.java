package com.android.app.biz;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
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
public class FragmentZhuCe extends Fragment implements View.OnClickListener{
    private View view;
    private EditText edt_email,edt_nicheng,edt_mima;
    private Button btn_lijizhuce;
    private CheckBox cb_sel;
    private UserManager userManager;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_zhuce,null);
        findId();
        return view;
    }

    private void findId() {
        edt_email = (EditText) view.findViewById(R.id.edt_email);
        edt_nicheng = (EditText) view.findViewById(R.id.edt_nicheng);
        edt_mima = (EditText) view.findViewById(R.id.edt_mima);
        btn_lijizhuce = (Button) view.findViewById(R.id.btn_lijizhuce);
        cb_sel = (CheckBox) view.findViewById(R.id.cb_sel);
        sweAction();
    }

    private void sweAction() {
        btn_lijizhuce.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_lijizhuce:
                listener();
                break;
        }
    }

    private void listener(){
        if(!cb_sel.isChecked()){
            Toast.makeText(getActivity(), "没有同意条款！", Toast.LENGTH_SHORT).show();
            return;
        }
        String email = edt_email.getText().toString().trim();
        String name = edt_nicheng.getText().toString().trim();
        String pwd = edt_mima.getText().toString().trim();
        if(!CommonUtil.verifyEmail(email)){
            Toast.makeText(getActivity(), "请输入正确的邮箱格式", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getActivity(), "用户名不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(pwd)){
            Toast.makeText(getActivity(), "密码不能为空", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!CommonUtil.verifyPassword(pwd)){
            Toast.makeText(getActivity(), "请输入6-22位包含数字和字母的密码", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userManager==null){
            userManager = UserManager.getInstance(getActivity());
        }
        userManager.register(getActivity(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                BaseEntity<Register> entity = ParserUser.parserRegister(response);
                Register data = entity.getData();
                int status = Integer.parseInt(entity.getStatus());
                if(status==0){
                    String result = data.getResult();
                    String explain = data.getExplain();
                    if(data.getResult().equals("0")){
                        //登录成功
                        SharedPreferenceUtils.saveRegister(getActivity(),entity);
                        startActivity(new Intent(getActivity(),UserActivity.class));
                        getActivity().overridePendingTransition(R.anim.anim_activity_right_in,R.anim.anim_activity_bottom_out);
                        ((HomeActivity)getActivity()).showFragmentMain();
                    }
                    Toast.makeText(getActivity(), explain, Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        },CommonUtil.VERSION_CODE+"",name,email,pwd);
    }

}
