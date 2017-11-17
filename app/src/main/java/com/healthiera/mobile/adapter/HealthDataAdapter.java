package com.healthiera.mobile.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.healthiera.mobile.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Dell on 3/31/2017.
 */

public class HealthDataAdapter extends RecyclerView.Adapter<HealthDataAdapter.CatalogViewHolder> {

    private String[] title = {"Complaints and Symptoms","Anamnesis Vitae","Heredity ( family history )","Status","Special Devices"};
    private Map<String, Integer> map = new HashMap<>();
    private Context context;


    public HealthDataAdapter(Context context){
        this.context = context;
        map.put("aaa1", R.drawable.complains);
        map.put("aaa2", R.drawable.anamnesis);
        map.put("aaa3", R.drawable.heredity);
        map.put("aaa4", R.drawable.status);
        map.put("aaa5", R.drawable.device);

    }



    @Override
    public HealthDataAdapter.CatalogViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        final LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        final View v = layoutInflater.inflate(R.layout.item_health_data, viewGroup, false);

        return new HealthDataAdapter.CatalogViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final HealthDataAdapter.CatalogViewHolder catalogViewHolder, final int i) {




        catalogViewHolder.title.setText(title[i]);

        switch(i){
            case 0:
                catalogViewHolder.icon.setImageResource(map.get("aaa1"));
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_medication);
                break;
            case 1:
                catalogViewHolder.icon.setImageResource(map.get("aaa2"));
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_medication);
                break;
            case 2:
                catalogViewHolder.icon.setImageResource(map.get("aaa3"));
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_medication);
                break;
            case 3:
                catalogViewHolder.icon.setImageResource(map.get("aaa4"));
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_medication);
                break;
            case 4:
                catalogViewHolder.icon.setImageResource(map.get("aaa5"));
                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_medication);
                break;

        }


        catalogViewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Activity activity = (Activity) context;
                Intent intent;

                switch (i) {
                    case 0:
                        intent = new Intent(context, com.healthiera.mobile.activitys.healthData.Complains.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);

                        break;
                    case 1:
                        intent = new Intent(context, com.healthiera.mobile.activitys.healthData.AnamnesisVitae.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.right_in, R.anim.left_out);

                        break;
                    case 2:
                        intent = new Intent(context, com.healthiera.mobile.activitys.healthData.Heredity.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);

                        break;
                    case 3:

                        intent = new Intent(context, com.healthiera.mobile.activitys.healthData.Status.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);

                        break;
                    case 4:

                        intent = new Intent(context, com.healthiera.mobile.activitys.healthData.Devices.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);

                        break;
                    case 5:

                        intent = new Intent(context, com.healthiera.mobile.activitys.healthData.AnamnesisVitae.class);
                        activity.startActivity(intent);
                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);

                        break;
                }
            }
        });
//
//        catalogViewHolder.icon.setImageResource(items[i].getImage());
        //catalogViewHolder.icon

//        switch (items[i].getEventName()) {
//            case "Treatment":
//                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_treatment);
//                break;
//            case "Medication":
//                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_medication);
//                break;
//            case "Measurement":
//                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_measurement);
//                break;
//            case "Appointment":
//                catalogViewHolder.icon.setBackgroundResource(R.drawable.circle_appointment);
//                break;
//
//        }

//        Integer toDayEventCount = scheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate(), items[i].getEventName()).size();
//        Integer tomorrowEventCount = scheduleTimeService.getDateNotDoneScheduleTimes((new DateTime()).toLocalDate().plusDays(1), items[i].getEventName()).size();
//        catalogViewHolder.eventTetx.setText(
//                (toDayEventCount == 0 && tomorrowEventCount == 0) ? Dictionary.No_Event :
//                        (
//                                (toDayEventCount != 0 ? Dictionary.Today + toDayEventCount : "") +
//                                        ((toDayEventCount == 0 || tomorrowEventCount == 0) ? "" : ", ") +
//                                        (tomorrowEventCount != 0 ? Dictionary.Tomorrow + tomorrowEventCount : "")
//                        )
//        );

//        catalogViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Activity activity = (Activity) v.getContext();
//                Intent intent;
//                switch (i) {
//                    case 0:
//                        intent = new Intent(v.getContext(), Apointment.class);
//                        activity.startActivity(intent);
//                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);
//                        break;
//                    case 1:
//                        intent = new Intent(v.getContext(),Medication.class);
//                        activity.startActivity(intent);
//                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);
//                        break;
//                    case 2:
//                        intent = new Intent(v.getContext(), Measurment.class);
//                        activity.startActivity(intent);
//                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);
//                        break;
//                    case 3:
//                        intent = new Intent(v.getContext(), Treatments.class);
//                        activity.startActivity(intent);
//                        activity.overridePendingTransition(R.anim.right_in,R.anim.left_out);
//                        break;
//
//                }
//
//
//
//
//
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return title.length;
    }

    class CatalogViewHolder extends RecyclerView.ViewHolder {

        TextView title ;
        ImageView icon;
        ImageView start;
        CardView cardView;

        CatalogViewHolder(View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title_health_data);
            icon = (ImageView) itemView.findViewById(R.id.item_health_data);
            start = (ImageView)itemView.findViewById(R.id.image_health_data);
            cardView = (CardView) itemView.findViewById(R.id.cv_health_data);

        }
    }
}