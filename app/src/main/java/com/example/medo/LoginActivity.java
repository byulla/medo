package com.example.medo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    EditText mEmailText, mPasswordText;
    Button bt_login, bt_join;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //파이어베이스를 위한
        mAuth = FirebaseAuth.getInstance();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Users");

        mEmailText = findViewById(R.id.edtID);
        mPasswordText = findViewById(R.id.edtPW);
        bt_join = findViewById(R.id.btnJoin);
        bt_login = findViewById(R.id.btnLogin);

        bt_join.setOnClickListener(this);
        bt_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnJoin :
                gotoClass(JoinActivity.class);
                break;
            case R.id.btnLogin:
                String strPw = mPasswordText.getText().toString();
                String strEmail = mEmailText.getText().toString();
                //아이디와 비밀번호 둘 다 공백이 아닐 경우
                if (strPw.length() != 0 && strEmail.length() !=0) {
                    mAuth.signInWithEmailAndPassword(strEmail, strPw).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()) {
                                gotoClass(Menu.class);
                                finish();
                            } else {
                                Toast.makeText(LoginActivity.this, "아이디와 비밀번호가 맞지 않습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    Toast.makeText(LoginActivity.this, "아이디와 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + v);
        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}