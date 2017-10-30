package com.healthiera.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.github.clans.fab.FloatingActionMenu;
import com.healthiera.mobile.R;
import com.healthiera.mobile.component.base.EventItem;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.entity.model.EventItemModel;
import com.healthiera.mobile.fragment.Event.EventList;


/**
 * @author Davit Ter-Arakelyan
 * @date 19.08.2016
 */
public class CarePlan extends BaseFragment implements View.OnClickListener {
        @Nullable
    private ListView listview;
    private FloatingActionMenu menu;

    private static EventItemModel[] getEventitems() {
        EventItemModel[] items = new EventItemModel[EventType.values().length];
        int index = 0;
        for (EventType dir : EventType.values()) {
            items[index] = new EventItemModel(dir.getEvent(), dir.getEventName(), dir.getEventIcon());
            index++;
        }
        return items;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_care_plan, container, false);
        listview = (ListView) rootView.findViewById(R.id.lvEvent);

        listview.setAdapter(new EventItem(getContext(), getEventitems()));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Bundle args = new Bundle();

                args.putString("EventType", EventType.values()[position].getEventName());

                EventList eventList = new EventList();

                eventList.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.Content_id_, eventList)
                        .addToBackStack(EventType.values()[position].getEventName()).commit();
            }
        });

        com.github.clans.fab.FloatingActionButton fabTreatment =
                (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.fab_treatment);
        final com.github.clans.fab.FloatingActionButton fabAppointment =
                (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.fab_appointment);
        com.github.clans.fab.FloatingActionButton fabMedication =
                (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.fab_medication);
        com.github.clans.fab.FloatingActionButton fabMeasurement =
                (com.github.clans.fab.FloatingActionButton) rootView.findViewById(R.id.fab_measurement);

        fabAppointment.setOnClickListener(this);
        fabMeasurement.setOnClickListener(this);
        fabMedication.setOnClickListener(this);
        fabTreatment.setOnClickListener(this);

        menu = (FloatingActionMenu) rootView.findViewById(R.id.fab_menu);
        final View outside = rootView.findViewById(R.id.outside);
        outside.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.close(true);
            }
        });
        menu.setOnMenuToggleListener(new FloatingActionMenu.OnMenuToggleListener() {
            @Override
            public void onMenuToggle(boolean opened) {
                if (opened)
                    outside.setVisibility(View.VISIBLE);
                else
                    outside.setVisibility(View.GONE);
            }
        });
        return rootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab_treatment:
                Intent intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Treatment.class);
                startActivity(intent);
                break;
            case R.id.fab_appointment:
                intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Appointment.class);
                startActivity(intent);
                break;
            case R.id.fab_medication:
                intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Medication.class);
                startActivity(intent);
                break;
            case R.id.fab_measurement:
                intent = new Intent(getActivity(), com.healthiera.mobile.Schedule.Measurement.class);
                startActivity(intent);
                break;
        }
    }
}