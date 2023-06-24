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
    Button profileButton;


    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;
    List<Board> mDatas = new ArrayList<>();
    BoardAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        profileButton = findViewById(R.id.profileButton);

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), myprofile1.class);
                startActivity(intent);
                finish();
            }
        });

        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        RecyclerView mPostRecyclerView = findViewById(R.id.recyclerView);

        mAdapter = new BoardAdapter(mDatas);
        mAdapter.setOnItemClickListener(new BoardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Board item) {
                String groupName = item.getTitle();
                Intent intent = new Intent(BoardActivity.this,  ClubDetailsActivity.class);
                intent.putExtra("clubName", groupName);
                startActivity(intent);
            }
        });

        getGroups();

        mPostRecyclerView.setAdapter(mAdapter);
        mPostRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void getGroups() {
        DatabaseReference userRef = database.child("clubs");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mDatas.clear();

                for (DataSnapshot groupSnapshot : dataSnapshot.getChildren()) {
                    String groupName = groupSnapshot.getKey();
                    DatabaseReference groupRef = groupSnapshot.getRef();
                    groupRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String groupContents = dataSnapshot.child("onelistsummary").getValue(String.class);
                            mDatas.add(new Board(groupName, groupContents, "time", 20, 10));
                            mAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.e("Firebase", databaseError.getMessage());
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("Firebase", databaseError.getMessage());
            }
        });
    }
}