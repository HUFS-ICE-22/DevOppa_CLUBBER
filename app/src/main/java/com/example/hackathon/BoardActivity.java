package com.example.hackathon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BoardActivity extends AppCompatActivity {

    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    List<Board> mDatas = new ArrayList<>(); // 샘플 데이터 추가
    BoardAdapter mAdapter = new BoardAdapter(mDatas);;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);

        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        RecyclerView mPostRecyclerView = findViewById(R.id.recyclerView);
//        List<Board> mDatas = new ArrayList<>(); // 샘플 데이터 추가
//        mDatas.add(new Board("title","contents","time",20,10));
//        mDatas.add(new Board("title","contents","time",20,10));
//        mDatas.add(new Board("title","contents","time",20,10));
//        mDatas.add(new Board("title","contents","time",20,10));
//        mDatas.add(new Board("title","contents","time",20,10));

        getGroups();
//        Log.d("MainActivity", "onCreate - onClick : " + mDatas);

        // Adapter, LayoutManager 연결

        mPostRecyclerView.setAdapter(mAdapter);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getGroups() {
        //현재 사용자의 UID를 가져옴
//        String userId = firebaseAuth.getCurrentUser().getUid();

        //사용자가 포함된 그룹 정보 접근
        DatabaseReference userRef = database.child("clubs");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatas.clear();

                //데이터 스냅샷을 순회하며 그룹명을 가져와 리스트에 추가
                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    String groupName = groupSnapshot.getKey();
                    DatabaseReference groupRef = groupSnapshot.getRef();
                    groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //그룹명 가져오기
                            String groupContents = dataSnapshot.child("onelistsummary").getValue(String.class);
                            Log.d("MainActivity", "onCreate - onClick : " + groupContents);
                            //그룹명을 리스트에 추가
                            mDatas.add(new Board(groupName, groupContents,"time",20,10));
                            Log.d("MainActivity1", "onCreate - onClick : " + mDatas);

                            mAdapter.notifyDataSetChanged();
                        }

                        //데이터 읽기 실패할 경우 오류 발생
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Firebase", databaseError.getMessage());
                        }
                    });
                }

                Log.d("MainActivity2", "onCreate - onClick : " + mDatas);
            }

            //데이터 읽기 실패할 경우 오류 발생
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", databaseError.getMessage());
            }
        });

        Log.d("MainActivity3", "onCreate - onClick : " + mDatas);
    }

}