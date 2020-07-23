package com.example.pangxinlong.paint.recycle;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pangxinlong.paint.R;


/**
 * Date: 2020/7/22
 * author: pangxinlong
 * Description:
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {

    private Rect rect;
    private Paint dividerPaint;
    private Paint headPaint;
    private Paint textPaint;
    private int groupHeaderHeight;

    private Rect textRect;

    public MyItemDecoration(Context context) {
        groupHeaderHeight = dp2px(context, 100);

        dividerPaint = new Paint();
        dividerPaint.setStyle(Paint.Style.FILL);
        dividerPaint.setColor(Color.BLUE);
        dividerPaint.setAntiAlias(true);


        headPaint = new Paint();
        headPaint.setStyle(Paint.Style.FILL);
        headPaint.setColor(Color.RED);
        headPaint.setAntiAlias(true);

        textPaint = new Paint();
        textPaint.setTextSize(50);
        textPaint.setColor(Color.BLACK);

        textRect = new Rect();
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() instanceof RecyclerFragment.MyAdapter) {
            RecyclerFragment.MyAdapter adapter = (RecyclerFragment.MyAdapter) (parent.getAdapter());
            int position = parent.getChildLayoutPosition(view);
            boolean isGroupHeader = adapter.isHeader(position);
            if (isGroupHeader) {
                outRect.set(0, groupHeaderHeight, 0, 0);
            } else {
                outRect.set(0, 1, 0, 0);
            }
        }
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() instanceof RecyclerFragment.MyAdapter) {
            RecyclerFragment.MyAdapter adapter = (RecyclerFragment.MyAdapter) (parent.getAdapter());
            int count = parent.getChildCount();
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top;
            int bottom;
            for (int i = 0; i < count; i++) {
                View view = parent.getChildAt(i);
                int position = parent.getChildLayoutPosition(view);

                boolean isGroupHeader = adapter.isHeader(position);
                Log.i("view.getTop()", view.getTop() + "");
                if (view.getTop() - groupHeaderHeight - parent.getPaddingTop() >= 0) {
                    if (isGroupHeader) {
                        TextView textView = view.findViewById(R.id.tv_content);
                        c.drawRect(left, view.getTop() - groupHeaderHeight, right, view.getTop(), headPaint);
                        String groupName = textView.getText().toString();
                        textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                        c.drawText(groupName, left + 20, view.getTop() -
                                groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
                    } else {
                        bottom = view.getTop();
                        top = bottom - 1;
                        c.drawRect(left, top, right, view.getTop(), dividerPaint);
                    }
                }
            }
        }
    }

    @Override
    public void onDrawOver(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if (parent.getAdapter() instanceof RecyclerFragment.MyAdapter) {
            RecyclerFragment.MyAdapter adapter = (RecyclerFragment.MyAdapter) (parent.getAdapter());
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();
            int top = parent.getPaddingTop();
            int firstVisiblePosition = ((LinearLayoutManager) parent.getLayoutManager()).findFirstVisibleItemPosition();
            String groupName = adapter.getGroupName(firstVisiblePosition);
            View view = parent.findViewHolderForAdapterPosition(firstVisiblePosition).itemView;
            boolean isGroupHeader = adapter.isHeader(firstVisiblePosition + 1);
            if (isGroupHeader) {
                int bottom = Math.min(view.getBottom() - top, groupHeaderHeight);

                c.clipRect(left, top, right, top + bottom);
                c.drawRect(left, top, right, top + bottom, headPaint);
                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                c.drawText(groupName, left + 20, view.getBottom() -
                        groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
            } else {
                c.drawRect(left, top, right, top + groupHeaderHeight, headPaint);

                textPaint.getTextBounds(groupName, 0, groupName.length(), textRect);
                c.drawText(groupName, left + 20, top + groupHeaderHeight / 2 + textRect.height() / 2, textPaint);
            }
        }
    }

    private int dp2px(Context context, float dpValue) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale * 0.5f);
    }
}
