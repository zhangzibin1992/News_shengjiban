package com.android.app.tools;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import zhuoxinzhiqu.news2.R;


/**
 * Created by 张梓彬 on 2016/10/26.
 */
public class MyActionBar extends LinearLayout {
    private ImageView imv_left,imv_right;
    private TextView tv_title;


    public MyActionBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.actionbar,this);
        imv_left = (ImageView) findViewById(R.id.imv_left);
        imv_right = (ImageView) findViewById(R.id.imv_right);
        tv_title = (TextView) findViewById(R.id.tv_title);
    }



    public void setActionBar(String title,int imageLeft,int imageRight,OnClickListener listener){
        tv_title.setText(title);
        if (imageLeft==-1){
            imv_left.setVisibility(View.INVISIBLE);
        }else{
            imv_left.setImageResource(imageLeft);
            imv_left.setOnClickListener(listener);
        }

        if (imageRight==-1){
            imv_right.setVisibility(View.INVISIBLE);
        }else{
            imv_right.setImageResource(imageRight);
            imv_right.setOnClickListener(listener);
        }


    }
}
