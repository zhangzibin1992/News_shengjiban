package com.android.app.biz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.app.entity.BaseEntity;
import com.android.app.entity.ToEmail;
import com.android.app.parser.ParserUser;
import com.android.app.tools.CommonUtil;
import com.android.app.tools.UserManager;

import edu.feicui.mnewsupdate.volley.Response;
import edu.feicui.mnewsupdate.volley.VolleyError;
import zhuoxinzhiqu.news2.HomeActivity;
import zhuoxinzhiqu.news2.R;

/**
 * Created by 张梓彬 on 2016/11/10.
 */
public class FragmentZhaohui extends Fragment implements View.OnClickListener{
    private View view;
    private EditText edt_youxiang2;
    private Button btn_zhaohui;
    private UserManager userManager;



    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_wangji,null);
        findId();
        return view;
    }

    private void findId() {
        edt_youxiang2 = (EditText) view.findViewById(R.id.edt_youxiang2);
        btn_zhaohui = (Button) view.findViewById(R.id.btn_zhaohui);
        setAction();
    }

    private void setAction() {
        btn_zhaohui.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_zhaohui:
                onListener();
                break;
        }
    }

    private void onListener(){
        String email = edt_youxiang2.getText().toString().trim();
        if(!CommonUtil.verifyEmail(email)){
            Toast.makeText(getActivity(), "邮箱格式不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        if(userManager==null){
            userManager = UserManager.getInstance(getActivity());
        }
        userManager.forgetPass(getActivity(),listener,errorListener, CommonUtil.VERSION_CODE+"",email);
    }

    private Response.Listener<String> listener = new Response.Listener<String>() {
        @Override
        public void onResponse(String response) {
            BaseEntity<ToEmail> entity = ParserUser.parserGetPwd(response);
            int status = Integer.parseInt(entity.getStatus());
            ToEmail data = entity.getData();
            if(status==0){
                if(data.getResult()==0){
                    ((HomeActivity)getActivity()).showFragmentMain();
                }else if(data.getResult()==-1){
                    edt_youxiang2.requestFocus();
                }else if(data.getResult()==-2){
                    edt_youxiang2.requestFocus();
                }
                Toast.makeText(getActivity(), data.getExplain(), Toast.LENGTH_SHORT).show();
            }
        }
    };

    private Response.ErrorListener errorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getActivity(),"网络连接错误",Toast.LENGTH_SHORT).show();
        }
    };


}
