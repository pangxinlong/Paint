package com.example.pangxinlong.paint.paint_view;

import com.example.pangxinlong.paint.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pangxinlong on 2017/8/29.
 */

public class PaintEraserView extends View {


    private Bitmap mSrcBitmap;//源图

    private Bitmap mDstBitmap;//目标图

    private Paint mPaint;

    private float oldpositionX ;

    private float oldpositionY;

    private Path mPath;

    public PaintEraserView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mPath = new Path();
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);
        mDstBitmap = Bitmap.createBitmap(mSrcBitmap.getWidth(), mSrcBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);
        Log.e("view===",isHardwareAccelerated()+"");
        setLayerType(View.LAYER_TYPE_HARDWARE,null);
        Log.e("view===",isHardwareAccelerated()+"");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("canvas===",canvas.isHardwareAccelerated()+"");
//        int layerId=canvas.saveLayer(0,0,getWidth(),getHeight(),null,Canvas.ALL_SAVE_FLAG);
        Canvas canvas1 = new Canvas(mDstBitmap);
        canvas1.drawPath(mPath, mPaint);
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mSrcBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
//        canvas.restoreToCount(layerId);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                oldpositionX = event.getX();
                oldpositionY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                mPath.quadTo(oldpositionX, oldpositionY, event.getX(), event.getY());
                oldpositionX = event.getX();
                oldpositionY = event.getY();
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }
}
