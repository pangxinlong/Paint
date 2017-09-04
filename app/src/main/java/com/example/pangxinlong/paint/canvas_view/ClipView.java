package com.example.pangxinlong.paint.canvas_view;

import com.example.pangxinlong.paint.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

/**
 * Created by pangxinlong on 2017/9/3.
 */

//public class ClipView extends View {
//
//    private Paint mPaint;
//
//    private Bitmap mBitmap1;
//
//    private Bitmap mBitmap2;
//
//    public ClipView(Context context,
//            @Nullable AttributeSet attrs) {
//        super(context, attrs);
//        mPaint = new Paint();
//        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
//        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
//    }
//
//    @Override
//    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//
//        canvas.save();
//        canvas.clipRect(getWidth() / 2 - mBitmap1.getWidth() / 2,
//                getHeight() / 2 - mBitmap1.getHeight() / 2, getWidth() / 2,
//                getHeight() / 2 + mBitmap1.getHeight() / 2);
//        canvas.drawBitmap(mBitmap1, getWidth() / 2 - mBitmap1.getWidth() / 2,
//                getHeight() / 2 - mBitmap1.getHeight() / 2, mPaint);
//        canvas.restore();
//        canvas.clipRect(getWidth() / 2, getHeight() / 2 - mBitmap2.getHeight() / 2,
//                getWidth() / 2 + mBitmap2.getWidth() / 2,
//                getHeight() / 2 + mBitmap2.getHeight() / 2);
//        canvas.drawBitmap(mBitmap2, getWidth() / 2-mBitmap2.getWidth()/2, getHeight() / 2 - mBitmap2.getHeight() / 2,
//                mPaint);
//    }
//}
public class ClipView extends Drawable {

    private Drawable mSelectDrawable;

    private Drawable mUnSelectDrawable;

    private Paint mPaint;

    private Rect mRect = new Rect();

    public ClipView(Drawable selectDrawable, Drawable unSelectDrawable) {
        mSelectDrawable = selectDrawable;
        mUnSelectDrawable = unSelectDrawable;
        mPaint = new Paint();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int level = getLevel();
        float boundaryRange = level / 5000f - 1f;//-1f~1f
        Log.e("pxl===boundaryRange",boundaryRange+"");
        if (level == 0 || level == 10000) {
            mUnSelectDrawable.draw(canvas);
        } else if (level == 5000) {
            mUnSelectDrawable.draw(canvas);
        } else {
            canvas.save();
            //灰色区域
            int unSelectGravity = boundaryRange < 0 ? Gravity.LEFT : Gravity.RIGHT;

            Rect rect = mRect;
            Rect bound = getBounds();
            int unSelectWidth = (int) (Math.abs(boundaryRange) * bound.width());
            Gravity.apply(unSelectGravity, unSelectWidth, bound.height(), bound,
                    rect);
            canvas.clipRect(rect);
            mUnSelectDrawable.draw(canvas);
            canvas.restore();

            //彩色区域
            int selectWidth = bound.width() - (int) (Math.abs(boundaryRange) * bound.width());
            int selectGravity = boundaryRange < 0 ? Gravity.RIGHT : Gravity.LEFT;
            Gravity.apply(selectGravity, selectWidth, bound.height(), bound,
                    rect);
//            canvas.save();//保存画布
            canvas.clipRect(rect);
            mSelectDrawable.draw(canvas);
//            canvas.restore();
        }
    }

    protected void onBoundsChange(Rect bounds) {
        // 定好两个Drawable图片的宽高---边界bounds
        mUnSelectDrawable.setBounds(bounds);
        mSelectDrawable.setBounds(bounds);
        Log.d("pxl==bounds change", "w = " + bounds.width());
    }

    @Override
    public int getIntrinsicWidth() {
        //得到Drawable的实际宽度
        return Math.max(mSelectDrawable.getIntrinsicWidth(),
                mSelectDrawable.getIntrinsicWidth());
    }

    @Override
    public int getIntrinsicHeight() {
        //得到Drawable的实际高度
        return Math.max(mSelectDrawable.getIntrinsicHeight(),
                mSelectDrawable.getIntrinsicHeight());
    }

    @Override
    protected boolean onLevelChange(int level) {
//        Log.e("pxl===level",level+"");
        invalidateSelf();
        return true;
    }

    @Override
    public void setAlpha(@IntRange(from = 0, to = 255) int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
