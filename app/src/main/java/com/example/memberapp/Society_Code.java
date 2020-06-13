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

import com.example.memberapp.Model.President_id_Model;
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
                    databaseReferencepresident_id = FirebaseDatabase.getInstance().getReference("Users").child("Member").child(currentuserId);
                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("President").child(president_userid);
                    databaseReferencepresident_id.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            }else{
                                President_id_Model president_id_model =new President_id_Model(president_userid);
                                databaseReferencepresident_id.setValue(president_id_model);

                            }
                                                   }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    databaseReference.addValueEventListener(new ValueEventListener(){
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.exists()){
                                Toast.makeText(Society_Code.this, "Valid!!!", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(getApplicationContext(),Multiple_Society.class);
                                intent.putExtra("PresidentUserId", president_userid);
                                startActivity(intent);
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