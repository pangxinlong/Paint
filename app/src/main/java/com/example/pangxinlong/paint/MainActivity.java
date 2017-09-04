package com.example.pangxinlong.paint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
        dataList.add(ViewTag.PAINT_MAGNIFIER);
        dataList.add(ViewTag.PAINT_WATER);
        dataList.add(ViewTag.PAINT_RADAR);
        dataList.add(ViewTag.PAINT_TEXT);
        dataList.add(ViewTag.PROGRESSBAR);
        dataList.add(ViewTag.PAINT_ERASER);
        dataList.add(ViewTag.PAINT_SHAVECARD);
        dataList.add(ViewTag.CANVAS_SEARCH);
        dataList.add(ViewTag.CANVAS_CLIP);
    }

    private void setListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position < 7) {
                    PaintActivity.start(MainActivity.this, dataList.get(position));
                } else {
                    CanvasActivity.start(MainActivity.this, dataList.get(position));
                }
            }
        });
    }
}
