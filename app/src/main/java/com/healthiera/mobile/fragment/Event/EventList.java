package com.healthiera.mobile.fragment.Event;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.github.clans.fab.FloatingActionButton;
import com.healthiera.mobile.R;
import com.healthiera.mobile.component.base.EventListItem;
import com.healthiera.mobile.entity.Schedule;
import com.healthiera.mobile.entity.ScheduleTime;
import com.healthiera.mobile.fragment.BaseFragment;
import com.healthiera.mobile.serivce.ScheduleTimeService;

import org.joda.time.DateTime;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author Davit Ter-Arakelyan
 * @date 02.10.2016
 */

public class EventList extends BaseFragment {
    Bundle bundle;
    ListView listview;
    List<ScheduleTime> scheduleList;
    TextView tvDate;
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
    ScheduleTimeService ScheduleTimeService = new ScheduleTimeService();
    DateTime d;
    private String carePlan;
    private FloatingActionButton add;

    public EventList() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.event_list, container, false);
        bundle = getArguments();
        carePlan = bundle.getString("EventType");
        scheduleList = ScheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate(), bundle.getString("EventType"));

        listview = (ListView) view.findViewById(R.id.lvEventList);
        listview.setAdapter(new EventListItem(getContext(), scheduleList));

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Long eventId = scheduleList.get(position).getId();

                Bundle args = new Bundle();
                args.putString("EventType", bundle.getString("EventType"));
                args.putLong("EventId", eventId);
                args.putSerializable("Date", d.toDate());
                args.putInt("Time", scheduleList.get(position).getTime());
                EventItemVew eventItemVew = new EventItemVew();
                eventItemVew.setArguments(args);
                getFragmentManager().beginTransaction().replace(R.id.Content_id_, eventItemVew).commit();
            }
        });
        d = new DateTime();
        tvDate = (TextView) view.findViewById(R.id.tvDate);
//        tvDate.setText(df.format(d));
        final Button btnToday = (Button) view.findViewById(R.id.btnToday);
        final Button btnTomorrow = (Button) view.findViewById(R.id.btnTomorrow);

        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new DateTime();
                tvDate.setText(df.format(d.toDate()));
                scheduleList = ScheduleTimeService.getDateNotDoneScheduleTimes(d.toLocalDate(), bundle.getString("EventType"));
                listview.setAdapter(new EventListItem(getContext(), scheduleList));
                btnToday.setTextColor(Color.parseColor("#ffffff"));
                btnToday.setBackgroundColor(Color.parseColor("#607d8b"));
                btnTomorrow.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnTomorrow.setBackgroundResource(R.drawable.btn_default);

            }
        });

        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                d = new DateTime();
                d = d.plusDays(1);
                tvDate.setText(df.format(d.toDate()));
                scheduleList = ScheduleTimeService.getDateNotDoneScheduleTimes(d.toLocalDate(), bundle.getString("EventType"));
                listview.setAdapter(new EventListItem(getContext(), scheduleList));
                btnTomorrow.setTextColor(Color.parseColor("#ffffff"));
                btnTomorrow.setBackgroundColor(Color.parseColor("#607d8b"));
                btnToday.setTextColor(getResources().getColor(R.color.colorPrimary));
                btnToday.setBackgroundResource(R.drawable.btn_default);
            }
        });
        add = (FloatingActionButton) view.findViewById(R.id.fab_add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (carePlan) {
                    case "Appointment":
                        Intent intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Appointment.class);
                        startActivity(intent);
                        break;
                    case "Medication":
                        intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Medication.class);
                        startActivity(intent);
                        break;
                    case "Measurement":
                        intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Measurement.class);
                        startActivity(intent);
                        break;
                    case "Treatment":
                        intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Treatment.class);
                        startActivity(intent);
                        break;
                }
            }
        });
        hideFab(0);
        showFab(300);

        return view;
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
}
