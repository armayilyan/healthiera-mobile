package com.healthiera.mobile.activitys.CarePlanActivityes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.github.clans.fab.FloatingActionButton;
import com.healthiera.mobile.R;
import com.healthiera.mobile.Schedule.Appointment;
import com.healthiera.mobile.Schedule.Treatment;
import com.healthiera.mobile.activitys.CarePlanActivity_1;
import com.healthiera.mobile.component.base.EventListItem;
import com.healthiera.mobile.entity.Measurement;
import com.healthiera.mobile.entity.Schedule;
import com.healthiera.mobile.entity.ScheduleTime;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.fragment.Event.EventItemVew;
import com.healthiera.mobile.fragment.Event.EventList;
import com.healthiera.mobile.serivce.ScheduleTimeService;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Apointment extends AppCompatActivity {
    Bundle bundle;
    ListView listview;
    List<ScheduleTime> scheduleList;
    TextView tvDate;
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
    com.healthiera.mobile.serivce.ScheduleTimeService ScheduleTimeService = new ScheduleTimeService();
    DateTime d;
    private com.github.clans.fab.FloatingActionButton add;
    private View pageThumb;
    private View pageThumb2;
    private Button buttonToday;
    private Button buttonTomorrow;
    private String eventType;
    private int activeDay = 1;

    public static Intent newIntent(Context context) {
        return new Intent(context,Apointment.class);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apointment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        eventType = getIntent().getStringExtra("name");
        getSupportActionBar().setTitle(eventType);
        scheduleList = ScheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate(), eventType);//"Appointment");

        listview = (ListView) findViewById(R.id.lvEventList);
        listview.setAdapter(new EventListItem(this, scheduleList));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Long eventId = scheduleList.get(position).getId();
                Bundle args = new Bundle();
                args.putString("EventType", eventType);
                args.putLong("EventId", eventId);
                args.putSerializable("Date", d.toDate());
                args.putInt("Time", scheduleList.get(position).getTime());
                EventItemVew eventItemVew = new EventItemVew();
                eventItemVew.setArguments(args);
                getSupportFragmentManager().beginTransaction()
//                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                        .setCustomAnimations(R.anim.slide_left, R.anim.slide_up)
                        .replace(R.id.Content_id_, eventItemVew).commit();
            }
        });
        d = new DateTime();
        tvDate = (TextView) findViewById(R.id.tvDate);
//        tvDate.setText(df.format(d));
        buttonToday = (Button) findViewById(R.id.btnToday);
        buttonTomorrow = (Button) findViewById(R.id.btnTomorrow);
        myViewPager();
        buttonToday.callOnClick();

        add = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.fab_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                switch (eventType) {
                    case "Appointment":
                        intent = new Intent(getApplicationContext(), Appointment.class);
                        break;
                    case "Medication":
                        intent = new Intent(getApplicationContext(), Medication.class);
                        break;
                    case "Measurement":
                        intent = new Intent(getApplicationContext(), Measurement.class);
                        break;
                    case "Treatment":
                    default:
                        intent = new Intent(getApplicationContext(), Treatment.class);
                        break;
                }
                intent.putExtra("day", activeDay);
                startActivity(intent);
            }
        });
        hideFab(0);
        showFab(300);




//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });



        EventList eventList = new EventList();
        int  position = 0;
        Bundle args = new Bundle();
        args.putString("EventType", EventType.values()[position].getEventName());
        eventList.setArguments(args);

//        getFragmentManager().beginTransaction().replace(R.id.content_id_apointment, eventList)
//                .addToBackStack(EventType.values()[position].getEventName()).commit();

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        listview.notifyAll();
    }

    @Override
    public void onBackPressed() {
       // super.onBackPressed();
        Intent i = new Intent(Apointment.this, CarePlanActivity_1.class);
        startActivity(i);
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }


    private void hideFab(int duration) {
        Animation scaleto0 = new ScaleAnimation(1f, 0, 1f, 0, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        scaleto0.setFillAfter(true);
        scaleto0.setDuration(duration);
        add.startAnimation(scaleto0);
        scaleto0.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                add.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void showFab(int duration) {
        Animation scaleto1 = new ScaleAnimation(0, 1f, 0, 1f, Animation.RELATIVE_TO_SELF, .5f, Animation.RELATIVE_TO_SELF, .5f);
        scaleto1.setFillAfter(true);
        scaleto1.setDuration(duration);
        add.startAnimation(scaleto1);
        scaleto1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                add.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private Schedule[] getEventListItems(String e, Date d) {
        DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        List<Schedule> el = new Select()
                .from(Schedule.class)
                .where("event_type = ?", e)
                //.orderBy("Name ASC")
                //.where("start_date_time >= ?", d)
                .execute();
        List<Schedule> evByDate = new ArrayList<>();
        for (Schedule ev : el) {
            // TODO replace the logic
            if (dateFormat.format(ev.getStartDate()).equals(dateFormat.format(d))) {
                evByDate.add(ev);
            }
        }
        return evByDate.toArray(new Schedule[evByDate.size()]);
    }

    private void myViewPager() {
        pageThumb = findViewById(R.id.thumb);
        pageThumb2 = findViewById(R.id.thumb2);

        buttonToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new DateTime();
                tvDate.setText(df.format(d.toDate()));
                scheduleList = ScheduleTimeService.getDateNotDoneScheduleTimes(d.toLocalDate(), eventType);
                listview.setAdapter(new EventListItem(getApplicationContext(), scheduleList));

                pageThumb.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                pageThumb2.setBackgroundColor(getResources().getColor(R.color.transparent));
                buttonToday.setTextColor(getResources().getColor(R.color.colorPrimary));
                buttonTomorrow.setTextColor(getResources().getColor(R.color.colorPrimaryFade));
                activeDay = 1;

            }
        });

        buttonTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new DateTime();
                d = d.plusDays(1);
                tvDate.setText(df.format(d.toDate()));
                scheduleList = ScheduleTimeService.getDateNotDoneScheduleTimes(d.toLocalDate(), eventType);
                listview.setAdapter(new EventListItem(getApplicationContext(), scheduleList));

                pageThumb.setBackgroundColor(getResources().getColor(R.color.transparent));
                pageThumb2.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                buttonTomorrow.setTextColor(getResources().getColor(R.color.colorPrimary));
                buttonToday.setTextColor(getResources().getColor(R.color.colorPrimaryFade));
                activeDay = 2;

            }
        });
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Bundle args = new Bundle();
//
//                args.putString("EventType", EventType.values()[position].getEventName());
//
//                EventList eventList = new EventList();
//
//                eventList.setArguments(args);
//
//                getFragmentManager().beginTransaction().replace(R.id.Content_id_, eventList)
//                        .addToBackStack(EventType.values()[position].getEventName()).commit();
//            }
//        });

    }

}
