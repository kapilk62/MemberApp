package com.example.memberapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerlayout;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.navigationView);

        mDrawerlayout = findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerlayout, R.string.open, R.string.close);
        mDrawerlayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        mNavigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (mToggle.onOptionsItemSelected(item)) {

            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sprofile) {
            //Intent i = new Intent(MainActivity.this,Profile.class);
            // startActivity(i);
        } else if (id == R.id.smy_bills) {
            // Intent i = new Intent(MainActivity.this,Join_Building_Page.class);
            // startActivity(i);
        } else if (id == R.id.semergency_number) {
            // Intent i = new Intent(MainActivity.this, Emergency_Number.class);
            //startActivity(i);
        } else if (id == R.id.sevents) {
            // Intent i = new Intent(MainActivity.this,Event.class);
            // startActivity(i);
        } else if (id == R.id.scomplaints) {
            //    Intent i = new Intent(MainActivity.this,Complaints.class);
            //  startActivity(i);
        } else if (id == R.id.swallet) {
            //Intent i = new Intent(MainActivity.this,multiple_building_page.class);
            //startActivity(i);
        } else if (id == R.id.smember) {

        } else if (id == R.id.svehicles) {

        } else if (id == R.id.sannouncements) {
            //Intent i = new Intent(MainActivity.this,Announcements.class);
            //startActivity(i);
        } else if (id == R.id.sbalance_sheet) {

        } else if (id == R.id.sdocument) {

        } else if (id == R.id.srules) {
            Intent i = new Intent(MainActivity.this,Rules.class);
            startActivity(i);
        } else if (id == R.id.snotification) {

        } else if (id == R.id.ssetting) {

        } else if (id == R.id.logoutbutton) {
            //FirebaseAuth.getInstance().signOut();
            //startActivity(new Intent(getApplicationContext(),LoginActivity.class));
            // finish();
        } else if (id == R.id.scontact_us) {
            //Intent i = new Intent(MainActivity.this, ContactsActivity.class);
            //startActivity(i);
        }

        DrawerLayout drawer1 = findViewById(R.id.drawer);
        drawer1.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {
            case R.id.bills:
                //  i = new Intent(this, Bills.class);
                //startActivity(i);
                break;
            case R.id.members:
                //i = new Intent(this, Members.class);
                //startActivity(i);
                break;
            case R.id.society_fund:
                // i = new Intent(this, Society_Fund.class);
                //startActivity(i);
                //break;
            case R.id.emergency_number:
                /*i = new Intent(this, Emergency_Number.class);
                startActivity(i);
                break;*/
            case R.id.my_building:
                //i = new Intent(this, My_Building.class);
                //startActivity(i);
                //break;
            case R.id.complaints:
                //i = new Intent(this, Complaints.class);
                //startActivity(i);
                // break;
            case R.id.vehicles:
                //i = new Intent(this, Vehicles.class);
                //startActivity(i);
                //break;
            case R.id.gatekeeper:
                //i = new Intent(this, Gate_Keeper.class);
                //startActivity(i);
                //break;
            default:
                break;

        }
    }

}
