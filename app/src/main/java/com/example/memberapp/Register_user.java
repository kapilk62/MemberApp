package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;

import java.util.concurrent.TimeUnit;

public class Register_user extends AppCompatActivity {

    public static final String TAG = "number";
    FirebaseAuth fAuth;
    EditText phoneNumber, codeEnter;
    Button nextBtn;
    ProgressBar progressBar;
    TextView state;
    CountryCodePicker countryCodePicker;
    String verficationId;
    PhoneAuthProvider.ForceResendingToken token;
    Boolean verificationInProgress = false;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        fAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        phoneNumber = findViewById(R.id.phone);
        codeEnter = findViewById(R.id.codeEnter);
        nextBtn = findViewById(R.id.nextBtn);
        progressBar = findViewById(R.id.progressBar);
        state = findViewById(R.id.state);
        countryCodePicker = findViewById(R.id.ccp);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!verificationInProgress) {

                    if (!phoneNumber.getText().toString().isEmpty() && phoneNumber.getText().toString().length() == 10) {
                        String phoneNum = "+" + countryCodePicker.getSelectedCountryCode() + phoneNumber.getText().toString();
                        progressBar.setVisibility(View.GONE);
                        state.setVisibility(View.GONE);
                        codeEnter.setVisibility(View.VISIBLE);
                        requestOTP(phoneNum);


                    } else {
                        phoneNumber.setError("Phone Number is Not Valid!!");
                    }
                } else {
                    String userOTP = codeEnter.getText().toString();
                    if (!userOTP.isEmpty() && userOTP.length() == 6) {
                        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verficationId, userOTP);
                        verifyAuth(credential);

                    } else {
                        codeEnter.setError("Valid OTP is Required.");
                    }

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (fAuth.getCurrentUser() != null) {
            progressBar.setVisibility(View.VISIBLE);
            state.setText("Checking...");
            state.setVisibility(View.VISIBLE);
            checkUserProfile();
        } else {

        }
    }

    private void checkMemberPresident() {
        String currentUserId = fAuth.getCurrentUser().getUid();
        DatabaseReference member_president_id = FirebaseDatabase.getInstance().getReference("Member_president_id").child(currentUserId);
        member_president_id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    checkSocietyId();
                    //startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    //finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), Society_Code.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkSocietyId() {
        String currentUserId = fAuth.getCurrentUser().getUid();
        DatabaseReference member_president_id = FirebaseDatabase.getInstance().getReference("Member_society_id").child(currentUserId);
        member_president_id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), Multiple_Society.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void checkUserProfile() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("Member").child(FirebaseAuth.getInstance().getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    checkMemberPresident();
                    //startActivity(new Intent(getApplicationContext(),Society_Code.class));
                    //finish();
                } else {
                    startActivity(new Intent(getApplicationContext(), User_Info.class));
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void verifyAuth(PhoneAuthCredential credential) {
        fAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkUserProfile();
                } else {
                    Toast.makeText(Register_user.this, "Authentication failed!!!!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void requestOTP(String phoneNum) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNum, 60L, TimeUnit.SECONDS, this, new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verficationId = s;
                token = forceResendingToken;
                nextBtn.setText("Verify");
                verificationInProgress = true;

            }

            @Override
            public void onCodeAutoRetrievalTimeOut(@NonNull String s) {
                super.onCodeAutoRetrievalTimeOut(s);
                Toast.makeText(Register_user.this, "OTP Expired , Re-Request the OTP", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                verifyAuth(phoneAuthCredential);

            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(Register_user.this, "Cannot Create Account" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }
}