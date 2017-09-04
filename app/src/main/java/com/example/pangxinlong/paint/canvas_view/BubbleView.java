package com.example.pangxinlong.paint.canvas_view;

import android.animation.Animator;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.OvershootInterpolator;

/**
 * Created by pangxinlong on 2017/9/4.
 */

//仿qq气泡
public class BubbleView extends View {

    private Paint mCirclePaint;

    private Paint mTextPaint;

    private Path mPath;

    private static final float RADIUS = 20;

    private float originalRadius = RADIUS;//原点小圆半径

    private float originalCenterX;//原点小圆半径

    private float originalCenterY;//原点小圆半径

    private int moveRadius = 20;//移动小圆半径

    private float moveCenterX;//移动小圆半径

    private float moveCenterY;//移动小圆半径

    private float separateBoundary = 200;//分离边界值

    private float anchorX;//锚点x

    private float anchorY;//锚点y

    private float centerDistance;//圆心距

    private float sinValue;

    private float cosValue;

    private static STATUS mStatus;

    String content = "10";

    Rect rect = new Rect();

    public enum STATUS {
        STATUS_STATIC,//静止
        STATUS_CONNECTED,//链接
        STATUS_SEPARATE,//分离
        STATUS_DISAPPEAR//消失
    }

    public BubbleView(Context context,
            @Nullable AttributeSet attrs) {
        super(context, attrs);
        mStatus = STATUS.STATUS_STATIC;
        mPath = new Path();
        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.FILL);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setStyle(Paint.Style.STROKE);
        mTextPaint.getTextBounds(content, 0, content.length(), rect);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mStatus == STATUS.STATUS_STATIC) {//静止状态

            originalCenterX = getWidth() / 2;
            originalCenterY = getHeight() / 2;
            canvas.drawCircle(originalCenterX, originalCenterY, originalRadius, mCirclePaint);

            canvas.drawText("10", getWidth() / 2 - rect.width() / 2,
                    getHeight() / 2 + rect.height() / 2, mTextPaint);
        } else if (mStatus == STATUS.STATUS_CONNECTED) {//连接状态
            canvas.drawCircle(originalCenterX, originalCenterY, originalRadius, mCirclePaint);
            canvas.drawCircle(moveCenterX, moveCenterY, moveRadius, mCirclePaint);

            //贝塞尔曲线
            anchorX = (originalCenterX + moveCenterX) / 2;
            anchorY = (originalCenterY + moveCenterY) / 2;

            float moveStartX = moveCenterX - moveRadius * sinValue;
            float moveStartY = moveCenterY + moveRadius * cosValue;
            float moveEndX = moveCenterX + moveRadius * sinValue;
            float moveEndY = moveCenterY - moveRadius * cosValue;

            float originalStartX = originalCenterX - originalRadius * sinValue;
            float originalStartY = originalCenterY + originalRadius * cosValue;
            float originalEndX = originalCenterX + originalRadius * sinValue;
            float originalEndY = originalCenterY - originalRadius * cosValue;

            mPath.reset();
            mPath.moveTo(moveStartX, moveStartY);
            mPath.quadTo(anchorX, anchorY, originalStartX, originalStartY);
            mPath.lineTo(originalEndX, originalEndY);
            mPath.quadTo(anchorX, anchorY, moveEndX, moveEndY);
            mPath.close();
            canvas.drawPath(mPath, mCirclePaint);
            canvas.drawText("10", moveCenterX - rect.width() / 2,
                    moveCenterY + rect.height() / 2, mTextPaint);
        } else if (mStatus == STATUS.STATUS_SEPARATE) {//分离状态
            canvas.drawCircle(moveCenterX, moveCenterY, moveRadius, mCirclePaint);
            canvas.drawText("10", moveCenterX - rect.width() / 2,
                    moveCenterY + rect.height() / 2, mTextPaint);
        } else {//消失

        }

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mStatus != STATUS.STATUS_DISAPPEAR) {
                    mStatus = STATUS.STATUS_STATIC;
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (mStatus != STATUS.STATUS_DISAPPEAR) {
                    moveCenterX = event.getX();
                    moveCenterY = event.getY();
                    if (mStatus != STATUS.STATUS_SEPARATE) {
                        mStatus = STATUS.STATUS_CONNECTED;
                    }
                    float distanceX = originalCenterX - moveCenterX;
                    float distanceY = originalCenterY - moveCenterY;
                    centerDistance = (float) Math
                            .sqrt(distanceX * distanceX + distanceY * distanceY);
                    if (centerDistance < separateBoundary) {//在拖拽边界内
                        sinValue = distanceY / centerDistance;
                        cosValue = distanceX / centerDistance;
                        float ratio = centerDistance / separateBoundary;
                        originalRadius = Math.max(RADIUS * (1 - ratio), 5);
                    } else {//超出拖拽边界
                        mStatus = STATUS.STATUS_SEPARATE;
                    }
                }
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (mStatus != STATUS.STATUS_DISAPPEAR) {
                    if (mStatus == STATUS.STATUS_SEPARATE) {//分离状态
                        if (centerDistance < separateBoundary) {//超出边界则还原
                            originalRadius = RADIUS;
                            resetAnim();
                        } else {//超出边界则爆炸
                            mStatus = STATUS.STATUS_DISAPPEAR;
                        }
                    } else {//否则恢复原状
                        originalRadius = RADIUS;
                        resetAnim();
                    }
                }
                break;
        }
        return true;
    }

    private void resetAnim() {
        ValueAnimator valueAnimator = ValueAnimator
                .ofObject(new PointFEvaluator(), new PointF(moveCenterX, moveCenterY),
                        new PointF(originalCenterX, originalCenterY));
        valueAnimator.setDuration(200);
        valueAnimator.setInterpolator(new OvershootInterpolator(3f));
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF pointF = (PointF) animation.getAnimatedValue();
                moveCenterX = pointF.x;
                moveCenterY = pointF.y;
                invalidate();
            }
        });

        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mStatus = STATUS.STATUS_STATIC;
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        valueAnimator.start();
    }
}
