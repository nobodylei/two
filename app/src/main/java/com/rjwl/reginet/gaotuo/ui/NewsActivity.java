package com.rjwl.reginet.gaotuo.ui;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.rjwl.reginet.gaotuo.R;
import com.rjwl.reginet.gaotuo.entity.Notice;
import com.rjwl.reginet.gaotuo.utils.BaseActivity;
import com.rjwl.reginet.gaotuo.utils.SaveOrDeletePrefrence;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/28.
 */

public class NewsActivity extends BaseActivity {
    private SimpleDraweeView simpleDraweeView;
    private ListView lvNews;
    private ImageView backNews;
    private ArrayAdapter<String> adapter;
    private TextView tvNews;

    private List<String> titles;
    private List<String> companyIds;
    private List<String> images;
    private List<String> contents;
    private List<Notice> noticeList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        initView();
        initData();
    }

    private void initView() {

        simpleDraweeView = findViewById(R.id.img_news);
        lvNews = findViewById(R.id.lv_news);
        backNews = findViewById(R.id.back_news);
        tvNews = findViewById(R.id.tv_news);

        initList();

        backNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initList() {
        //暂时
        noticeList = new ArrayList<>();
        titles = new ArrayList<>();
        contents = new ArrayList<>();
        images = new ArrayList<>();
        companyIds = new ArrayList<>();
        //titles = new ArrayList<>();
        List<String> comList = SaveOrDeletePrefrence.getList(getApplicationContext(), "companyId");
        if (comList != null) {
            companyIds.addAll(comList);
        } else {
            return;
        }
        for (int i = 0; i < companyIds.size(); i++) {
            Notice notice;
            titles = SaveOrDeletePrefrence.getList(getApplicationContext(), companyIds.get(i) + "title");
            contents = SaveOrDeletePrefrence.getList(getApplicationContext(), companyIds.get(i) + "content");
            images = SaveOrDeletePrefrence.getList(getApplicationContext(), companyIds.get(i) + "path");
            for (int j = 0; j < titles.size(); j++) {
                notice = new Notice(Integer.parseInt(companyIds.get(i)), contents.get(j), images.get(j), titles.get(j));
                noticeList.add(notice);
            }
        }
    }

    private void initData() {
        /*list.add("今天停水！！！");
        list.add("明天停电！！！");*/
        //list.add("禁止跳楼！！！");

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
        lvNews.setAdapter(adapter);
        lvNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("news", "公告点击事件：" + titles.get(position) + " ; " + R.id.lv_news + " ; " + position);
                //Toast.makeText(NewsActivity.this, "消息不存在", Toast.LENGTH_SHORT).show();
                tvNews.setVisibility(View.VISIBLE);
                tvNews.setText(contents.get(position));
                lvNews.setVisibility(View.GONE);
                Uri imageUri = Uri.parse(images.get(position));
                simpleDraweeView.setImageURI(imageUri);
                simpleDraweeView.setVisibility(View.VISIBLE);
            }
        });


    }

    @Override
    public void onBackPressed() {
        if (tvNews.getVisibility() == View.VISIBLE) {
            tvNews.setVisibility(View.GONE);
            simpleDraweeView.setVisibility(View.GONE);
            lvNews.setVisibility(View.VISIBLE);
            return;
        }
        super.onBackPressed();
        finish();
    }
}
