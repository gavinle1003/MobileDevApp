package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;

import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registerActivity extends AppCompatActivity {
    public static final String TAG = "TAG";
    FirebaseFirestore fStore;
    FirebaseAuth user;
    EditText emailTxt, passWordTxt, userNameTxt;
    Button createBtn, backBtn;
    ProgressBar progressBar;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        user = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        emailTxt = findViewById(R.id.emailTxtBx);
        passWordTxt = findViewById(R.id.passWordTxtBx);
        userNameTxt = findViewById(R.id.userNameTxtBx);
        createBtn = findViewById(R.id.createBtn);
        backBtn = findViewById(R.id.backBtn);
        progressBar = findViewById(R.id.progressBar);


        FirebaseUser userCheck = FirebaseAuth.getInstance().getCurrentUser();
        if (userCheck != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = emailTxt.getText().toString().trim();
                String password = passWordTxt.getText().toString().trim();
                final String userName = userNameTxt.getText().toString();

                if (TextUtils.isEmpty(email)){
                    emailTxt.setError("Email is required");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    passWordTxt.setError("Password is required");
                    return;
                }
                if (password.length() < 6) {
                    passWordTxt.setError("Password Must be >= 6");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);

                user.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(registerActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                            userID = user.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("UserInformation").document(userID);
                            Map<String, Object> userMap = new HashMap<>();
                            userMap.put("UserName", userName);
                            userMap.put("Email", email);
                            documentReference.set(userMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: user Profile is created for " + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            progressBar.setVisibility(View.GONE);
                        }
                        else {
                            Toast.makeText(registerActivity.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            }
        });
    }

}
