package com.example.pangxinlong.paint.paint_view;

/**
 * Created by pangxinlong on 2017/8/25.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.SweepGradient;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 雷达
 */
public class PaintRadarView extends View {

    private float degrees = 0;

    private Paint mPaint;

    private int[] colors = {Color.BLUE, Color.RED};

    private Matrix matrix;

    private SweepGradient sweepGradient;

    private int radius=300;

    public PaintRadarView(Context context) {
        super(context);
    }

    public PaintRadarView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mPaint = new Paint();
        sweepGradient = new SweepGradient(getWidth() / 2, getHeight() / 2, colors, null);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        //雷达扫描
        mPaint.reset();
        mPaint.setShader(sweepGradient);
        degrees += 1;
        if (degrees >= 360) {
            degrees = 0;
        }
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.setRotate(degrees, getWidth() / 2, getHeight() / 2);
        sweepGradient.setLocalMatrix(matrix);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);


        canvas.restore();
        //绘制线条
        mPaint.reset();
        mPaint.setColor(Color.WHITE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(1);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius-100,mPaint);
        canvas.drawCircle(getWidth()/2,getHeight()/2,radius-200,mPaint);
        canvas.drawLine(getWidth()/2,getHeight()/2-radius,getWidth()/2,getHeight()/2+radius,mPaint);
        canvas.drawLine(getWidth()/2-radius,getHeight()/2,getWidth()/2+radius,getHeight()/2,mPaint);
        invalidate();
    }
}
