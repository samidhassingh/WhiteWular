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
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class signSam extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signUpButton;
    EditText emailEditText2;
    EditText passwordEditText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_sam);
        mAuth = FirebaseAuth.getInstance();
        signUpButton=(Button)findViewById(R.id.signUpButton);
        emailEditText2=(EditText)findViewById(R.id.emailEditText2);
        passwordEditText2=(EditText)findViewById(R.id.passwordEditText2);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=emailEditText2.getText().toString().trim();
                String password=passwordEditText2.getText().toString().trim();
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(signSam.this, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(signSam.this, "Please enter password", Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(signSam.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    mAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                startActivity(new Intent(getApplicationContext(), logSam.class));
                                                Toast.makeText(signSam.this, "User Registered successfully, Please verify your email ID by clicking on the link sent to your Email Id.", Toast.LENGTH_SHORT).show();
                                            }else{
                                                Toast.makeText(signSam.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                                } else {

                                    Toast.makeText(signSam.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                                }

                            }
                        });
            }
        });
    }
}
