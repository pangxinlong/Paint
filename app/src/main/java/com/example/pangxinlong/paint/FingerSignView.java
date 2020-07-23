package com.example.pangxinlong.paint;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Date: 2020-03-02
 * author: pangxinlong
 * Description:
 */
public class FingerSignView extends View {
    public FingerSignView(Context context) {
        super(context);
        paint.setColor(getResources().getColor(android.R.color.black));
        paint.setStrokeWidth(10);
        paint.setStyle(Paint.Style.STROKE);
        path = new Path();
    }

    public FingerSignView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FingerSignView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Paint paint;
    private Path path;

    private int height;
    private int width;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        height = getHeight();
        width = getWidth();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                invalidate();
                break;
        }
        return true;
    }
}
