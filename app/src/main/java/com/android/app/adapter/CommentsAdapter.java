package com.android.app.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.app.base.MyBaseAdapter;
import com.android.app.entity.Comment;
import com.android.app.tools.CommonUtil;

import zhuoxinzhiqu.news2.R;

/**
 * Created by 张梓彬 on 2016/11/8.
 */
public class CommentsAdapter extends MyBaseAdapter<Comment>{
    private ListView listView;
    private Bitmap bitmap;
    public CommentsAdapter(Context context,ListView listView) {
        super(context);
        this.listView= listView;
       // bitmap = BitmapFactory.decodeResource(C);
    }

    @Override
    public View getMyView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView==null){
            convertView = inflater.inflate(R.layout.pinglun_list,null);
             holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        Comment comment = getItem(position);
        holder.img_user.setImageResource(R.drawable.gc_leaderboard_default_icon);
        holder.tv_date.setText(comment.getStamp());
        holder.tv_context.setText(comment.getContent());
        holder.tv_user.setText(comment.getUid());

        return convertView;
    }

    class ViewHolder{
        private ImageView img_user;
        private TextView tv_user,tv_date,tv_context;
        public ViewHolder(View view) {
            img_user = (ImageView) view.findViewById(R.id.img_user);
            tv_user = (TextView) view.findViewById(R.id.tv_user);
            tv_date = (TextView) view.findViewById(R.id.tv_date);
            tv_context = (TextView) view.findViewById(R.id.tv_context);
        }
    }
}
