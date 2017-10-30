package com.healthiera.mobile.component.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.Schedule;
import com.healthiera.mobile.entity.ScheduleTime;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.serivce.MedicationLogService;
import com.healthiera.mobile.serivce.ScheduleService;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * Created by Davit on 04.10.2016.
 */

public class EventListItem extends BaseAdapter {

    private static LayoutInflater inflater = null;
    Context context;
    List<ScheduleTime> data;

    DateFormat df = new SimpleDateFormat("HH:mm");
    MedicationLogService MedicationLogService = new MedicationLogService();

    public EventListItem(Context context, List<ScheduleTime> data) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.data = data;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public static String getDateDiff(Integer t) {
        if (t < 60)
            return t.toString() + " min";
        else
            return (t / 60 - 2) + " hr";
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

    private String IntToDate(Integer minutes) {
        String startTime = "00:00";
        int h = minutes / 60 + Integer.parseInt(startTime.substring(0, 1));
        int m = minutes % 60 + Integer.parseInt(startTime.substring(3, 4));
        return (h < 10 ? "0" : "") + h + ":" + (m < 10 ? "0" : "") + m;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View vi = convertView;
        if (vi == null)
            vi = inflater.inflate(R.layout.event_list_item, null);

        ScheduleService ScheduleService = new ScheduleService();
        Schedule schedule = ScheduleService.getScheduleById(data.get(position).getSchedule().getId());

        TextView tvEventCategoryName = (TextView) vi.findViewById(R.id.tvEventListItemName);
        tvEventCategoryName.setText(schedule.getTitle());
        TextView tvEventListItemDesc = (TextView) vi.findViewById(R.id.tvEventListItemDesc);
        tvEventListItemDesc.setText(schedule.getDescription());
        TextView tvEventListItemTime = (TextView) vi.findViewById(R.id.tvEventListItemTime);
        //tvEventListItemTime.setText(df.format(schedule.getStartDate()));
        tvEventListItemTime.setText(IntToDate(data.get(position).getTime()));
        TextView tvEventListItemRemainedTime = (TextView) vi.findViewById(R.id.tvEventListItemRemainedTime);
        if (schedule.getStartDate().before(new Date())) {
            tvEventListItemRemainedTime.setText(getDateDiff(data.get(position).getTime()));
        }
        View statusBorder = (View) vi.findViewById(R.id.status_border);
        View eventStatusBadge = (View) vi.findViewById(R.id.eventStatusBadge);
        ImageView ivEventStatus = (ImageView) vi.findViewById(R.id.ivEventStatus);
        String time = df.format(new Date());
        Integer t = Integer.parseInt(time.split(":")[0]) * 60 + Integer.parseInt(time.split(":")[1]);
        if (data.get(position).getSchedule().getStartDate().after(new Date())) {
            ivEventStatus.setImageResource(R.drawable.active_status);
            statusBorder.setBackgroundColor(context.getResources().getColor(R.color.colorStatusActive));
            eventStatusBadge.setBackgroundResource(R.drawable.badge_active);
        } else {
            if (data.get(position).getTime() > t) {
                ivEventStatus.setImageResource(R.drawable.active_status);
                statusBorder.setBackgroundColor(context.getResources().getColor(R.color.colorStatusActive));
                eventStatusBadge.setBackgroundResource(R.drawable.badge_active);
            } else {
                ivEventStatus.setImageResource(R.drawable.done_status);
                statusBorder.setBackgroundColor(context.getResources().getColor(R.color.colorStatusDone));
                eventStatusBadge.setBackgroundResource(R.drawable.badge_done);
            }
            if (data.get(position).getSchedule().getEventType() == EventType.Measurement) {

                ivEventStatus.setImageResource(R.drawable.cancel_status);
                statusBorder.setBackgroundColor(context.getResources().getColor(R.color.colorStatusCancel));
                eventStatusBadge.setBackgroundResource(R.drawable.badge_cancel);
            }
            if (data.get(position).getSchedule().getEventType() == EventType.Medication) {
                ivEventStatus.setImageResource(R.drawable.cancel_status);
                statusBorder.setBackgroundColor(context.getResources().getColor(R.color.colorStatusCancel));
                eventStatusBadge.setBackgroundResource(R.drawable.badge_cancel);
            }
        }
        return vi;
    }
}