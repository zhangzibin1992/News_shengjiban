package com.android.app.biz;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import zhuoxinzhiqu.news2.HomeActivity;
import zhuoxinzhiqu.news2.R;

/**
 * Created by 张梓彬 on 2016/11/1.
 */
public class FragmentMenu extends Fragment {
    private View view;
    private RelativeLayout[] layouts = new RelativeLayout[5];
    public static SlidingMenu slidingMenu;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_menu_left,container,false);
        findId();
        return view;
    }

    private void findId() {
        layouts[0] = (RelativeLayout) view.findViewById(R.id.lay_news);
        layouts[1] = (RelativeLayout) view.findViewById(R.id.lay_shoucang);
        layouts[2] = (RelativeLayout) view.findViewById(R.id.lay_local);
        layouts[3] = (RelativeLayout) view.findViewById(R.id.lay_gentie);
        layouts[4] = (RelativeLayout) view.findViewById(R.id.lay_tupian);

        setAction();
    }

    private void setAction() {
        layouts[0].setOnClickListener(listener);
        layouts[1].setOnClickListener(listener);
        layouts[2].setOnClickListener(listener);
        layouts[3].setOnClickListener(listener);
        layouts[4].setOnClickListener(listener);
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.lay_news:
                    layouts[0].setBackgroundColor(0x3c85555);
                    ((HomeActivity) getActivity()).showFragmentMain();
                    break;

                case R.id.lay_shoucang:
                    layouts[1].setBackgroundColor(0x3c85555);
                    ((HomeActivity) getActivity()).showFragmentShoucang();
                    break;

                case R.id.lay_local:
                    layouts[2].setBackgroundColor(0x3c85555);
                    break;

                case R.id.lay_gentie:
                    layouts[3].setBackgroundColor(0x3c85555);
                    break;

                case R.id.lay_tupian:
                    layouts[4].setBackgroundColor(0x3c85555);
                    break;
            }

        }
    };




}
