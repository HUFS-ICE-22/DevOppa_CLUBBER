package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClubDetailsActivity extends AppCompatActivity {

    private TextView textViewClubName;
    private TextView textViewClubDescription;
    private ImageView imageViewClubMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_details);

        // XML 레이아웃 파일에서 UI 요소 초기화
        textViewClubName = findViewById(R.id.textViewClubName);
        textViewClubDescription = findViewById(R.id.textViewClubDescription);
        imageViewClubMain = findViewById(R.id.imageViewClubMain);

        // Intent에서 동아리 정보 및 이미지 URI 추출
        Intent intent = getIntent();
        String clubName = intent.getStringExtra("club_name");
        String clubDescription = intent.getStringExtra("club_description");
        Uri clubMainImageUri = intent.getParcelableExtra("club_main_image_uri");

        // UI 요소에 동아리 정보 설정
        textViewClubName.setText(clubName);
        textViewClubDescription.setText(clubDescription);
        imageViewClubMain.setImageURI(clubMainImageUri);

    }
}

