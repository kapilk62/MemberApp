package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.memberapp.Adapter.RuleList;
import com.example.memberapp.Model.Rule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Rules extends AppCompatActivity {
    EditText editTextRule;
    Button btnAddRule;
    DatabaseReference databaseRule;
    ListView listViewRules;
    List<Rule> ruleList;
    String currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
    String buildingId;

    static final String myPref = "myPref";
    static final String myPresidentId = "myPresidentIdKey";
    SharedPreferences sharedPreferences;
    String member_president_id;
    private static final String TAG = "1";
    String prsident_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);
        GlobalClass globalClass = (GlobalClass) getApplicationContext();
        buildingId = globalClass.getBuildingId();
        prsident_id = globalClass.getPresident_id();
        Log.d(TAG, "onCreate: " + prsident_id);

        databaseRule = FirebaseDatabase.getInstance().getReference("Rules").child(prsident_id).child(buildingId);

        //editTextRule = (EditText) findViewById(R.id.add_rule_txtfld);
        btnAddRule = findViewById(R.id.add_rule_btn);

        listViewRules = findViewById(R.id.listViewRules);
        ruleList = new ArrayList<>();

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: " + ruleList);
        databaseRule.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ruleList.clear();

                for (DataSnapshot ruleSnapshot : dataSnapshot.getChildren()) {
                    Rule rule = ruleSnapshot.getValue(Rule.class);

                    ruleList.add(rule);
                    Log.d(TAG, "onDataChange: " + ruleList);
                }
                RuleList adapter = new RuleList(Rules.this, ruleList);
                listViewRules.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void loadpresidentid() {
        sharedPreferences = getSharedPreferences(myPref, Context.MODE_PRIVATE);
        sharedPreferences.contains(myPresidentId);
        member_president_id = sharedPreferences.getString(myPresidentId, "");
        Log.d(TAG, "rulepresidentid: " + sharedPreferences.getString(myPresidentId, ""));
    }
}
