package com.android.app.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by 张梓彬 on 2016/10/28.
 */
public class LayoutPagerAdapter extends PagerAdapter{
    private Context context;
    private ArrayList<View> arrayList = new ArrayList<View>();
    public LayoutPagerAdapter(Context context) {
        this.context = context;
    }

    public void addViewToAdapter(View view){
        arrayList.add(view);
    }

    public ArrayList<View> getArrayList() {
        return arrayList;
    }

    public void delAdapter(){
        arrayList.clear();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        View view = arrayList.get(position);
        container.removeView(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = arrayList.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
