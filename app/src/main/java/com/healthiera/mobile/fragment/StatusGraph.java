package com.healthiera.mobile.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.healthiera.mobile.R;
import com.healthiera.mobile.activity.main.MakeGraph;
import com.healthiera.mobile.entity.Goal;
import com.healthiera.mobile.serivce.GoalService;
import com.healthiera.mobile.serivce.MeasurementLogService;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class StatusGraph extends Fragment {

    GoalService service = new GoalService();
    List<Goal> goals;
    String[] value;
    private String[] name;
    private String[] valueType;
    private float[] valuesHDL, valuesWeight, valuesBMI, valuesA1c, valuesLDL, valuesSysBloodPr, valuesDiasBloodPr;
    private MeasurementLogService MeasurementLogService =new MeasurementLogService();
    public StatusGraph() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_status_graph, container, false);
        name = new String[]{
                getString(R.string.goal1_name), getString(R.string.goal2_name), getString(R.string.goal3_name),
                getString(R.string.goal4_name), getString(R.string.goal5_name), getString(R.string.goal6_name),
                getString(R.string.goal7_name)};

        valueType = new String[]{
                getString(R.string.goal1_value), getString(R.string.goal2_value), getString(R.string.goal3_value),
                getString(R.string.goal4_value), getString(R.string.goal5_value), getString(R.string.goal6_value),
                getString(R.string.goal6_value)};

        goals = new ArrayList<>();
        goals = service.getAllGoals();
        value = new String[name.length];
        for (int i = 0; i < goals.size() && i < 7; i++) {
            value[i] = goals.get(i).getValue();
        }
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

//        valuesWeight = new float[]{0.2f, 0.6f, 0.8f, 0.5f, 0.1f, 0.3f, 0.6f, 0.8f, 0.7f, 0.4f, 0.1f};
//        valuesBMI = new float[]{2f, 6f, 1f, 15.5f, 28.1f, 13f, 5.6f, 0.8f, 4.7f, 6.4f, 17.1f};
//        valuesA1c = new float[]{1.8f, 4f}; // empty
//        valuesLDL = new float[]{12f, 5f, 10.1f, 6.3f, -1.6f, 1.8f, -3.7f, 0.6f, 1.1f};
//        valuesSysBloodPr = new float[]{4f, 20.1f, 4.3f, 17.6f, 13.8f, 21.7f, 4.4f, 6.1f};
//        valuesDiasBloodPr = new float[]{4f, 20.1f, 4.3f, 17.6f, 13.8f, 21.7f, 4.4f, 6.1f};

        float v = 0f;

        try {
            v = Float.parseFloat(value[0]);
        } catch (NumberFormatException e) {
            v = 0f;
        }
        valuesHDL = new float[]{v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v};

        try {
            v = Float.parseFloat(value[1]);
        } catch (NumberFormatException e) {
            v = 0f;
        }
        valuesWeight = new float[]{v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v};
        try {
            v = Float.parseFloat(value[2]);
        } catch (NumberFormatException e) {
            v = 0f;
        }
        valuesBMI = new float[]{v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v};
        try {
            v = Float.parseFloat(value[3]);
        } catch (NumberFormatException e) {
            v = 0f;
        }
        valuesA1c = new float[]{v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v};
        try {
            v = Float.parseFloat(value[4]);
        } catch (NumberFormatException e) {
            v = 0f;
        }
        valuesLDL = new float[]{v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v, v};
        try {
            v = Float.parseFloat(value[5]);
        } catch (NumberFormatException e) {
            v = 0f;
        }
        view = GraphImplementation(view);
        return view;
    }

    private View GraphImplementation(View view) {
        float[] values;
        GraphView graph;

        graph = (GraphView) view.findViewById(R.id.gv1);
        DrowChart(graph,"Weight",valuesWeight);

        graph = (GraphView) view.findViewById(R.id.gv2);
        DrowChart(graph,"BMI",valuesBMI);

        graph = (GraphView) view.findViewById(R.id.gv3);
        DrowChart(graph,"A1c",valuesA1c);

        graph = (GraphView) view.findViewById(R.id.gv4);
        DrowChart(graph,"LDL",valuesLDL);



        return view;
    }

    private void DrowChart(GraphView graph, String goalName,float[] val)
    {
        int[] weightLog = MeasurementLogService.getChartData(goalName);

        DataPoint[] weightGoalDp = new DataPoint[val.length];
        DataPoint[] weightLogDp = new DataPoint[weightLog.length];

        for(int i=0;i<val.length;i++)
        {
            weightGoalDp[i]=new DataPoint(i,val[i]);
        }

        for(int i=0;i<weightLog.length;i++)
        {
            weightLogDp[i]=new DataPoint(i,weightLog[i]);
        }

        LineGraphSeries<DataPoint> seriesWeightGoal = new LineGraphSeries<>(weightGoalDp);
        LineGraphSeries<DataPoint> seriesWeightLog = new LineGraphSeries<>(weightLogDp);
        seriesWeightGoal.setColor(Color.WHITE);
        seriesWeightLog.setColor(Color.YELLOW);

        graph.addSeries(seriesWeightGoal);
        graph.addSeries(seriesWeightLog);

        graph.getViewport().setMinX(0);
        graph.getViewport().setMaxX(30);
        graph.getViewport().setMinY(0.0);
        graph.getViewport().setMaxY(200.0);

        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setXAxisBoundsManual(true);
    }
}
