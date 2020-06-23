package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class Society_Code extends AppCompatActivity {

    String president_userid;
    private static final String TAG = "hello";
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    DatabaseReference member_president_id;
    EditText Society_code;
    Button Society_code_btn;
    SharedPreferences sharedPreferences;

    static final String myPref = "myPref";
    static final String myPresidentId = "myPresidentIdKey";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_society__code);
        final String currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        Society_code = findViewById(R.id.president_id_code);
        Society_code_btn = findViewById(R.id.president_id_code_btn);
        Society_code_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!Society_code.getText().toString().isEmpty()) {
                    president_userid = Society_code.getText().toString();

                    databaseReference = FirebaseDatabase.getInstance().getReference("Users").child("President").child(president_userid);

                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                Toast.makeText(Society_Code.this, "Valid!!!", Toast.LENGTH_SHORT).show();
                                //savepresidentid();
//                                GlobalClass globalClass = (GlobalClass) getApplicationContext();
//                                globalClass.setPresident_id(president_userid);
                                //String CurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                Member_president_model member_president_model = new Member_president_model(president_userid);
                                member_president_id = FirebaseDatabase.getInstance().getReference("Member_president_id").child(currentuserId);
                                member_president_id.setValue(member_president_model);
                                // savepresidentid();
                                Intent intent = new Intent(getApplicationContext(), Multiple_Society.class);
                                intent.putExtra("PresidentUserId", president_userid);
                                startActivity(intent);
                                finish();
                            } else {
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

    public void savepresidentid() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(myPresidentId, president_userid);
        editor.commit();
    }
}