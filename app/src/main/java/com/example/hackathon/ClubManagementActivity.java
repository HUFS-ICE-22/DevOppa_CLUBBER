package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ClubManagementActivity extends AppCompatActivity {

    private LinearLayout layoutClubOfficials;
    private Button buttonAddOfficial;
    private Button buttonChooseImage;
    private Button buttonSave;
    private Button buttonRemoveOfficial;
    private ImageView imageViewMainImage;
    private int REQUEST_IMAGE_GALLERY;

    private int officialCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_management);

        layoutClubOfficials = findViewById(R.id.layoutClubOfficials);
        buttonAddOfficial = findViewById(R.id.buttonAddOfficial);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        buttonRemoveOfficial = findViewById(R.id.buttonRemoveOfficial);

        buttonSave = findViewById(R.id.buttonSave);

        // 버튼 클릭 이벤트 핸들러
        buttonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 갤러리에서 사진을 선택하기 위한 인텐트 생성
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");

                // 갤러리 앱 호출 및 결과 처리를 위한 startActivityForResult 사용
                startActivityForResult(intent, REQUEST_IMAGE_GALLERY);
            }
        });

        buttonAddOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOfficialInputFields();
                buttonRemoveOfficial.setVisibility(View.VISIBLE); // 수정된 부분
            }
        });

        buttonRemoveOfficial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeOfficialInputFields();
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
                LinearLayout.LayoutParams.MATCH_PARENT
        );

        LinearLayout layoutOfficial = new LinearLayout(this);
        layoutOfficial.setOrientation(LinearLayout.VERTICAL);
        layoutOfficial.setLayoutParams(layoutParams);

        TextView titleText = new TextView(this);
        titleText.setText("임원 정보 추가");
        layoutOfficial.addView(titleText);

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
    private void removeOfficialInputFields() {
        if (officialCount > 0) {
            layoutClubOfficials.removeViewAt(officialCount - 1);
            officialCount--;

            // 모든 임원 정보 입력 필드를 삭제했을 경우, 삭제 버튼을 다시 숨깁니다
            if (officialCount == 0) {
                buttonRemoveOfficial.setVisibility(View.GONE);
            }
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            if (data != null) {
                Uri selectedImageUri = data.getData();

                // 선택한 이미지를 가져와서 사용할 수 있습니다.
                // 예를 들어, ImageView에 표시하거나 업로드 등의 작업을 수행할 수 있습니다.
                imageViewMainImage.setImageURI(selectedImageUri);
            }
        }
    }
}
