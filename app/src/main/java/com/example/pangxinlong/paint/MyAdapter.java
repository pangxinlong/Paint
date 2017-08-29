package com.example.pangxinlong.paint;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by pangxinlong on 2017/8/28.
 */

public class MyAdapter extends BaseAdapter {


    private Context mContext;

    private List<String> mDataList;

    public MyAdapter(Context context, List<String> dataList) {
        mContext = context;
        mDataList = dataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
            viewHolder.bind(position);
            return convertView;

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.bind(position);
            return convertView;
        }
    }

    class ViewHolder {

        private TextView mTextView;

        public ViewHolder(View convertView) {
            mTextView = (TextView) convertView.findViewById(R.id.tv_description);
        }

        public void bind(int position) {
            mTextView.setText(mDataList.get(position));
        }
    }
}
