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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class Log_in extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    EditText editText_email,editText_Password;
    Button login_btn, signup_btn, button;

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
        button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ClubManagementActivity.class);
                startActivity(intent);
                finish();
            }
        });

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

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    String userId = user.getUid();

                    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("users");
                    DatabaseReference currentUserRef = usersRef.child(userId);

                    currentUserRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                int clubPresident = snapshot.child("club_president").getValue(Integer.class);
                                if (clubPresident == 1) {
//                                    Intent intent = new Intent(Log_in.this, MenuActivity.class);
//                                    startActivity(intent);
//                                    finish();
                                } else {
                                    Intent intent = new Intent(Log_in.this, BoardActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            // 처리 중 오류가 발생한 경우 호출됩니다.
                        }
                    });
                } else {
                    // 로그아웃 상태 처리
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