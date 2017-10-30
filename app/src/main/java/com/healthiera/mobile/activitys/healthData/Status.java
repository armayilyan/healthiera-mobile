package com.healthiera.mobile.activitys.healthData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.Goal;
import com.healthiera.mobile.entity.MeasurementLog;
import com.healthiera.mobile.serivce.GoalService;

import java.util.ArrayList;
import java.util.List;


public class Status extends AppCompatActivity {

    TextView Weight;
    TextView Height;
    TextView bmi;
    TextView thalliumHips;
    TextView pulse;
    TextView bloodPressure;
    com.healthiera.mobile.serivce.MeasurementLogService MeasurementLogService = new com.healthiera.mobile.serivce.MeasurementLogService();
    GoalService service = new GoalService();
    GoalListAdapter goalListAdapter;
    List<Goal> goals;
    String[] value;
    private String[] name;
    private String[] valueType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Weight = (TextView) findViewById(R.id.value_weight);
        Height = (TextView) findViewById(R.id.value_height);
        bmi = (TextView) findViewById(R.id.value_bmi);
        thalliumHips = (TextView) findViewById(R.id.value_thalium_hips);
        pulse = (TextView) findViewById(R.id.value_pulse);
        bloodPressure = (TextView) findViewById(R.id.value_blood_pressure);

        name = new String[]{
                getString(R.string.goal1_name), getString(R.string.goal2_name), getString(R.string.goal3_name)
                , getString(R.string.goal4_name), getString(R.string.goal5_name), getString(R.string.goal6_name),
                getString(R.string.goal7_name)};

        valueType = new String[]{
                getString(R.string.goal1_value), getString(R.string.goal2_value), getString(R.string.goal3_value),
                getString(R.string.goal4_value), getString(R.string.goal5_value), getString(R.string.goal6_value),
                getString(R.string.goal6_value)};

        value = new String[name.length];
        goals = new ArrayList<Goal>();
        goals = service.getAllGoals();

        List<MeasurementLog> list = MeasurementLogService.GetLastLogedMeasurement();
        for (int i = 0; i < list.size(); i++) {
            value[i] = list.get(i).getValue().toString();
        }

        goalListAdapter = new GoalListAdapter();
        ListView listView = (ListView) findViewById(R.id.list_view_goals);
        listView.setAdapter(goalListAdapter);

        List<Goal> goals = service.getAllGoals();
        for (int i = 0; i < value.length - 1; i++) {
            if (value[i] != null && value[i].length() > 0) {
                goals.get(i).setName(name[i]);
                if (i == value.length - 2) {
                    goals.get(i).setValue(value[i] + "," + value[i + 1]);
                } else {
                    goals.get(i).setValue(value[i]);
                }
                service.updateGoal(goals.get(i));

            } else {
                goals.get(i).setValue(" ");
                service.updateGoal(goals.get(i));
            }
        }
        goals = service.getAllGoals();
        goalListAdapter.notifyDataSetChanged();
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.from_right, R.anim.to_left);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(menuItem);
    }

    private class GoalListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            if (name != null && name.length != 0) {
                return name.length;
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return name[position];
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final GoalListAdapter.ViewHolder holder;
            if (convertView == null) {
                holder = new GoalListAdapter.ViewHolder();
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.status_item, null);
                holder.textName = (TextView) convertView.findViewById(R.id.goal_item_name);
                holder.inputValue = (TextView) convertView.findViewById(R.id.goal_item_tv);
                holder.valueType = (TextView) convertView.findViewById(R.id.goal_item_etalon);
                convertView.setTag(holder);

            } else {
                holder = (GoalListAdapter.ViewHolder) convertView.getTag();
            }

            holder.ref = position;
            holder.textName.setText(name[position]);
//            if (value.length > 0) {
//                if (position == 5) {
//                    TextView tvValue = holder.inputValue;
//                    tvValue.setText(value[5].split(",")[0]);//goals.get(position).getValue().toString());
//                } else if (position == 6 && value[5].split(",").length > 1) {
//                    holder.inputValue.setText(value[5].split(",")[1]);//goals.get(position).getValue().toString());
//                } else {
//                    holder.inputValue.setText(value[position]);//goals.get(position).getValue().toString());
//                }
//            } else {
                holder.inputValue.setText(value[position]);
           // }

            holder.valueType.setText(valueType[position]);

            return convertView;
        }

        private class ViewHolder {
            TextView textName;
            TextView inputValue;
            TextView valueType;
            int ref;
        }
    }

}
