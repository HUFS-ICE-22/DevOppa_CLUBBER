package com.example.hackathon;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ClubManagementActivity extends AppCompatActivity {

    private LinearLayout layoutClubOfficials;
    private Button buttonAddOfficial;
    private Button buttonSave;

    private int officialCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_management);

        layoutClubOfficials = findViewById(R.id.layoutClubOfficials);
        buttonAddOfficial = findViewById(R.id.buttonAddOfficial);
        buttonSave = findViewById(R.id.buttonSave);

        buttonAddOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOfficialInputFields();
            }
        });

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveClubInfo();
            }
        });

        // 초기에 1개의 동아리 임원 정보 입력 필드 생성
        addOfficialInputFields();
    }

    private void addOfficialInputFields() {
        officialCount++;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );

        LinearLayout layoutOfficial = new LinearLayout(this);
        layoutOfficial.setOrientation(LinearLayout.VERTICAL);
        layoutOfficial.setLayoutParams(layoutParams);

        EditText editTextName = new EditText(this);
        editTextName.setHint("이름");
        layoutOfficial.addView(editTextName);

        EditText editTextStudentNumber = new EditText(this);
        editTextStudentNumber.setHint("학번");
        layoutOfficial.addView(editTextStudentNumber);

        EditText editTextMajor = new EditText(this);
        editTextMajor.setHint("학과");
        layoutOfficial.addView(editTextMajor);

        layoutClubOfficials.addView(layoutOfficial);
    }

    private void saveClubInfo() {
        // 동아리 정보와 동아리 임원 정보를 저장하는 로직을 구현
        // 동아리 회장이 설정한 정보를 앱 내부에 저장하거나 서버에 업로드하는 등의 처리를 수행

        for (int i = 0; i < layoutClubOfficials.getChildCount(); i++) {
            LinearLayout layoutOfficial = (LinearLayout) layoutClubOfficials.getChildAt(i);
            EditText editTextName = (EditText) layoutOfficial.getChildAt(0);
            EditText editTextStudentNumber = (EditText) layoutOfficial.getChildAt(1);
            EditText editTextMajor = (EditText) layoutOfficial.getChildAt(2);

            String name = editTextName.getText().toString();
            String studentNumber = editTextStudentNumber.getText().toString();
            String major = editTextMajor.getText().toString();

            // 동아리 임원 정보를 저장하는 로직을 구현
        }
    }
}
