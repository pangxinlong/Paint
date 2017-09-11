package com.example.pangxinlong.paint.svg_view;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Region;

/**
 * Created by pangxinlong on 2017/9/8.
 */

public class MapBean {

    private Path mPath;

    private boolean isSelect;

    public MapBean(Path path) {
        mPath = path;
    }


    public void draw(Canvas canvas, Paint paint) {
        paint.reset();
        if (isSelect) {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(0xff00ff);
            canvas.drawPath(mPath, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setShadowLayer(2, 0, 0, 0x80ffffff);
            paint.setColor(Color.BLUE);
            canvas.drawPath(mPath, paint);
        } else {
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(5);
            paint.setColor(Color.RED);
            canvas.drawPath(mPath, paint);

            paint.setStyle(Paint.Style.FILL);
            paint.setColor(Color.GRAY);
            canvas.drawPath(mPath, paint);
        }

    }

    public void isSelect(int x, int y) {
        RectF rectF = new RectF();
        mPath.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(mPath, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right,
                (int) rectF.bottom));
        isSelect=region.contains(x, y);
    }


    public Path getPath() {
        return mPath;
    }

    public void setPath(Path path) {
        mPath = path;
    }


    public void setSelect(boolean select) {
        isSelect = select;
    }
}
