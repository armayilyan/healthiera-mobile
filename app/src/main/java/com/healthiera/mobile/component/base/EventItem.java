package com.healthiera.mobile.component.base;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.model.EventItemModel;
import com.healthiera.mobile.util.Dictionary;

import org.joda.time.DateTime;


/**
 * Created by Davit on 01.10.2016.
 *
 * @author Davit Ter-Arakelyan
 * @date 01.10.2016
 */

public class EventItem extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    EventItemModel[] data;
    com.healthiera.mobile.serivce.ScheduleTimeService ScheduleTimeService = new com.healthiera.mobile.serivce.ScheduleTimeService();

    public EventItem(Context context, EventItemModel[] data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return data[position];
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
            vi = inflater.inflate(R.layout.event_item, null);

        TextView tvEventCount = (TextView) vi.findViewById(R.id.tvEventCount);
        TextView tvEventCategoryName = (TextView) vi.findViewById(R.id.tvEventCategoryName);
        tvEventCategoryName.setText(data[position].getEventName());

        ImageView ivEventIcon = (ImageView) vi.findViewById(R.id.ivEventIcon);
        ivEventIcon.setImageBitmap(BitmapFactory.decodeResource(vi.getResources(), data[position].getImage()));

        switch (data[position].getEventName()) {
            case "Treatment":
                ivEventIcon.setBackgroundResource(R.drawable.circle_treatment);
                break;
            case "Medication":
                ivEventIcon.setBackgroundResource(R.drawable.circle_medication);
                break;
            case "Measurement":
                ivEventIcon.setBackgroundResource(R.drawable.circle_measurement);
                break;
            case "Appointment":
                ivEventIcon.setBackgroundResource(R.drawable.circle_appointment);
                break;

        }


        Integer toDayEventCount = ScheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate(), data[position].getEventName()).size();
        Integer tomorrowEventCount = ScheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate().plusDays(1), data[position].getEventName()).size();

        tvEventCount.setText(
                (toDayEventCount == 0 && tomorrowEventCount == 0) ? Dictionary.No_Event :
                        (
                                (toDayEventCount != 0 ? Dictionary.Today + toDayEventCount : "") +
                                        ((toDayEventCount == 0 || tomorrowEventCount == 0) ? "" : ", ") +
                                        (tomorrowEventCount != 0 ? Dictionary.Tomorrow + tomorrowEventCount : "")
                        )
        );

        return vi;
    }
}

