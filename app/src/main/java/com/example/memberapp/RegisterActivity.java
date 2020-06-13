package com.example.memberapp;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.memberapp.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;



public class RegisterActivity extends AppCompatActivity {
    EditText mFirstname,mLastname,mEmail,mPassword,mMobileno;
    Button mRegisterbutton;
    TextView mLoginhere;
    FirebaseAuth fAuth;
    private static final String TAG = "RegisterActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mFirstname= findViewById(R.id.firstname);
        mLastname =findViewById(R.id.lastname);
        mEmail = findViewById(R.id.emailid);
        mPassword = findViewById(R.id.paswd);
        mMobileno = findViewById(R.id.mobileno);
        mLoginhere=findViewById(R.id.createtext);

        mRegisterbutton = findViewById(R.id.registerbutton);

        fAuth= FirebaseAuth.getInstance();


        if (fAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        mRegisterbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=mEmail.getText().toString().trim();
                String password=mPassword.getText().toString().trim();
                final String fname=mFirstname.getText().toString().trim();
                final String lname=mLastname.getText().toString().trim();
                final String m_no=mMobileno.getText().toString().trim();

                if(TextUtils.isEmpty(email)){
                    mEmail.setError("Email is Required");
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    mPassword.setError("Password is Required");
                    return;
                }
                if (password.length() < 8) {
                    mPassword.setError("Password must be 8 or more Characters");
                    return;
                }

                if(TextUtils.isEmpty(m_no)){
                    mMobileno.setError("Mobile Number is Required");
                    return;
                }
                if (m_no.length() != 10 ) {
                    mMobileno.setError("Mobile Number Must be 10 digit !!");
                    return;
                }

                if(TextUtils.isEmpty(fname)){
                    mFirstname.setError("First Name is Required");
                    return;
                }
                if(TextUtils.isEmpty(lname)){
                    mLastname.setError("Last Name is Required");
                    return;
                }



                fAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            User userm = new User(
                                    fname,
                                    lname,
                                    email,
                                    m_no
                            );
                            FirebaseDatabase.getInstance().getReference("Users").child("Member")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(userm).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful()) {
                                        Toast.makeText(RegisterActivity.this, "User is Created", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(RegisterActivity.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            FirebaseUser user=fAuth.getCurrentUser();
                            user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this,"Verification Email has been Sent !",Toast.LENGTH_SHORT);

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: Email not sent " + e.getMessage());

                                }
                            });

                            Toast.makeText(RegisterActivity.this, "User is Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),LoginActivity.class));

                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

        mLoginhere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser()!=null){
            //login user

        }
    }
}
