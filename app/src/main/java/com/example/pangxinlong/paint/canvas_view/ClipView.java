package com.example.pangxinlong.paint.canvas_view;

import com.example.pangxinlong.paint.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by pangxinlong on 2017/9/3.
 */

public class ClipView extends View {

    private Paint mPaint;

    private Bitmap mBitmap1;

    private Bitmap mBitmap2;

    public ClipView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint();
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.save();
        canvas.clipRect(getWidth() / 2 - mBitmap1.getWidth() / 2,
                getHeight() / 2 - mBitmap1.getHeight() / 2, getWidth() / 2,
                getHeight() / 2 + mBitmap1.getHeight() / 2);
        canvas.drawBitmap(mBitmap1, getWidth() / 2 - mBitmap1.getWidth() / 2,
                getHeight() / 2 - mBitmap1.getHeight() / 2, mPaint);
        canvas.restore();
        canvas.clipRect(getWidth() / 2, getHeight() / 2 - mBitmap2.getHeight() / 2,
                getWidth() / 2 + mBitmap2.getWidth() / 2,
                getHeight() / 2 + mBitmap2.getHeight() / 2);
        canvas.drawBitmap(mBitmap2, getWidth() / 2-mBitmap2.getWidth()/2, getHeight() / 2 - mBitmap2.getHeight() / 2,
                mPaint);
    }
}
