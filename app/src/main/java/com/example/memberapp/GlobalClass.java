package com.example.memberapp;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.memberapp.Model.Member_president_model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GlobalClass extends Application {
    DatabaseReference databaseReference_president_id;
    private String member_president_id;
    private static final String TAG = "1";
    String currentuserId;
    public String getMember_president_id(){
        currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference_president_id = FirebaseDatabase.getInstance().getReference("Member_president_id").child(currentuserId);
        databaseReference_president_id.addValueEventListener(new ValueEventListener(){
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Member_president_model member_president_model = dataSnapshot.getValue(Member_president_model.class);
                member_president_id = member_president_model.getMember_president_id();
                Log.d(TAG, "global: "+member_president_id);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("failed: " + databaseError.getCode());
            }
        });

        return member_president_id;}

    public void setMember_president_id(String member_president_id) {
        this.member_president_id = member_president_id;
    }
}
