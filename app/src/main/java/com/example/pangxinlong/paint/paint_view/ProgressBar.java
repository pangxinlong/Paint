package com.example.pangxinlong.paint.paint_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pangxinlong on 2017/8/28.
 */

public class ProgressBar extends View {


    private Paint mPaint;

    private int progress = 0;

    public ProgressBar(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //环
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 100, mPaint);

        mPaint.setStrokeWidth(0);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
        if (progress != 0) {
            canvas.drawText(progress + "%", getWidth() / 2 - mPaint.measureText(progress + "%")/2,
                    getHeight() / 2 + (fontMetrics.bottom - fontMetrics.top) / 2
                            - fontMetrics.bottom,
                    mPaint);
        }

        mPaint.setStrokeWidth(20);
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(getWidth() / 2 - 100, getHeight() / 2 - 100, getWidth() / 2 + 100,
                getHeight() / 2 + 100);
        canvas.drawArc(rectF, 0, progress / 100f * 360f, false, mPaint);

    }

    public void setProgress(int progress){
        this.progress=progress;
        postInvalidate();
    }
}
