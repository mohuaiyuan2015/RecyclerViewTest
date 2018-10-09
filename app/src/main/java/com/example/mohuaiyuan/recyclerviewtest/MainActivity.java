package com.example.mohuaiyuan.recyclerviewtest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Context context;

//    private RecyclerView rv_notArrival;
    private AutoPollRecyclerView rv_notArrival;
    private MyAdapter myAdapter;
    private List<String> list=new ArrayList<>();
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context=this;

        initUI();
        initListener();
        initData();
        
    }

    private void initData() {

        initListData();

        Log.d("yfing.wei", "list.size(): "+list.size());
        GridLayoutManager gridLayoutManager=new GridLayoutManager(context,3);
        rv_notArrival.setLayoutManager(gridLayoutManager);
//        rv_notArrival.setItemAnimator(new DefaultItemAnimator());
//        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        linearLayoutManager.findFirstVisibleItemPosition();
//        rv_notArrival.setLayoutManager(linearLayoutManager);

        myAdapter=new MyAdapter(context,list);
        rv_notArrival.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
        rv_notArrival.start();
    }

    private void initListData() {
        for (int i=0;i<16;i++){
            list.add("第"+i+"项");
        }
    }

    private void initListener() {
    }

    private void initUI() {
        rv_notArrival=findViewById(R.id.rv_notArrival);
    }
}
