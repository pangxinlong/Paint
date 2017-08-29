package com.example.pangxinlong.paint.paint_view;

import com.example.pangxinlong.paint.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by pangxinlong on 2017/8/24.
 */

/**
 * 放大镜
 */
public class PaintMagnifierView extends View {

    private Paint mPaint;

    private Bitmap mBitmap;

    private Bitmap mScaleBitmap;

    private ShapeDrawable mShapeDrawable;

    //放大半径
    private int RADIUS = 50;

    //放大配属
    private int MULTIPLE=2;

    private float eventX = 0;

    private float eventY = 0;

    private boolean isShowScale;

    private Matrix mMatrix = new Matrix();


    public PaintMagnifierView(Context context) {
        super(context);
    }

    public PaintMagnifierView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap = ((BitmapDrawable) getResources().getDrawable(R.mipmap.timg)).getBitmap();
        mScaleBitmap = mBitmap;
        mScaleBitmap = Bitmap.createScaledBitmap(mScaleBitmap, mScaleBitmap.getWidth() * MULTIPLE,
                mScaleBitmap.getHeight() * MULTIPLE, true);
        mPaint = new Paint();

        BitmapShader bitmapShader = new BitmapShader(mScaleBitmap, Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT);
        mShapeDrawable = new ShapeDrawable(new OvalShape());
        mShapeDrawable.getPaint().setShader(bitmapShader);
        mShapeDrawable.setBounds(0, 0, RADIUS, RADIUS);
    }

    public PaintMagnifierView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.WHITE);

//        int[] colors={Color.RED,Color.YELLOW,Color.GREEN,Color.BLUE};
//        LinearGradient linearGradient=new LinearGradient(0,0,500,500,colors,null, Shader.TileMode.CLAMP);
//        mPaint.setShader(linearGradient);
//        Rect rect=new Rect(0,0,Math.min(mBitmap.getHeight(),mBitmap.getWidth()),Math.min(mBitmap.getHeight(),mBitmap.getWidth()));
//        canvas.drawRect(rect,mPaint);

        canvas.drawBitmap(mBitmap, 0, 0, mPaint);
        if (isShowScale) {
            mShapeDrawable.draw(canvas);
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.e("pxl=====", eventX + "   " + eventY);
                isShowScale = true;
                eventX = event.getX();
                eventY = event.getY();
                if (0 < eventX && eventX < mBitmap.getWidth() && 0 < eventY && eventY < mBitmap
                        .getHeight()) {
                    mMatrix.setTranslate(RADIUS - eventX * MULTIPLE, RADIUS - eventY * MULTIPLE);
                    mShapeDrawable.getPaint().getShader().setLocalMatrix(mMatrix);
                    mShapeDrawable.setBounds((int) eventX - RADIUS, (int) eventY - RADIUS,
                            (int) eventX + RADIUS,
                            (int) eventY + RADIUS);
                }else {
                    isShowScale = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                isShowScale = false;
                break;
        }
        invalidate();
        return true;
    }
}
