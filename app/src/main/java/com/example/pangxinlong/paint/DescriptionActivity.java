package com.example.pangxinlong.paint;

import com.example.pangxinlong.paint.paint_view.PaintEraserView;
import com.example.pangxinlong.paint.paint_view.PaintMagnifierView;
import com.example.pangxinlong.paint.paint_view.PaintRadarView;
import com.example.pangxinlong.paint.paint_view.PaintShaveCardView;
import com.example.pangxinlong.paint.paint_view.PaintText;
import com.example.pangxinlong.paint.paint_view.PaintWaterView;
import com.example.pangxinlong.paint.paint_view.ProgressBar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

/**
 * Created by pangxinlong on 2017/8/28.
 */

public class DescriptionActivity extends Activity {

    private PaintMagnifierView mPaintMagnifierView;

    private PaintText mPaintText;

    private PaintRadarView mPaintRadarView;

    private PaintWaterView mPaintWaterView;

    private ProgressBar mProgressBar;

    private PaintEraserView mPaintEraserView;

    private PaintShaveCardView mPaintShaveCardView;

    private int tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        initData();
        initView();
    }


    private void initData() {
        Intent intent = getIntent();
        tag = intent.getIntExtra("tag", 0);

    }

    private void initView() {
        mPaintMagnifierView = (PaintMagnifierView) findViewById(R.id.paint_magnifier);
        mPaintText = (PaintText) findViewById(R.id.paint_text);
        mPaintRadarView = (PaintRadarView) findViewById(R.id.paint_radar);
        mPaintWaterView = (PaintWaterView) findViewById(R.id.paint_water);
        mProgressBar = (ProgressBar) findViewById(R.id.paint_pro);
        mPaintEraserView = (PaintEraserView) findViewById(R.id.paint_eraser);
        mPaintShaveCardView = (PaintShaveCardView) findViewById(R.id.paint_shave_card);
        switch (tag) {
            case 0:
                mPaintMagnifierView.setVisibility(View.VISIBLE);
                break;
            case 1:
                mPaintWaterView.setVisibility(View.VISIBLE);
                break;
            case 2:
                mPaintRadarView.setVisibility(View.VISIBLE);
                break;
            case 3:
                mPaintText.setVisibility(View.VISIBLE);
                break;
            case 4:
                mProgressBar.setVisibility(View.VISIBLE);
                mProgressBar.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                int progress = 0;
                                while (progress <= 100) {
                                    mProgressBar.setProgress(progress);
                                    progress += 2;
                                    try {
                                        Thread.sleep(50);
                                    } catch (InterruptedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }).start();
                    }
                });
                break;
            case 5:
                mPaintEraserView.setVisibility(View.VISIBLE);
                break;
            case 6:
                mPaintShaveCardView.setVisibility(View.VISIBLE);
                break;


        }
    }

    public static void start(Context context, int position) {
        Intent intent = new Intent();
        intent.putExtra("tag", position);
        intent.setClass(context, DescriptionActivity.class);
        context.startActivity(intent);
    }
}
