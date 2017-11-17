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
import android.view.Menu;
import android.view.MenuItem;

import com.healthiera.mobile.R;
import com.healthiera.mobile.activity.main.MainActivity;
import com.healthiera.mobile.entity.AppointmentCode;
import com.healthiera.mobile.fragment.CarePlan;
import com.healthiera.mobile.serivce.AppointmentCodeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class CarePlanActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_plan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        getSupportFragmentManager().beginTransaction().replace(R.id.Content_care_plan_id_, new CarePlan()).commit();

//        Thread t = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                readAppointments();
////                readTreatments();
////                readMedications();
//            }
//        });
//        t.start();

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.care_plan, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i = new Intent(this.getApplication(),MainActivity.class);
            startActivity(i);

        }
    }

    private void readAppointments() {

        AppointmentCodeService appointmentCodeService = new AppointmentCodeService();
        String[] RowData;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("appointment_codes.csv")));
            String line = null;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                RowData = line.split(",");
                AppointmentCode appointmentCode = new AppointmentCode();
                appointmentCode.setCode(RowData[0]);
                appointmentCode.setName(RowData[1]);
                appointmentCodeService.createAppointmentCode(appointmentCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
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

        }
//        else if (id == R.id.health_data) {
//
//            drawer.closeDrawer(GravityCompat.START);
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), HealthDataActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);
//
//        }
        else if (id == R.id.health_data1) {

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


        }
//        else if (id == R.id.care_plan_activity) {
//
//            drawer.closeDrawer(GravityCompat.START);
//        }
        else if (id == R.id.medical_id) {

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


        }
//        else if (id == R.id.health_data) {
//            drawer.closeDrawer(GravityCompat.START);
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), EducationalTipsActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);
//
//        }

        return true;
    }
}
