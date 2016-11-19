package com.android.app.biz;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.app.adapter.NewsAdapter;
import com.android.app.entity.News;
import com.android.app.tools.DBManager;

import java.util.ArrayList;

import zhuoxinzhiqu.news2.R;
import zhuoxinzhiqu.news2.ShowActivity;

/**
 * Created by 张梓彬 on 2016/11/7.
 */
public class FragmentShoucang extends Fragment {
    private View view;
    private ListView listView;
    private NewsAdapter adapter;
    private DBManager dbManage;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shoucang,null);
        findId();
        return view;
    }

    private void findId() {
        listView = (ListView) view.findViewById(R.id.lv_shoucang);
        adapter = new NewsAdapter(getActivity(),listView);
        listView.setAdapter(adapter);

        setAction();
    }

    private void setAction() {
        listView.setOnItemClickListener(listener);
        loadLoveNews();
    }

    private AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            News news = (News) parent.getItemAtPosition(position);
            Intent intent=new Intent(getActivity(), ShowActivity.class);
            intent.putExtra("newsitem", news);
            getActivity().startActivity(intent);
        }
    };

    private void loadLoveNews() {
        dbManage = DBManager.getInstance(getActivity());
        ArrayList<News> data=dbManage.queryLoveNews();
        adapter.appendData(data, true);
    }


}
