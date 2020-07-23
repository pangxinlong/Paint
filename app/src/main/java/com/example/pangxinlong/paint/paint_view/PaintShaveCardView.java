package com.example.pangxinlong.paint.paint_view;

import com.example.pangxinlong.paint.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.Drawable;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pangxinlong on 2017/8/29.
 */

public class PaintShaveCardView extends View {


    private Bitmap mSrcBitmap;

    private Bitmap mDstBitmap;//目标图

    private Bitmap mCoverBitmap;

    private Paint mPaint;

    private Path mPath;

    private float oldpositionX = -1;

    private float oldpositionY = -1;

    public PaintShaveCardView(Context context) {
        this(context, null);
    }

    public PaintShaveCardView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(40);
        mPaint.setColor(Color.RED);
        mPath = new Path();
        mSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.timg);

//        mCoverBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_black_cover);
        Drawable drawable = getResources().getDrawable(R.drawable.bg_black_cover);
        mCoverBitmap = Bitmap
                .createBitmap(mSrcBitmap.getWidth(), mSrcBitmap.getHeight(),
                        drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888
                                : Bitmap.Config.RGB_565);
        mDstBitmap = Bitmap.createBitmap(mCoverBitmap.getWidth(), mCoverBitmap.getHeight(),
                Bitmap.Config.ARGB_8888);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(mSrcBitmap, 0, 0, mPaint);
        int layerId = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);

        Canvas canvas1 = new Canvas(mDstBitmap);
        canvas1.drawPath(mPath, mPaint);
        canvas.drawBitmap(mDstBitmap, 0, 0, mPaint);

        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mCoverBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(layerId);
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
