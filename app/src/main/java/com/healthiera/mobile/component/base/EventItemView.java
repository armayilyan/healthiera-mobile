package com.healthiera.mobile.component.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.model.EventViewModel;

import java.util.List;

/**
 * Created by Davit on 08.10.2016.
 */

public class EventItemView  extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<EventViewModel> data;

    public EventItemView(Context context, List<EventViewModel> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.event_view, null);

        TextView tvEventRowName = (TextView) vi.findViewById(R.id.tvEventRowName);
        tvEventRowName.setText(data.get(position).getName()+": ");

        TextView tvEventRowValue = (TextView) vi.findViewById(R.id.tvEventRowValue);
        tvEventRowValue.setText(data.get(position).getValue());


        return vi;
    }
}