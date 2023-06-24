package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class myprofile1 extends AppCompatActivity {

    private ImageView imageViewProfile;
    private TextView textViewName;
    private TextView textViewStudentID;
    private TextView textViewMajor;
    private TextView textViewEmail;
    private TextView textViewPhoneNumber;

    Button button;

    private DatabaseReference userRef;
    private FirebaseAuth mAuth;
    private String currentUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myprofile1);

        // XML 레이아웃 파일에서 UI 요소 초기화
        imageViewProfile = findViewById(R.id.imageViewProfile);
        textViewName = findViewById(R.id.textViewName);
        textViewStudentID = findViewById(R.id.textViewStudentID);
        textViewMajor = findViewById(R.id.textViewMajor);
        textViewEmail = findViewById(R.id.textViewEmail);

        button = findViewById(R.id.back);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        // 현재 사용자 ID 가져오기
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            currentUserID = currentUser.getUid();
        }

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        userRef = database.getReference().child("users").child(currentUserID);

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // 데이터 스냅샷에서 필요한 정보를 추출하여 변수에 저장
                    String name = dataSnapshot.child("name").getValue(String.class);
                    String studentID = dataSnapshot.child("studentID").getValue(String.class);
                    String major = dataSnapshot.child("major").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    // 값을 UI에 설정
                    textViewName.setText(name);
                    textViewStudentID.setText(studentID);
                    textViewMajor.setText(major);
                    textViewEmail.setText(email);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // 데이터베이스에서 정보를 가져오는 도중 오류가 발생한 경우 처리할 내용을 작성합니다.
            }
        });
    }
}
