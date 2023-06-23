package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);


        RecyclerView mPostRecyclerView = findViewById(R.id.recyclerView);
        List<Board> mDatas = new ArrayList<>(); // 샘플 데이터 추가
        mDatas.add(new Board("title","contents","time",20,10));
        mDatas.add(new Board("title","contents","time",20,10));
        mDatas.add(new Board("title","contents","time",20,10));
        mDatas.add(new Board("title","contents","time",20,10));
        mDatas.add(new Board("title","contents","time",20,10));

        // Adapter, LayoutManager 연결
        BoardAdapter mAdpater = new BoardAdapter(mDatas);
        mPostRecyclerView.setAdapter(mAdpater);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}