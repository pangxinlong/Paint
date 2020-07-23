package com.example.pangxinlong.paint;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.pangxinlong.paint.recycle.RecyclerActivity;

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
        dataList.add(ViewTag.CANVAS_BUBBLE);
        dataList.add(ViewTag.SVG_TAIWAN_MAP);
        dataList.add(ViewTag.RECYCLE_VIEW);
    }

    private void setListener() {
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (dataList.get(position)) {
                    case ViewTag.PAINT_MAGNIFIER:
                    case ViewTag.PAINT_WATER:
                    case ViewTag.PAINT_RADAR:
                    case ViewTag.PAINT_TEXT:
                    case ViewTag.PROGRESSBAR:
                    case ViewTag.PAINT_ERASER:
                    case ViewTag.PAINT_SHAVECARD:
                        PaintActivity.start(MainActivity.this, dataList.get(position));
                        break;
                    case ViewTag.CANVAS_SEARCH:
                    case ViewTag.CANVAS_CLIP:
                    case ViewTag.CANVAS_BUBBLE:
                    case ViewTag.SVG_TAIWAN_MAP:
                        CanvasActivity.start(MainActivity.this, dataList.get(position));
                        break;
                    case ViewTag.RECYCLE_VIEW:
                        RecyclerActivity.start(MainActivity.this);
                        break;
                }
            }
        });
    }
}
