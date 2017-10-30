package com.healthiera.mobile.activity.main;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.TypedValue;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;


public class MakeGraph {
    public View view;
    int dp;
    float average = 0.0f;
    private int colorBackground;
    private float values[];
    public GraphView graphview;
    private int colorAccent;

    public MakeGraph(GraphView graphview, String title, Context context, float[] args) {
        this.graphview = graphview;
        this.colorBackground = Color.parseColor("#ff0000");
        this.colorAccent = Color.parseColor("#ffffff");
        dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
        values = args;
        float min = values[0];
        float max = values[0];
        for (int i = 1; i < values.length; i++) {
            if (values[i] < min) min = values[i];
            if (values[i] > max) max = values[i];
        }

        for (float value : values) {
            average += value;
        }
        average = (average) / values.length;
        graphview.setHorizontalScrollBarEnabled(true);
        graphview.setTitle(title + " : average(" + String.format("%.2f", average) + ")");
        graphview.getGridLabelRenderer().setGridColor(Color.parseColor("#14000000"));
        graphview.getGridLabelRenderer().setHighlightZeroLines(false);
        graphview.getGridLabelRenderer().setLabelsSpace(6);
        graphview.getGridLabelRenderer().setGridStyle(GridLabelRenderer.GridStyle.HORIZONTAL);
        this.graphview.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#a0ffffff"));
        this.graphview.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#a0ffffff"));
        graphview.getViewport().setXAxisBoundsManual(true);
        graphview.getViewport().setYAxisBoundsManual(true);
        graphview.getViewport().setMinX(0.0f);
        graphview.getViewport().setMaxX(values.length);
        graphview.getViewport().setMinY(min);
        graphview.getViewport().setMaxY(max);
        DrawAverage();
        DrawChart();
        DrawPoints();
    }

    public void setColorAccent(int color) {
        colorAccent = color;
        DrawAverage();
        DrawChart();
        DrawPoints();
    }

    public void setColorBackground(int color) {
        colorBackground = color;
        if (color == Color.parseColor("#ffffff")) {
            this.graphview.getGridLabelRenderer().setVerticalLabelsColor(Color.parseColor("#40000000"));
            this.graphview.getGridLabelRenderer().setHorizontalLabelsColor(Color.parseColor("#40000000"));
        }
        DrawAverage();
        DrawChart();
        DrawPoints();
    }

    public void DrawAverage() {
        /// Average Line
        LineGraphSeries<DataPoint> series0 = new LineGraphSeries<DataPoint>();
        series0.appendData(new DataPoint(0, average), true, values.length);
        series0.appendData(new DataPoint(values.length, average), true, values.length);
        Paint paint0 = new Paint();
        paint0.setStrokeWidth(dp);
        paint0.setStyle(Paint.Style.STROKE);
        paint0.setPathEffect(new DashPathEffect(new float[]{2 * dp, 4 * dp}, 2 * dp));
        paint0.setColor(Color.parseColor("#ffff00"));
        series0.setCustomPaint(paint0);
        graphview.addSeries(series0);

    }

    public void DrawChart() {
        /// Chart
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>();
        for (int i = 0; i < values.length; i++) {
            series.appendData(new DataPoint(i, values[i]), true, values.length);
        }
        graphview.addSeries(series);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(dp);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(colorAccent);
        series.setCustomPaint(paint);
    }

    public void DrawPoints() {
        // Points outter
        PointsGraphSeries<DataPoint> series1 = new PointsGraphSeries<DataPoint>();
        graphview.addSeries(series1);
        for (int i = 0; i < values.length; i++) {
            series1.appendData(new DataPoint(i, values[i]), true, values.length);
        }
        series1.setShape(PointsGraphSeries.Shape.POINT);
        series1.setSize(3f * dp);
        series1.setColor(colorAccent);

        // Points inner
        PointsGraphSeries<DataPoint> series2 = new PointsGraphSeries<DataPoint>();
        graphview.addSeries(series2);
        for (int i = 0; i < values.length; i++) {
            series2.appendData(new DataPoint(i, values[i]), true, values.length);
        }
        series2.setShape(PointsGraphSeries.Shape.POINT);
        series2.setSize(2f * dp);
        series2.setColor(colorBackground);
    }
}
