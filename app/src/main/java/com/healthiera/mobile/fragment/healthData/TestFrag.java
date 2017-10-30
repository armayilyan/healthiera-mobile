package com.healthiera.mobile.fragment.healthData;

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
import com.healthiera.mobile.fragment.BaseFragment;
import com.healthiera.mobile.fragment.Event.EventList;

/**
 * Created by Dell on 3/31/2017.
 */

public class TestFrag extends BaseFragment {
    @Nullable
    private ListView listview;
    private boolean isMneuOpened = false;
    private FloatingActionMenu menu;
    private int position = 0;






    //    private static EventItemModel[] getEventitems() {
//        EventItemModel[] items = new EventItemModel[EventType.values().length];
//        int index = 0;
//        for (EventType dir : EventType.values()) {
//            items[index] = new EventItemModel(dir.getEvent(), dir.getEventName(), dir.getEventIcon());
//            index++;
//        }
//        return items;
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_test, container, false);
//        listview = (ListView) rootView.findViewById(R.id.lvEvent);
//
//        listview.setAdapter(new EventItem(getContext(), getEventitems()));
//        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

         int position = 0;

                Bundle args = new Bundle();

                args.putString("EventType", EventType.values()[position].getEventName());

                EventList eventList = new EventList();

                eventList.setArguments(args);

                getFragmentManager().beginTransaction().replace(R.id.Content_id_test, eventList)
                        .addToBackStack(EventType.values()[position].getEventName()).commit();






        return rootView;
    }


}