package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class ClubDetailsActivity extends AppCompatActivity {

    private TextView textViewClubName;
    private TextView textViewClubDescription;
    private ImageView imageViewClubMain;

    private DatabaseReference database;
    private FirebaseAuth firebaseAuth;

    private Button go_home;
    private Button go_resister;
    private DatabaseReference clubRef;
    private String clubName;
    private String clubDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        // XML 레이아웃 파일에서 UI 요소 초기화
        textViewClubName = findViewById(R.id.textViewClubName);
        textViewClubDescription = findViewById(R.id.textViewClubDescription);
        imageViewClubMain = findViewById(R.id.imageViewClubMain);
        go_home = findViewById(R.id.go_home);
        go_resister = findViewById(R.id.go_register);

        // Intent에서 동아리 정보 및 이미지 URI 추출
//        Intent intent = getIntent();
//        clubName = intent.getStringExtra("groupname");
        clubName = "hard";

        database = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();

        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                startActivity(intent);
                finish();
            }
        });

//        go_home.setOnClickListener(new View.OnClickListener() {
        // Intent에서 동아리 정보 및 이미지 URI 추출
        Intent intent = getIntent();

        if (intent != null) {
            String clubName = intent.getStringExtra("clubName");
            String clubDescription = intent.getStringExtra("club_description");
            Uri clubMainImageUri = intent.getParcelableExtra("club_main_image_uri");

            textViewClubName.setText(clubName);
            textViewClubDescription.setText(clubDescription);
            imageViewClubMain.setImageURI(clubMainImageUri);

            DatabaseReference categoriesRef = FirebaseDatabase.getInstance().getReference().child("clubs").child(clubName).child("categories");
            categoriesRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        boolean isInternal = dataSnapshot.child("internal").getValue(Boolean.class);
                        boolean isVolunteer = dataSnapshot.child("volunteer").getValue(Boolean.class);

                        // 여기서 isInternal과 isVolunteer 변수를 활용하여 필요한 처리를 수행할 수 있습니다.

                    }
                    getClubs(clubName);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // 데이터베이스에서 categories 변수를 가져오는 도중 오류가 발생한 경우 처리할 내용
                }
            });


//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference clubRef = database.getReference().child("clubs").child(clubName);
//
//        clubRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), resister_club.class);
//                startActivity(intent);
//                finish();
//            }
//        });


        }
    }

    private void getClubs(String clubName) {
        database.child("clubs").child(clubName).child("clubDetail").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {//친구의 이름을 가져와서 출력
                Log.d("MainActivity", "ValueEventListener - onDataChange : " + (String) dataSnapshot.getValue());

                clubDetail = (String) dataSnapshot.getValue();

                textViewClubDescription.setText(clubDetail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {//실패시 에러메세지 출력
                Log.e("Firebase", "Error fetching friends: " + databaseError.getMessage());
            }
        });
    }
}


