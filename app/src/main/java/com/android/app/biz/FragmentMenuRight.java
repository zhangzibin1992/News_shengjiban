package com.android.app.biz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import zhuoxinzhiqu.news2.HomeActivity;
import zhuoxinzhiqu.news2.R;

/**
 * Created by 张梓彬 on 2016/11/1.
 */
public class FragmentMenuRight extends Fragment implements View.OnClickListener{
    private View view;
    private TextView tv_denglu;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_menu_right,container,false);
        findId();
        return view;
    }

    private void findId() {
        tv_denglu = (TextView) view.findViewById(R.id.tv_denglu);

        setAction();
    }

    private void setAction() {
        tv_denglu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_denglu:
                ((HomeActivity)getActivity()).showDenglu();
                break;
        }
    }
}
