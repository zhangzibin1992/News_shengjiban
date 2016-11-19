package com.android.app.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


public class LeadImgAdapter extends PagerAdapter {
    private List<View> mlist;
    public LeadImgAdapter(List<View> list){
        this.mlist=list;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view== (View) object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mlist.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=mlist.get(position);
        container.addView(view);
        return view;
    }
}
