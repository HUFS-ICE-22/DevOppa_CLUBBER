package com.example.hackathon;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.auth.FirebaseAuth;

public class ClubManagementActivity extends AppCompatActivity {

    private LinearLayout layoutClubOfficials;
    private Button buttonAddOfficial;
    private Button buttonChooseImage;
    private Button buttonSave;
    private FirebaseAuth firebaseAuth;
    private Button buttonRemoveOfficial;
    private Uri selectedImageUri;
    private ImageView imageViewMainImage;
    private String clubName;
    private String clubDetail;
    private Switch switchInternal;
    private Switch switchVolunteer;

    private boolean isInternal; // 교내 동아리 여부
    private boolean isVolunteer; // 봉사 동아리 여부

    private int REQUEST_IMAGE_GALLERY;

    private int officialCount = 0;

    private int count = 0;

    DatabaseReference officialRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_management);

        layoutClubOfficials = findViewById(R.id.layoutClubOfficials);
        buttonAddOfficial = findViewById(R.id.buttonAddOfficial);
        buttonChooseImage = findViewById(R.id.buttonChooseImage);
        buttonRemoveOfficial = findViewById(R.id.buttonRemoveOfficial);
        imageViewMainImage = findViewById(R.id.imageViewMainImage);
        buttonSave = findViewById(R.id.buttonSave);
        switchInternal = findViewById(R.id.switchInternal);
        switchVolunteer = findViewById(R.id.switchVolunteer);

        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();

        switchInternal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isInternal = isChecked;
            }
        });

        // 봉사 동아리 토글 상태 변경 리스너
        switchVolunteer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isVolunteer = isChecked;
            }
        });

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
                buttonRemoveOfficial.setVisibility(View.VISIBLE);
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
                Intent detailsIntent = new Intent(ClubManagementActivity.this, BoardActivity.class);

                startActivity(detailsIntent);
                finish();
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
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference clubRef = database.getReference("clubs");


        String userId = firebaseAuth.getCurrentUser().getUid();


        clubName = ((EditText) findViewById(R.id.editTextClubNameActivity)).getText().toString();
        clubDetail = ((EditText) findViewById(R.id.editTextClubActivity)).getText().toString();


            DatabaseReference membersRef = FirebaseDatabase.getInstance().getReference().child("users");

            for (int i = 0; i < layoutClubOfficials.getChildCount(); i++) {

//                officialRef = clubRef.child(clubName);

                LinearLayout layoutOfficial = (LinearLayout) layoutClubOfficials.getChildAt(i);
                EditText editTextName = (EditText) layoutOfficial.getChildAt(1);
                EditText editTextStudentNumber = (EditText) layoutOfficial.getChildAt(2);
                EditText editTextMajor = (EditText) layoutOfficial.getChildAt(3);

                String name = editTextName.getText().toString();
                String studentNumber = editTextStudentNumber.getText().toString();
                String major = editTextMajor.getText().toString();

                membersRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot memberSnapshot : dataSnapshot.getChildren()) {
                            String uid = memberSnapshot.getKey();

                            String users_name = memberSnapshot.child("name").getValue(String.class);
                            String users_studentID = memberSnapshot.child("studentID").getValue(String.class);
                            String users_major = memberSnapshot.child("major").getValue(String.class);

                            if (name.equals(users_name) && studentNumber.equals(users_studentID) && major.equals(users_major)) {


                                clubRef.child(clubName).child(uid).setValue("1");

                            } else {
                                Log.d("adlsfjlaskdfjlaksdfjas;dlfjdasl;fj", clubName);
                            }
                            count++;
                        }
                        if (count == dataSnapshot.getChildrenCount()) {
                            //                        DatabaseReference categoriesRef = clubRef.child(clubName).child("categories");
                            clubRef.child(clubName).child("categories").child("internal").setValue(isInternal);
                            clubRef.child(clubName).child("categories").child("volunteer").setValue(isVolunteer);
                            Log.d("check", userId);
                            clubRef.child(clubName).child("활동내용").setValue(clubDetail);
                            clubRef.child(clubName).child(userId).setValue("1");

                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

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
                selectedImageUri = data.getData();
                imageViewMainImage.setImageURI(selectedImageUri);
            }
        }
    }
}
