package com.example.pangxinlong.paint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private ListView mListView;

    private MyAdapter mMyAdapter;

    private List<String> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        setListener();
    }


    private void initView() {
        mListView = (ListView) findViewById(R.id.lv_paint);
        mMyAdapter = new MyAdapter(this, dataList);
        mListView.setAdapter(mMyAdapter);
    }

    private void initData() {
        dataList = new ArrayList<>();
        dataList.add("放大镜(Shader)");
        dataList.add("水波纹(Shader)");
        dataList.add("雷达(Shader)");
        dataList.add("类跑马灯(Shader)");
        dataList.add("ProgressBar(Shader)");
        dataList.add("橡皮擦(Xfermode)");
        dataList.add("刮刮卡(Xfermode)");
    }

    private void setListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DescriptionActivity.start(MainActivity.this, position);
            }
        });
    }
}
