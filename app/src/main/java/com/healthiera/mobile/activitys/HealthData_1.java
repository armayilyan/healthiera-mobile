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

import com.healthiera.mobile.R;
import com.healthiera.mobile.activity.main.MainActivity;
import com.healthiera.mobile.adapter.HealthDataAdapter;

public class HealthData_1 extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health_data_1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Health Data");
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

        RecyclerView rv = (RecyclerView) findViewById(R.id.health_data_rv);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new HealthDataAdapter(context));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.health_data_1, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

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
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), HealthDataActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);
//
        }else if (id == R.id.health_data1) {

            drawer.closeDrawer(GravityCompat.START);


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
//
//
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
//

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
