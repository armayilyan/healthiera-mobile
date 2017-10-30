package com.healthiera.mobile.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.activity.main.MainActivity;

public class HealthDataActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {



    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            Intent intent;
            switch (view.getId()) {
                case R.id.tvComplainsandSymptoms:

                    intent = new Intent(HealthDataActivity.this, com.healthiera.mobile.activitys.healthData.Complains.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.left_out);

                    break;
                case R.id.tvHeredity:

                    intent = new Intent(HealthDataActivity.this, com.healthiera.mobile.activitys.healthData.Heredity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.left_out);

                    break;
                case R.id.tvStatus:

                    intent = new Intent(HealthDataActivity.this, com.healthiera.mobile.activitys.healthData.Status.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.left_out);


                    break;
                case R.id.tvSpecialDevices:

                    intent = new Intent(HealthDataActivity.this, com.healthiera.mobile.activitys.healthData.Devices.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.left_out);

                    break;
                case R.id.tvAnamnesisVitae:

                    intent = new Intent(HealthDataActivity.this, com.healthiera.mobile.activitys.healthData.AnamnesisVitae.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.right_in,R.anim.left_out);

                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final TextView tvComplainsandSymptoms = (TextView) findViewById(R.id.tvComplainsandSymptoms);
        final TextView tvHeredity = (TextView) findViewById(R.id.tvHeredity);
        final TextView tvStatus = (TextView) findViewById(R.id.tvStatus);
        final TextView tvSpecialDevices = (TextView) findViewById(R.id.tvSpecialDevices);
        final TextView tvAnamnesisVitae = (TextView) findViewById(R.id.tvAnamnesisVitae);


        tvComplainsandSymptoms.setOnClickListener(onClickListener);
        tvHeredity.setOnClickListener(onClickListener);
        tvStatus.setOnClickListener(onClickListener);
        tvSpecialDevices.setOnClickListener(onClickListener);
        tvAnamnesisVitae.setOnClickListener(onClickListener);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i = new Intent(this.getApplication(), MainActivity.class);
            startActivity(i);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Handler handler = new Handler();
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.dashboard) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), MainActivity.class);
                            startActivity(i);
                        }
                    }, 260L);

//        } else if (id == R.id.health_data) {
//
//            drawer.closeDrawer(GravityCompat.START);
//

        }else if (id == R.id.health_data1) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), HealthData_1.class);
                            startActivity(i);
                        }
                    }, 260L);

        } else if (id == R.id.care_plan_activity1) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), CarePlanActivity_1.class);
                            startActivity(i);
                        }
                    }, 260L);

        } else if (id == R.id.goals) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), GoalActivity.class);
                            startActivity(i);
                        }
                    }, 260L);


//        } else if (id == R.id.care_plan_activity) {
//
//            drawer.closeDrawer(GravityCompat.START);
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), CarePlanActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);


        } else if (id == R.id.medical_id) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), MedicalIdActivity.class);
                            startActivity(i);
                        }
                    }, 260L);


        } else if (id == R.id.educational_tips) {
            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), EducationalTipsActivity.class);
                            startActivity(i);
                        }
                    }, 260L);

//
//        } else if (id == R.id.health_data) {
//            drawer.closeDrawer(GravityCompat.START);
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), EducationalTipsActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);


        } else if (id == R.id.educational_tips) {
            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), EducationalTipsActivity.class);
                            startActivity(i);
                        }
                    }, 260L);


        }


        return true;
    }
}
