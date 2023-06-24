package com.example.hackathon;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ManageClub extends AppCompatActivity {

    private EditText editTextClubName;
    private EditText editTextClubDescription;
    private EditText editTextContactInfo;
    private Button buttonUpdate;

    private DatabaseReference clubRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_club);

        editTextClubName = findViewById(R.id.editTextClubName);
        editTextClubDescription = findViewById(R.id.editTextClubDescription);
        editTextContactInfo = findViewById(R.id.editTextContactInfo);
        buttonUpdate = findViewById(R.id.buttonUpdate);

        clubRef = FirebaseDatabase.getInstance().getReference().child("clubs");

        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateClubInfo();
            }
        });

        // 동아리 정보를 표시하는 메소드 호출
        displayClubInfo();
    }

    private void displayClubInfo() {
    }

    private void updateClubInfo() {
        String clubName = editTextClubName.getText().toString();
        String clubDescription = editTextClubDescription.getText().toString();
        String contactInfo = editTextContactInfo.getText().toString();

        DatabaseReference clubRef = FirebaseDatabase.getInstance().getReference().child("clubs").child(clubName);

        clubRef.child("name").setValue(clubName);
        clubRef.child(clubName).child("description").setValue(clubDescription);
        clubRef.child(clubName).child("contactInfo").setValue(contactInfo);



        Toast.makeText(this, "동아리 정보가 업데이트되었습니다.", Toast.LENGTH_SHORT).show();
    }
}
