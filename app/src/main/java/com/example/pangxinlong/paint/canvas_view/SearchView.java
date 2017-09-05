package com.example.pangxinlong.paint.canvas_view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static java.lang.Math.PI;

/**
 * Created by pangxinlong on 2017/9/1.
 */

public class SearchView extends View {

    private Paint mPaint;

    private int radius = 40;//半径

    private float progress;//动画进度

    public SearchView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        setLayerType(LAYER_TYPE_HARDWARE, null);
    }

    private int dx = 0;

    private float oldProgress;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.GREEN);
        if (oldProgress < progress) {
            canvas.translate(dx++, 0);
        } else {
            canvas.translate(dx--, 0);
        }

        if (progress < 0.5) {//小于一半
            //圆形 progress范围要从0~0.5扩大到0~1 公式y=2x
            float changeProgress = 2 * progress;
            RectF rectF = new RectF(getWidth() / 2f - radius, getHeight() / 2f - radius,
                    getWidth() / 2f + radius,
                    getHeight() / 2f + radius);
            canvas.drawArc(rectF, 45 + changeProgress * 360f, 360f - changeProgress * 360f, false,
                    mPaint);

            //圆形尾巴
            float offset = (float) (Math.cos(PI / 4f) * radius);
            canvas.drawLine(getWidth() / 2f + offset, getHeight() / 2f + offset,
                    getWidth() / 2f + offset + radius,
                    getHeight() / 2f + offset + radius, mPaint);

            //直线
            float lineLeftX = getWidth() / 2f + offset + radius
                    - (getWidth() / 2f + offset + radius - getWidth() / 4f) * progress;
            canvas.drawLine(lineLeftX, getHeight() / 2f + offset + radius,
                    getWidth() / 2f + offset + radius,
                    getHeight() / 2f + offset + radius, mPaint);
        } else {//大于一半
            //圆形尾巴 progress范围要从0.5~1扩大到0~1 公式y=2x-1
            float changeProgress = 2 * progress - 1;
            float offset = (float) (Math.cos(PI / 4f) * radius);
            canvas.drawLine(getWidth() / 2f + offset + radius * changeProgress,
                    getHeight() / 2f + offset + radius * changeProgress,
                    getWidth() / 2f + offset + radius,
                    getHeight() / 2f + offset + radius, mPaint);

            //直线
            float lineLeftX = getWidth() / 2f + offset + radius
                    - (getWidth() / 2f + offset + radius - getWidth() / 4f) * progress;
            canvas.drawLine(lineLeftX, getHeight() / 2f + offset + radius,
                    getWidth() / 2f + offset + radius,
                    getHeight() / 2f + offset + radius, mPaint);
        }
        oldProgress = progress;
    }

    ValueAnimator mValueAnimator;

    public void startAnim() {
        mValueAnimator = new ValueAnimator();

        mValueAnimator.setFloatValues(0, 1);
        mValueAnimator.setDuration(1000);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setRepeatMode(ValueAnimator.REVERSE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mValueAnimator.start();
    }

    public void cancelAnim() {
        if (mValueAnimator != null) {
            mValueAnimator.cancel();
        }
    }

}
