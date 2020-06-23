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
    private String buildingId,president_id;

    public String getBuildingId() {
        return buildingId;
    }

    public String getPresident_id() {return president_id;}

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public  void  setPresident_id(String president_id) { this.president_id =president_id;}
}
