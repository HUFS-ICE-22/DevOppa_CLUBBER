package com.example.hackathon;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class Log_in extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    EditText editText_email,editText_Password;
    Button login_btn, signup_btn;

    FirebaseAuth mAuth=null;


    DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        firebaseAuth=FirebaseAuth.getInstance();
        FirebaseUser user=firebaseAuth.getCurrentUser();
        mAuth=FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference();

        signup_btn = findViewById(R.id.button_sign_in);
        editText_email= findViewById(R.id.editText_email);
        editText_Password= findViewById(R.id.editText_Password);



        //회원가입 엑티비티로 이동하는 버튼
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Sign_in.class);
                startActivity(intent);
            }
        });

        login_btn= findViewById(R.id.button_login);
        //로그인버튼 아이디와 비밀번호가 비어있는지 확인하고 로그인함수 호출
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!editText_email.getText().toString().equals("") && !editText_Password.getText().toString().equals("")) {
                    loginUser(editText_email.getText().toString(), editText_Password.getText().toString());
                } else {
                    Toast.makeText(Log_in.this, "계정과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {//인증정보가 변경돠면(로그인되면) 메뉴엑티비티로 이동
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent intent = new Intent(Log_in.this, ClubManagementActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                }
            }
        };

    }




    //일반로그인 처리함수 firebaseAuth의 인증기능을 사용함
    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Log_in.this, "로그인 성공", Toast.LENGTH_SHORT).show();

                            firebaseAuth.addAuthStateListener(firebaseAuthListener);
                        } else {
                            Toast.makeText(Log_in.this, "아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}