package com.example.pangxinlong.paint.recycle;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pangxinlong.paint.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 2020/7/22
 * author: pangxinlong
 * Description:
 */
public class RecyclerFragment extends Fragment {

    private View view;

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_recycler, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rv_list);
        init();
    }

    private void init() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            if (i < 10) {
                list.add("内容一");
            } else if (i < 20) {
                list.add("内容二");
            } else {
                list.add("内容三");
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.addItemDecoration(new MyItemDecoration(getContext()));
        recyclerView.setAdapter(new MyAdapter(list));

    }

    class MyAdapter extends RecyclerView.Adapter {

        private List<String> list;

        public MyAdapter(List<String> list) {
            this.list = list;
        }


        public boolean isHeader(int position) {
            if (position == 0) {
                return true;
            } else {
                return !list.get(position - 1).equals(list.get(position));
            }
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.recycle_item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            TextView textView = holder.itemView.findViewById(R.id.tv_content);
            textView.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public String getGroupName(int position) {
            return list.get(position);
        }

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
