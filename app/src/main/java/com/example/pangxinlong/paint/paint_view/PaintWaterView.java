package com.example.pangxinlong.paint.paint_view;

/**
 * Created by pangxinlong on 2017/8/25.
 */

import com.example.pangxinlong.paint.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * 水波纹
 */
public class PaintWaterView extends View {

    private Paint mPaint;

    private int[] colors = {0x00000000, 0x50ffffff, 0x00000000};

    private float[] stops = {0.2f, 0.3f, 0.3f, 0.2f};

    private RadialGradient mRadialGradient;

    private int radius = 20;

    private float centerX = -1;

    private float centerY = -1;

    private Bitmap mBitmap;

    private double diagonal;//图片对角线长度

    public PaintWaterView(Context context) {
        super(context);
    }

    public PaintWaterView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.timg)).getBitmap();
        diagonal = Math
                .sqrt(Math.pow(mBitmap.getWidth(), 2) + Math.pow(mBitmap.getHeight(), 2));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        if (centerX != -1 && centerY != -1) {
            mRadialGradient = new RadialGradient(centerX, centerY, radius,
                    colors, null, Shader.TileMode.CLAMP);
            mPaint.setShader(mRadialGradient);
            radius += 20;
            if (radius > judgeEdge()) {
                return;
            }
            postInvalidateDelayed(50);
            Rect rect = new Rect(0, 0, mBitmap.getWidth(), mBitmap.getHeight());
            canvas.drawRect(rect, mPaint);
        }
    }

    private float judgeEdge() {
        float edgeX = Math.abs(centerX - mBitmap.getWidth() / 2);
        float edgeY = Math.abs(centerY - mBitmap.getHeight() / 2);
        double edge = Math
                .sqrt(Math.pow(edgeX, 2) + Math.pow(edgeY, 2));
        return (float) (diagonal / 2.0 + edge)*2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            centerX = event.getX();
            centerY = event.getY();
            radius = 20;
            invalidate();
        }
        return true;
    }
}
