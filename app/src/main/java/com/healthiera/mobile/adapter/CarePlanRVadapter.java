package com.healthiera.mobile.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.activitys.CarePlanActivityes.Apointment;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.entity.model.EventItemModel;
import com.healthiera.mobile.util.Dictionary;

import org.joda.time.DateTime;


public class CarePlanRVadapter extends RecyclerView.Adapter<CarePlanRVadapter.CatalogViewHolder> {

    EventItemModel[] items = new EventItemModel[EventType.values().length];
    com.healthiera.mobile.serivce.ScheduleTimeService scheduleTimeService = new com.healthiera.mobile.serivce.ScheduleTimeService();
    private Context context;

    public CarePlanRVadapter(Context context, EventItemModel[] item) {
        this.context = context;
        items = item;

    }


    @Override
    public CarePlanRVadapter.CatalogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.care_plan_item, viewGroup, false);

        return new CarePlanRVadapter.CatalogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final CarePlanRVadapter.CatalogViewHolder catalogViewHolder, final int i) {


        catalogViewHolder.title.setText("" + items[i].getEventName());

        catalogViewHolder.icon.setImageResource(items[i].getImage());
        //catalogViewHolder.icon

        switch (items[i].getEventName()) {
            case "Treatment":
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_treatment);
                break;
            case "Medication":
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_medication);
                break;
            case "Measurement":
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_measurement);
                break;
            case "Appointment":
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_appointment);
                break;

        }

        Integer toDayEventCount = scheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate(), items[i].getEventName()).size();
        Integer tomorrowEventCount = scheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate().plusDays(1), items[i].getEventName()).size();
        catalogViewHolder.eventTetx.setText(
                (toDayEventCount == 0 && tomorrowEventCount == 0) ? Dictionary.No_Event :
                        (
                                (toDayEventCount != 0 ? Dictionary.Today + toDayEventCount : "") +
                                        ((toDayEventCount == 0 || tomorrowEventCount == 0) ? "" : ", ") +
                                        (tomorrowEventCount != 0 ? Dictionary.Tomorrow + tomorrowEventCount : "")
                        )
        );

        catalogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AppCompatActivity activity = (AppCompatActivity) v.getContext();

                Intent intent;
                intent = new Intent(v.getContext(), Apointment.class);

                switch (i) {
                    case 0:
                        intent.putExtra("name", "Appointment");
                        break;
                    case 1:
                        intent.putExtra("name", "Medication");
                        break;
                    case 2:
                        intent.putExtra("name", "Measurement");
                        break;
                    case 3:
                        intent.putExtra("name", "Treatment");
                        break;
                }
                activity.startActivityForResult(intent, 1);
                activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

            }
        });
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    class CatalogViewHolder extends RecyclerView.ViewHolder {

        TextView title, eventTetx;
        ImageView icon;

        CatalogViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.careitem_titile_id);
            eventTetx = (TextView) itemView.findViewById(R.id.care_event_text_id);
            icon = (ImageView) itemView.findViewById(R.id.careitem_image_id);

        }
    }

}
