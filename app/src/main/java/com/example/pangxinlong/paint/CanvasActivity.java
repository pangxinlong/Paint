package com.example.pangxinlong.paint;

import com.example.pangxinlong.paint.canvas_view.ClipView;
import com.example.pangxinlong.paint.canvas_view.SearchView;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**
 * Created by pangxinlong on 2017/9/1.
 */

public class CanvasActivity extends Activity {

    private SearchView mSearchView;

    private ClipView mClipView;

    private ImageView mImageView;

    private ClipView clipView;

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
//        mClipView = (ClipView) findViewById(R.id.canvas_clip);
        mImageView = (ImageView) findViewById(R.id.iv_clip);
        switch (tag) {
            case ViewTag.CANVAS_SEARCH:
                mSearchView.setVisibility(View.VISIBLE);
                mSearchView.startAnim();
                break;
            case ViewTag.CANVAS_CLIP:
//                mClipView.setVisibility(View.VISIBLE);
                mImageView.setVisibility(View.VISIBLE);
                clipView = new ClipView(getResources().getDrawable(R.mipmap.avft),
                        getResources().getDrawable(R.mipmap.avft_active));
                mImageView.setImageDrawable(clipView);
                initAnim();
                break;
        }
    }

    private void initAnim() {
        ValueAnimator valueAnimator = new ValueAnimator();
        valueAnimator.setIntValues(0, 10000);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.setDuration(5000);
        valueAnimator.setRepeatCount(Integer.MAX_VALUE);
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

    public static void start(Context context, String position) {
        Intent intent = new Intent();
        intent.putExtra("tag", position);
        intent.setClass(context, CanvasActivity.class);
        context.startActivity(intent);
    }
}
