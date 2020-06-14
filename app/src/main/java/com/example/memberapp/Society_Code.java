package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.memberapp.Model.Member_president_model;
import com.example.memberapp.Model.President_id_Model;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Society_Code extends AppCompatActivity{

    String president_userid;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReferencepresident_id;
    DatabaseReference member_president_id;
    EditText Society_code;
    Button Society_code_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__code);
        final String currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Society_code = findViewById(R.id.president_id_code);
        Society_code_btn = findViewById(R.id.president_id_code_btn);
        Society_code_btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(!Society_code.getText().toString().isEmpty()){
                    president_userid = Society_code.getText().toString();
                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("President").child(president_userid);

                   /* databaseReferencepresident_id.orderByChild("president_id").equalTo(president_userid)
                            .addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(Society_Code.this, "not exist", Toast.LENGTH_SHORT).show();
                                }
                            });
                   */
                    databaseReference.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(Society_Code.this, "Valid!!!", Toast.LENGTH_SHORT).show();
                                String CurrentUserId =FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Member_president_model member_president_model = new Member_president_model(president_userid);
                                member_president_id = FirebaseDatabase.getInstance().getReference("Member_president_id").child(currentuserId);
                                member_president_id.setValue(member_president_model);

                                Intent intent = new Intent(getApplicationContext(),Multiple_Society.class);
                                intent.putExtra("PresidentUserId", president_userid);
                                startActivity(intent);
                            }
                            else
                            {
                                Toast.makeText(Society_Code.this, "Society Code Not Valid!!!", Toast.LENGTH_SHORT).show();
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