package com.example.pangxinlong.paint.recycle;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Date: 2020/7/23
 * author: pangxinlong
 * Description:利用布局裁剪
 */
public class ClipCanvasText extends View {


    public ClipCanvasText(Context context) {
        super(context);
    }

    public ClipCanvasText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClipCanvasText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        float textWidth = paint.measureText(text);
//        canvas.drawLine(0, getHeight() / 2, getWidth(), getHeight() / 2, paint);
//        canvas.drawLine(getWidth() / 2, 0, getWidth() / 2, getHeight(), paint);

        //获取文字的几根关键线
        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
        baseX = getWidth() / 2;
        // getHeight() / 2 - fontMetrics.ascent - ((fontMetrics.descent - fontMetrics.ascent) / 2) 推到公式-——>>>
        // getHeight() / 2 +((fontMetrics.ascent + fontMetrics.descent) / 2)
        baseLine = getHeight() / 2 - ((fontMetrics.ascent + fontMetrics.descent) / 2);
        float dividerLine = baseX - textWidth / 2 + rate * textWidth;

        canvas.save();
        canvas.clipRect(dividerLine, 0, dividerLine + textWidth, getHeight());//此行优化减少布局绘制
        canvas.drawText(text, baseX, baseLine, paint);
        canvas.restore();

        canvas.clipRect(dividerLine - textWidth, 0, dividerLine, getHeight());
        canvas.drawText(text, baseX, baseLine, overPaint);
    }

    Rect textRect;
    private Paint paint;
    private String text = "测试测试测试测试测试测试测试测试";

    private Paint overPaint;
    private float baseX;
    private float baseLine;

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1);
        paint.setTextSize(48);
        paint.setAntiAlias(true);
        paint.setTextAlign(Paint.Align.CENTER);
        textRect = new Rect();


        overPaint = new Paint();
        overPaint.setColor(Color.RED);
        overPaint.setStyle(Paint.Style.FILL);
        overPaint.setStrokeWidth(1);
        overPaint.setTextSize(48);
        overPaint.setAntiAlias(true);
        overPaint.setTextAlign(Paint.Align.CENTER);

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "rate", 0, 1);
        objectAnimator.setStartDelay(1000);
        objectAnimator.setDuration(5000);
        objectAnimator.setRepeatMode(ValueAnimator.REVERSE);
        objectAnimator.setRepeatCount(ValueAnimator.INFINITE);
        objectAnimator.start();

    }

    private float rate;

    public void setRate(float rate) {
        this.rate = rate;
        invalidate();
    }

    public float getRate() {
        return rate;
    }
}
