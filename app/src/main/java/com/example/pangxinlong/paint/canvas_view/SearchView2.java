package com.example.pangxinlong.paint.canvas_view;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;

import static java.lang.Math.PI;

/**
 * Created by pangxinlong on 2017/9/5.
 */

public class SearchView2 extends View {

    private Paint mPaint;

    private int radius = 40;//半径

    private int loadingRadius = 60;//半径

    private float progress;//动画进度

    private float loadingProgress;//loading动画进度

    private Path mPath;

    private PathMeasure mPathMeasure;

    private static final int STEP_ONE = 1;

    private static final int STEP_TWO = 2;

    private int status;

    float length;

    public SearchView2(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        setLayerType(LAYER_TYPE_HARDWARE, null);

        mPath = new Path();
        mPathMeasure = new PathMeasure();
        status = STEP_ONE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        if (status == STEP_ONE) {
            //圆形
            if (progress < 0.5) {
                float changeProgress = 2 * progress;
                RectF rectF = new RectF(getWidth() / 2f - radius, getHeight() / 2f - radius,
                        getWidth() / 2f + radius,
                        getHeight() / 2f + radius);
                canvas.drawArc(rectF, 45 + changeProgress * 360f, 360f - changeProgress * 360f,
                        false,
                        mPaint);
            } else {
                //圆形尾巴
                float changeProgress = 2 * progress - 1;
                float offset = (float) (Math.cos(PI / 4f) * radius);
                canvas.drawLine(getWidth() / 2f + offset, getHeight() / 2f + offset,
                        getWidth() / 2f + offset + radius * (1 - changeProgress),
                        getHeight() / 2f + offset + radius * (1 - changeProgress), mPaint);
            }
        } else {
            //loading

            mPath.addCircle(getWidth() / 2, getHeight() / 2, loadingRadius, Path.Direction.CW);
            mPathMeasure.setPath(mPath, true);
            length = mPathMeasure.getLength();
            Path path = new Path();
            float start = loadingProgress * length;
//            start+=length/8f;//起始点从45度开始
//            if (start > length) {
//                start -= length;
//            }
            Log.e("pxl===start", start + "");
//            if (start <= length/8f-20 || start > length/8f) {
            boolean b = mPathMeasure.getSegment(start, start + 20, path, true);
            if (b) {
                canvas.drawPath(path, mPaint);
            }
        }
//        }
    }

    ValueAnimator mValueAnimator;

    ValueAnimator mValueAnimator2;

    public void startAnim() {
        mValueAnimator = new ValueAnimator();

        mValueAnimator.setFloatValues(0, 1);
        mValueAnimator.setDuration(1500);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();

        mValueAnimator2 = ValueAnimator.ofFloat(0, 1);
        mValueAnimator2.setDuration(2000);
        mValueAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator2.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator2.setInterpolator(new AccelerateDecelerateInterpolator());
        mValueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                loadingProgress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        mValueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                status = STEP_TWO;
                mValueAnimator2.start();

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    public void cancelAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
        if (mValueAnimator2 != null) {
            mValueAnimator2.cancel();
        }
    }
}
