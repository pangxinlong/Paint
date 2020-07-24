package com.example.pangxinlong.paint.recycle;

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
public class TabText extends View {


    public TabText(Context context) {
        super(context);
        init();
    }

    public TabText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TabText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

    }

    Rect textRect;
    private Paint paint;
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
    }

    private String text;

    public void setText(String text) {
        this.text = text;
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

        //底部文字
        canvas.drawText(text, baseX, baseLine, paint);

        //计算覆盖文字需要的裁剪范围
        float left = baseX - textWidth / 2 + leftRate * textWidth;
        float right = baseX + textWidth / 2 - rightRate * textWidth;
        canvas.clipRect(left, 0, right, getHeight());
        //覆盖文字
        canvas.drawText(text, baseX, baseLine, overPaint);
    }

    //裁剪左边比例
    private float leftRate;

    public void setLeftRate(float rate) {
        this.leftRate = rate;
        invalidate();
    }

    //裁剪右边比例
    private float rightRate = 1;

    public void setRightRate(float rate) {
        this.rightRate = rate;
        invalidate();
    }

    public float getRate() {
        return rightRate;
    }

    public void setDefaultSelected() {
        leftRate = 0;
        rightRate = 0;
        invalidate();
    }
}
