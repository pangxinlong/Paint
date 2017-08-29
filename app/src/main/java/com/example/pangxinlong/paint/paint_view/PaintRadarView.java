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
        mPaint.setShader(sweepGradient);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        degrees += 1;
        if (degrees >= 360) {
            degrees = 0;
        }
        if (matrix == null) {
            matrix = new Matrix();
        }
        matrix.setRotate(degrees, getWidth() / 2, getHeight() / 2);
        sweepGradient.setLocalMatrix(matrix);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, 300, mPaint);
        invalidate();
    }
}
