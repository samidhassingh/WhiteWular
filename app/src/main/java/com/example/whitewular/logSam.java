package com.example.whitewular;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class logSam extends AppCompatActivity {
    private FirebaseAuth mAuth;
    EditText emailEditText;
    EditText passwordEditText;
    Button logInButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_sam);
        mAuth = FirebaseAuth.getInstance();
        emailEditText=(EditText)findViewById(R.id.emailEditText);
        passwordEditText=(EditText)findViewById(R.id.passwordEditText);
        logInButton=(Button)findViewById(R.id.logInButton);
        logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEditText.getText().toString().trim();
                String password=passwordEditText.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(logSam.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(logSam.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(logSam.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    if(mAuth.getCurrentUser().isEmailVerified()){
                                        startActivity(new Intent(getApplicationContext(), billGen.class));
                                    }else{
                                        Toast.makeText(logSam.this, "Please verify your Email Id by clicking on the link sent to your registered Email Id", Toast.LENGTH_SHORT).show();
                                    }

                                } else {
                                    Toast.makeText(logSam.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
            }
        });
    }
}
