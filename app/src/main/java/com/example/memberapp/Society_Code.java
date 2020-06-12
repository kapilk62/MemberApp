package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Society_Code extends AppCompatActivity{
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    EditText Society_code;
    Button Society_code_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__code);
        Society_code = findViewById(R.id.president_id_code);
        Society_code_btn = findViewById(R.id.president_id_code_btn);
        Society_code_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!Society_code.getText().toString().isEmpty()){
                    String president_userid = Society_code.getText().toString();
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("President").child(president_userid);
                    databaseReference.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(Society_Code.this, "Valid!!!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(Society_Code.this, "Not Valid!!!", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });

    }
}