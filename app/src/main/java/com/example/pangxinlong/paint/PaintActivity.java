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

public class PaintActivity extends Activity {

    private PaintMagnifierView mPaintMagnifierView;

    private PaintText mPaintText;

    private PaintRadarView mPaintRadarView;

    private PaintWaterView mPaintWaterView;

    private ProgressBar mProgressBar;

    private PaintEraserView mPaintEraserView;

    private PaintShaveCardView mPaintShaveCardView;

    private String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paint);
        initData();
        initView();
    }


    private void initData() {
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

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
            case ViewTag.PAINT_MAGNIFIER:
                mPaintMagnifierView.setVisibility(View.VISIBLE);
                break;
            case ViewTag.PAINT_WATER:
                mPaintWaterView.setVisibility(View.VISIBLE);
                break;
            case ViewTag.PAINT_RADAR:
                mPaintRadarView.setVisibility(View.VISIBLE);
                break;
            case ViewTag.PAINT_TEXT:
                mPaintText.setVisibility(View.VISIBLE);
                break;
            case ViewTag.PROGRESSBAR:
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
            case ViewTag.PAINT_ERASER:
                mPaintEraserView.setVisibility(View.VISIBLE);
                break;
            case ViewTag.PAINT_SHAVECARD:
                mPaintShaveCardView.setVisibility(View.VISIBLE);
                break;


        }
    }

    public static void start(Context context, String tag) {
        Intent intent = new Intent();
        intent.putExtra("tag", tag);
        intent.setClass(context, PaintActivity.class);
        context.startActivity(intent);
    }
}
