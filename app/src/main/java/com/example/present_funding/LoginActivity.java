package com.example.present_funding;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends Activity {

    Button goJoin, goMain, findPwd;
    EditText Idtxt, Pwdtxt;
    //Firebase에서 계정 정보를 가져오는 객체
    private FirebaseAuth firebaseAuth;

    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    //구글 로그인을 위한 객체

    DatabaseReference mDatabase;

    SharedPreferences.Editor editor;

    //onActivityResultCode 를 위한것
    private static final int RC_SIGN_IN = 9001;

    private static final int REQ_ONE_TAP = 2;  // Can be any integer unique to the Activity.
    private boolean showOneTapUI = true;

    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        goJoin = findViewById(R.id.btn_join);
        goMain = findViewById(R.id.btn_login);
        findPwd = findViewById(R.id.btn_findPwd);

        Idtxt = findViewById(R.id.Id);
        Pwdtxt = findViewById(R.id.Pwd);


        SharedPreferences sharedPreferences = getSharedPreferences("sFile", MODE_PRIVATE);
        editor = sharedPreferences.edit();

        //로그인 버튼이 눌리면
        goMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id, pwd;

                if (!Idtxt.getText().toString().equals("") && !Pwdtxt.getText().toString().equals("")) {
                    id = Idtxt.getText().toString().trim();
                    pwd = Pwdtxt.getText().toString().trim();
                    loginUser(id, pwd);
                } else {
                    Toast.makeText(LoginActivity.this, "이메일과 비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });

        //회원가입 버튼이 눌리면
        goJoin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //intent함수를 통해 register액티비티 함수를 호출한다.
                startActivity(new Intent(getApplication(), JoinActivity.class));

            }
        });

        //비밀번호 찾기 버튼이 눌리면
        findPwd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplication(), FindActivity.class));

            }
        });

    }

    private void readUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser(); //로그인한 유저의 정보 가져오기
        String uid = user != null ? user.getUid() : null;

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("Users").child(uid).child("userType").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                Intent intent;
//                Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
//                intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//                finish();
                editor.putString("userType", snapshot.getValue(String.class));
                editor.commit();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    public void loginUser(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            if (user.isEmailVerified()) {
                                Intent intent;
                                Toast.makeText(LoginActivity.this, "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show();
                                readUser();
                                intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "이메일 인증이 필요합니다.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(LoginActivity.this, "이메일 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show();
                        }

                    }

                });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    private BackPressCloseHandler backPressCloseHandler;
    //뒤로가기 추가
    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }
}

