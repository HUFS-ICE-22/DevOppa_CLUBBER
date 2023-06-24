package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminMenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        Button button_registerClub = findViewById(R.id.button_registerClub);
        Button button_myProfile = findViewById(R.id.button_myProfile);
        Button button_manageClub = findViewById(R.id.button_manageClub);
        Button button_logout = findViewById(R.id.button_logout);
//        Button button_find_null_time = findViewById(R.id.button_find_null_time);
//        Button btn_temporary = findViewById(R.id.btn_temporary);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            String uid=user.getUid();
            Log.d("uid",uid);
        }


        button_registerClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(AdminMenuActivity.this, ClubManagementActivity.class);
                startActivity(myIntent);
                finish();
            }
        });

        button_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this, myprofile1.class);
                startActivity(intent);
            }
        });

//        btn_temporary.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Menu.this, GroupList.class);
//                startActivity(intent);
//            }
//        });

        button_manageClub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this, ManageClub.class);
                startActivity(intent);
                finish();
            }
        });

        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminMenuActivity.this, Log_in.class);
                startActivity(intent);
                finish();
            }
        });

//        button_find_null_time.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Menu.this, FindNullTime.class);
//                startActivity(intent);
//                finish();
//            }
//        });
    }
}
