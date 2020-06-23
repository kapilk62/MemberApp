package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Rules_new extends AppCompatActivity {

    DatabaseReference databaseRule;
    ListView listViewRulesNew;
    String currentuserId;
    ArrayList<String> ruleList = new ArrayList<>();
    private static final String TAG = "1";
    String buildingId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules_new);
        final ArrayAdapter ruleListAdapter = new ArrayAdapter<String>(Rules_new.this,android.R.layout.simple_list_item_1,ruleList);
        listViewRulesNew = findViewById(R.id.listviewRulesNew);
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        buildingId = globalClass.getBuildingId();
        Log.d(TAG, "onCreate: "+buildingId);

        currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseRule = FirebaseDatabase.getInstance().getReference("Rules").child(currentuserId).child(buildingId);

        databaseRule.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String rule = dataSnapshot.getValue(String.class);
                ruleList.add(rule);
                final ArrayAdapter ruleListAdapter = new ArrayAdapter<String>(Rules_new.this,android.R.layout.simple_list_item_1,ruleList);
                listViewRulesNew.setAdapter(ruleListAdapter);

                Log.d(TAG, "onChildAdded: "+ruleList);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}