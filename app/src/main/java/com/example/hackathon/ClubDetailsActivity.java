package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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
    private Button go_home;
    private DatabaseReference clubRef;
    private String clubName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        // XML 레이아웃 파일에서 UI 요소 초기화
        textViewClubName = findViewById(R.id.textViewClubName);
        textViewClubDescription = findViewById(R.id.textViewClubDescription);
        imageViewClubMain = findViewById(R.id.imageViewClubMain);
        go_home = findViewById(R.id.go_home);

        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Board.class);
                startActivity(intent);
                finish();
            }
        });

        go_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), resister_club.class);
                startActivity(intent);
                finish();
            }
        });

        // Intent에서 동아리 정보 및 이미지 URI 추출
        Intent intent = getIntent();
        if (intent != null) {
            String clubName = intent.getStringExtra("club_name");
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
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                if (dataSnapshot.exists()) {
//                    // 데이터 스냅샷에서 필요한 정보를 추출하여 변수에 저장
//                    String name = dataSnapshot.child("name").getValue(String.class);
//                    String studentID = dataSnapshot.child("studentID").getValue(String.class);
//                    String major = dataSnapshot.child("major").getValue(String.class);
//
//                    // 변수에 저장된 정보 활용
//                    // ...
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                // 데이터베이스에서 정보를 가져오는 도중 오류가 발생한 경우 처리할 내용을 작성합니다.
//            }
//        });
        }
    }
}