package com.example.pangxinlong.paint.paint_view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by pangxinlong on 2017/8/24.
 */

public class PaintText extends TextView {

    private Paint mPaint;

    private int[] colors = {0x20ffffff, 0xffffffff, 0x20ffffff};

    private float speed = 10;//移动速度

    private int refreshRate = 50;//刷新频率

    private float translate = 0;//位移距离

    private LinearGradient linearGradient;

    private Matrix matrix;

    private float textWidth;

    private int screenWidth;


    public PaintText(Context context) {
        super(context);
    }

    public PaintText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        wm.getDefaultDisplay().getSize(point);
        screenWidth = point.x;

        mPaint = getPaint();
        String content = getText().toString();
        textWidth = mPaint.measureText(content);

        linearGradient = new LinearGradient(0, 0, textWidth / content.length() * 3, 0,
                colors, null,
                Shader.TileMode.CLAMP);

        mPaint.setShader(linearGradient);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (matrix == null) {
            matrix = new Matrix();
        }

        if (translate > Math.min(screenWidth, textWidth)) {
            translate = 0;
        }

        matrix.setTranslate(translate, 0);
        linearGradient.setLocalMatrix(matrix);
        translate += speed;
        postInvalidateDelayed(refreshRate);
    }
}
