package com.android.app.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.app.base.MyBaseAdapter;
import com.android.app.entity.SubType;

import zhuoxinzhiqu.news2.R;


/**
 * Created by Justin on 2016/6/6.
 */
public class NewsTypeAdapter extends MyBaseAdapter<SubType> {

    private int selectPosition;


    public NewsTypeAdapter(Context context) {
        super(context);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if(convertView==null){
            convertView = inflater.inflate(R.layout.item_list_newstype, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        SubType item = (SubType) getItem(position);
        holder.tv_type.setText(item.getSubgroup());
       /* holder.tv_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finalHolder.tv_type.setTextColor(Color.RED);
            }
        });*/

        //给当前选中的type设置字体颜色为红色，另外没有选中的则设置为黑色
        if(selectPosition==position){
            holder.tv_type.setTextColor(Color.RED);
        }else{
            holder.tv_type.setTextColor(Color.BLACK);
        }

        return convertView;
    }



    public void setSelectPosition(int position){
        this.selectPosition = position;
    }

    class ViewHolder{
        public TextView tv_type;
        public ViewHolder(View view){
            tv_type = (TextView) view.findViewById(R.id.tv_newstype_type);
        }
    }


}
