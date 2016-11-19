package com.android.app.parser;

import com.android.app.entity.BaseEntity;
import com.android.app.entity.News;
import com.android.app.entity.NewsType;
import com.android.app.entity.SubType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;

/**
 * Created by Justin on 2016/6/1.
 */
public class ParserNews {



    public static List<SubType> parserTypeList(String json){
        Gson gson = new Gson();
        BaseEntity<List<NewsType>> entity = gson.fromJson(json, new TypeToken<BaseEntity<List<NewsType>>>() {
        }.getType());

        return entity.getData().get(0).getSubgrp();

    }

    public static List<News> praserNewsList(String json){
        Gson gson = new Gson();
        BaseEntity<List<News>> entity = gson.fromJson(json, new TypeToken<BaseEntity<List<News>>>() {
        }.getType());

        return entity.getData();
    }



}
