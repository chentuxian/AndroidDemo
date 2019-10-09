package com.marcochan.androiddemo.google.two;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcochan.androiddemo.R;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecyclerViewActivity extends AppCompatActivity {

    // 模拟数据
    private final LinkedList<String> dataList = new LinkedList<>();

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        // 初始化数据
        for (int i = 0; i < 20; i++) dataList.add("word" + i);

        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter());
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            // 根据XML文件来创建一个VIEW
            View view = LayoutInflater.from(RecyclerViewActivity.this).inflate(R.layout.item_recycler_view_layout,parent,false);
            // 把VIEW保存在ViewHolder中
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.my_recycler_view_tv.setText(dataList.get(position));
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView my_recycler_view_tv;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                my_recycler_view_tv = itemView.findViewById(R.id.my_recycler_view_tv);
            }
        }
    }
}
