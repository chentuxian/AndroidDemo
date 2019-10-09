package com.marcochan.androiddemo.google.two;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.marcochan.androiddemo.R;

import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecyclerViewActivity extends AppCompatActivity {

    // 模拟数据
    private final LinkedList<String> dataList = new LinkedList<>();

    private MyAdapter myAdapter;

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.item_add_btn)
    Button item_add_btn;

    @BindView(R.id.item_remove_btn)
    Button item_remove_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);
        ButterKnife.bind(this);

        // 初始化数据
        for (int i = 0; i < 30; i++) dataList.add("word" + i);

        // 设置RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        myAdapter = new MyAdapter();
        recyclerView.setAdapter(myAdapter);

        // 添加删除和添加动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
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

        public void addData(String data,int position){
            dataList.add(position,data);
            notifyItemInserted(position);
        }

        public void removeData(int position){
            dataList.remove(position);
            notifyItemRemoved(position);
        }
    }

    @OnClick(R.id.item_add_btn)
    public void itemAddBtnClick(){
        myAdapter.addData("test",0);
    }

    @OnClick(R.id.item_remove_btn)
    public void itemRemoveBtnClick(){
        myAdapter.removeData(0);
    }


}
