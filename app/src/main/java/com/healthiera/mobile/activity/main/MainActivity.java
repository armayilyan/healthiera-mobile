package com.healthiera.mobile.activity.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.healthiera.mobile.R;
import com.healthiera.mobile.activitys.CarePlanActivity_1;
import com.healthiera.mobile.activitys.EducationalTipsActivity;
import com.healthiera.mobile.activitys.GoalActivity;
import com.healthiera.mobile.activitys.HealthData_1;
import com.healthiera.mobile.activitys.MedicalIdActivity;
import com.healthiera.mobile.entity.AppointmentCode;
import com.healthiera.mobile.entity.Doctor;
import com.healthiera.mobile.entity.Goal;
import com.healthiera.mobile.entity.MedicationList;
import com.healthiera.mobile.entity.TreatmentCode;
import com.healthiera.mobile.fragment.Dashboard;
import com.healthiera.mobile.notification_service.EventNotifiService;
import com.healthiera.mobile.serivce.AppointmentCodeService;
import com.healthiera.mobile.serivce.DcotorService;
import com.healthiera.mobile.serivce.GoalService;
import com.healthiera.mobile.serivce.MedicationListService;
import com.healthiera.mobile.serivce.TreatmentCodeService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private String ACTIVE_FRAGMENT_KEY = "active_fragment";

    private int activeFragment;

    public static Intent newIntent(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(navigationView.getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        if (savedInstanceState != null) {
            activeFragment = savedInstanceState.getInt(ACTIVE_FRAGMENT_KEY);
        }


        EventNotifiService.setServiceAlarm(getApplicationContext(), true);

}

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {

            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ACTIVE_FRAGMENT_KEY, activeFragment);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onStart() {
        super.onStart();
        getSupportFragmentManager().beginTransaction().replace(R.id.Content_id_, new Dashboard()).commit();
//        openActiveFragment();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime5", false)) {
            // <---- run your one time code here
//            MedicationListService medicationListService = new MedicationListService();
//            medicationListService.loadAllMedicationList();

            List<Goal> goals =new ArrayList<Goal>();
            goals.add(new Goal(getString(R.string.goal1_name),""));
            goals.add(new Goal(getString(R.string.goal2_name),""));
            goals.add(new Goal(getString(R.string.goal3_name),""));
            goals.add(new Goal(getString(R.string.goal4_name),""));
            goals.add(new Goal(getString(R.string.goal5_name),""));
            goals.add(new Goal(getString(R.string.goal6_name),""));

            for (Goal item : goals)
            {
                (new GoalService()).createGoal(item);
            }

            DcotorService dcotorService = new DcotorService();
            Doctor d1 = new Doctor("David", "Endocrinologist", "Davitterar@gmail.com", "077458571");
            Doctor d2 = new Doctor("Karen", "Therapist", "Karen@gmail.com", "077458571");
            Doctor d3 = new Doctor("Armen", "Dentist", "Armen@gmail.com", "077458571");
            dcotorService.createDoctor(d1);
            dcotorService.createDoctor(d2);
            dcotorService.createDoctor(d3);

            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    readAppointments();
                    readTreatments();
                    readMedications();
                }
            });
            t.start();

            // mark first time has runned.
            prefs.edit().putBoolean("firstTime5", true).commit();
        }
    }

    private void readAppointments() {

        AppointmentCodeService appointmentCodeService = new AppointmentCodeService();
        String[] RowData;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("appointment_codes.csv")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                RowData = line.split(",");
                AppointmentCode appointmentCode = new AppointmentCode();
                appointmentCode.setCode(trimString(RowData[0]));
                appointmentCode.setName(trimString(RowData[1]));
                appointmentCodeService.createAppointmentCode(appointmentCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readTreatments() {

        TreatmentCodeService treatmentCodeService = new TreatmentCodeService();
        String[] RowData;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("treatment_codes.csv")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                RowData = line.split(",");
                TreatmentCode treatmentCode = new TreatmentCode();
                treatmentCode.setCode(trimString(RowData[0]));
                treatmentCode.setName(trimString(RowData[1]));
                treatmentCodeService.createTreatmentCode(treatmentCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readMedications() {

        MedicationListService medicationListService = new MedicationListService();
        String[] RowData;

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("medication_names.csv")));
            String line = null;
            while ((line = reader.readLine()) != null) {
                MedicationList medicationList = new MedicationList();
                RowData = line.split(",");
                medicationList.setName(trimString(RowData[0]));
                medicationList.setManufacturer(trimString(RowData[1]));
                medicationList.setDose(RowData[2]);
                medicationListService.CreateMedicationList(medicationList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String trimString(String value) {
        String value1;
        value1 = value.replaceAll("'", "");
        value1 = value1.replaceAll("\"", "");

        return value1;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Handler handler = new Handler();
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.dashboard) {

            drawer.closeDrawer(GravityCompat.START);


        } else if (id == R.id.health_data1) {

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
