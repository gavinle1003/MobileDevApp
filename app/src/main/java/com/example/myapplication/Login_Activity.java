package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

public class Login_Activity extends AppCompatActivity {
    EditText emailTxt, passwordTxt;
    Button registerBtn, loginBtn;
    ProgressBar progressBar;
    FirebaseAuth user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);

        registerBtn = findViewById(R.id.signUpBtn);
        loginBtn = findViewById(R.id.loginBtn);
        emailTxt = findViewById(R.id.emailLoginTxt);
        passwordTxt = findViewById(R.id.passwordLoginTxt);
        progressBar = findViewById(R.id.progressBar2);
        user = FirebaseAuth.getInstance();

        FirebaseUser userCheck = FirebaseAuth.getInstance().getCurrentUser();
        if (userCheck != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailTxt.getText().toString().trim();
                String password = passwordTxt.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    emailTxt.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passwordTxt.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    passwordTxt.setError("Password Must be >= 6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                user.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login_Activity.this, "Logged in Successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(Login_Activity.this, "Error ! " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), registerActivity.class));
                overridePendingTransition(R.anim.anim_slide_in_left, R.anim.anim_slide_out_left);
            }
        });
    }
}
