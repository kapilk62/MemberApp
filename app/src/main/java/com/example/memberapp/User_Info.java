package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memberapp.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Info extends AppCompatActivity {

    EditText firstname, lastname, email;
    Button saveBtn;
    FirebaseAuth firebaseAuth;
    String userID;
    DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__info);
        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        email = findViewById(R.id.emailid);
        saveBtn = findViewById(R.id.saveuser_btn);
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("Member").child(userID);
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!firstname.getText().toString().isEmpty() && !lastname.getText().toString().isEmpty() && !email.getText().toString().isEmpty()) {
                    String firstName = firstname.getText().toString();
                    String lastName = lastname.getText().toString();
                    String userEmail = email.getText().toString();
                    String userInfoId = databaseReference.getKey();

                    User user = new User(firstName, lastName, userEmail);
                    databaseReference.setValue(user);

                    /*startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();*/
                    checkMemberPresident();

                } else {
                    Toast.makeText(User_Info.this, "All Field Are Required!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void checkSocietyId() {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
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

    private void checkMemberPresident() {
        String currentUserId = firebaseAuth.getCurrentUser().getUid();
        DatabaseReference member_president_id = FirebaseDatabase.getInstance().getReference("Member_president_id").child(currentUserId);
        member_president_id.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                   checkSocietyId();
                    // startActivity(new Intent(getApplicationContext(), MainActivity.class));
                  //  finish();
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

   /* @Override
    protected void onStart() {
        super.onStart();
        if (firebaseAuth.getCurrentUser()!=null){
            checkMemberPresident();
        }else {

        }
    }*/
}