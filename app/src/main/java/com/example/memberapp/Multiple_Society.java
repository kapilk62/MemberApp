package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.memberapp.Model.CreateNewSocietyModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Multiple_Society extends AppCompatActivity implements View.OnClickListener ,NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "hello";
    private RecyclerView adminBuildingList;
    DatabaseReference databaseReference;
    CardView createNewSociety;
    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intent =getIntent();
        String President_UserId = intent.getStringExtra("PresidentUserId");
        Log.d(TAG, "onCreate: "+President_UserId);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple__society);
        String currentuserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference("New Building").child(President_UserId);
        databaseReference.keepSynced(true);

        //createNewSociety = findViewById(R.id.create_new_society_cardView);
        adminBuildingList = findViewById(R.id.recyclerViewAdmin);
        adminBuildingList.setHasFixedSize(true);
        adminBuildingList.setLayoutManager(new LinearLayoutManager(this));

        mDrawerlayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNavigationView = findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        //createNewSociety.setOnClickListener(this);

    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {

            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item){
        int id=item.getItemId();

       /* if(id==R.id.sprofile)
        {
            Intent i = new Intent(multiple_building_page.this,Profile.class);
            startActivity(i);
        }

        else if (id==R.id.logoutbutton)
        {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(),Register_user.class));
            finish();
        }

        else if(id==R.id.scontact_us)
        {
            Intent i = new Intent(multiple_building_page.this, ContactsActivity.class);
            startActivity(i);
        }*/

        DrawerLayout drawer1 =findViewById(R.id.drawer);
        drawer1.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<CreateNewSocietyModel, buildingAdminViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<CreateNewSocietyModel, buildingAdminViewHolder>
                (CreateNewSocietyModel.class, R.layout.cardview_admin, buildingAdminViewHolder.class, databaseReference) {
            @Override
            protected void populateViewHolder(buildingAdminViewHolder buildingAdminViewHolder, CreateNewSocietyModel createNewSocietyModel, final int i) {
                buildingAdminViewHolder.setBuildingName(createNewSocietyModel.getBuildingName());
                buildingAdminViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String buildingId = getRef(i).getKey();
                        Intent intent = new Intent(Multiple_Society.this, MainActivity.class);
                        intent.putExtra("BUILDINGID", buildingId);
                        startActivity(intent);
                    }
                });

            }
        };
        adminBuildingList.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    public static class buildingAdminViewHolder extends RecyclerView.ViewHolder {
        View view;

        public buildingAdminViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setBuildingName(String buildingName) {
            TextView building_name = view.findViewById(R.id.building_name_cardView);
            building_name.setText(buildingName);
        }
    }
}