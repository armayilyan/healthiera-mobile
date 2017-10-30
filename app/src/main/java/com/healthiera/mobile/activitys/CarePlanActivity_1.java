package com.healthiera.mobile.activitys;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.github.clans.fab.FloatingActionMenu;
import com.healthiera.mobile.R;
import com.healthiera.mobile.Schedule.Appointment;
import com.healthiera.mobile.Schedule.Measurement;
import com.healthiera.mobile.Schedule.Medication;
import com.healthiera.mobile.Schedule.Treatment;
import com.healthiera.mobile.activity.main.MainActivity;
import com.healthiera.mobile.adapter.CarePlanRVadapter;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.entity.model.EventItemModel;


public class CarePlanActivity_1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    final String KEY = "key_for_fab";
    Context context;
    boolean flag = false;
    FloatingActionMenu menu;
    CarePlanRVadapter adapter;


    private EventItemModel[] getEventitems() {
        EventItemModel[] items = new EventItemModel[EventType.values().length];
        int index = 0;
        for (EventType dir : EventType.values()) {
            items[index] = new EventItemModel(dir.getEvent(), dir.getEventName(), dir.getEventIcon());
            index++;
        }
        return items;
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.fab_treatment_care:
                intent = new Intent(CarePlanActivity_1.this, Treatment.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.fab_appointment_care:
                intent = new Intent(CarePlanActivity_1.this, Appointment.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.fab_medication_care:
                intent = new Intent(CarePlanActivity_1.this, Medication.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.fab_measurement_care:
                intent = new Intent(CarePlanActivity_1.this, Measurement.class);
                startActivityForResult(intent, 1000);
                break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_plan_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Care Plan");
        context = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
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

        final View outside = findViewById(R.id.outside1);

        menu = (FloatingActionMenu) findViewById(R.id.fab_menu_care);

        final com.github.clans.fab.FloatingActionButton fabTreatment =
                (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_treatment_care);
        final com.github.clans.fab.FloatingActionButton fabAppointment =
                (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_appointment_care);
        final com.github.clans.fab.FloatingActionButton fabMedication =
                (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_medication_care);
        final com.github.clans.fab.FloatingActionButton fabMeasurement =
                (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_measurement_care);

        fabAppointment.setOnClickListener(this);
        fabMeasurement.setOnClickListener(this);
        fabMedication.setOnClickListener(this);
        fabTreatment.setOnClickListener(this);


        if (savedInstanceState == null || !savedInstanceState.containsKey(KEY)) {

        } else {
            flag = savedInstanceState.getBoolean(KEY);
            if (flag) {
                menu.open(true);
            }
        }

        menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened)
                    outside.setVisibility(View.VISIBLE);

                else
                    outside.setVisibility(View.GONE);
            }
        });

        outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.close(true);
            }
        });

        RecyclerView rv = (RecyclerView) findViewById(R.id.care_recycler_view);
        rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CarePlanRVadapter(context, getEventitems());
        rv.setAdapter(adapter);

//        getSupportFragmentManager().beginTransaction().replace(R.id.Content_care_plan_id_, new CarePlan()).commit();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(KEY, menu.isOpened());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (menu.isOpened()) {
            menu.close(true);
        } else {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                super.onBackPressed();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
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
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), CarePlanActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);
//
//
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
//
//        }
        else if (id == R.id.educational_tips) {
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
