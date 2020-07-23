package com.example.pangxinlong.paint;

import com.example.pangxinlong.paint.canvas_view.BubbleView;
import com.example.pangxinlong.paint.canvas_view.ClipView;
import com.example.pangxinlong.paint.canvas_view.SearchView;
import com.example.pangxinlong.paint.canvas_view.SearchView2;
import com.example.pangxinlong.paint.svg_view.TaiwanMapView;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * Created by pangxinlong on 2017/9/1.
 */

public class CanvasActivity extends AppCompatActivity {

    private SearchView mSearchView;

    private SearchView2 mSearchView2;

    private ClipView mClipView;

    private ImageView mImageView;

    private ClipView clipView;

    private BubbleView mBubbleView;

    private TaiwanMapView mTaiwanMapView;

    private String tag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        initData();
        initView();
    }

    private void initData() {
        Intent intent = getIntent();
        tag = intent.getStringExtra("tag");

    }

    private void initView() {
        mSearchView = (SearchView) findViewById(R.id.canvas_search);
        mSearchView2 = (SearchView2) findViewById(R.id.canvas_search2);
//        mClipView = (ClipView) findViewById(R.id.canvas_clip);
        mImageView = (ImageView) findViewById(R.id.iv_clip);
        mBubbleView = (BubbleView) findViewById(R.id.canvas_bubble);
        mTaiwanMapView = (TaiwanMapView) findViewById(R.id.svg_taiwan_map);
        switch (tag) {
            case ViewTag.CANVAS_SEARCH:
                mSearchView.setVisibility(View.VISIBLE);
                mSearchView.startAnim();
                mSearchView2.setVisibility(View.VISIBLE);
                mSearchView2.startAnim();
                break;
            case ViewTag.CANVAS_CLIP:
//                mClipView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);
                clipView = new ClipView(getResources().getDrawable(R.mipmap.avft),
                        getResources().getDrawable(R.mipmap.avft_active));
                mImageView.setImageDrawable(clipView);
                startClipAnim();
                break;
            case ViewTag.CANVAS_BUBBLE:
                mBubbleView.setVisibility(View.VISIBLE);
                break;
            case ViewTag.SVG_TAIWAN_MAP:
                mTaiwanMapView.setVisibility(View.VISIBLE);
                break;
        }
    }

    ValueAnimator valueAnimator;

    private void startClipAnim() {
        valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, 10000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int animValue = (int) animation.getAnimatedValue();
                mImageView.setImageLevel(animValue);
            }
        });
        valueAnimator.start();
    }

    private void cancelClipAnim() {
        if (valueAnimator != null) {
            valueAnimator.cancel();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSearchView.cancelAnim();
        mSearchView2.cancelAnim();
        cancelClipAnim();
    }

    public static void start(Context context, String position) {
        Intent intent = new Intent();
        intent.putExtra("tag", position);
        intent.setClass(context, CanvasActivity.class);
        context.startActivity(intent);
    }
}
