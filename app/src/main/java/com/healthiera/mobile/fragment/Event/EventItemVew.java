package com.healthiera.mobile.fragment.Event;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.Measurement;
import com.healthiera.mobile.entity.MeasurementLog;
import com.healthiera.mobile.entity.Medication;
import com.healthiera.mobile.entity.MedicationLog;
import com.healthiera.mobile.entity.Procedure;
import com.healthiera.mobile.entity.Schedule;
import com.healthiera.mobile.fragment.BaseFragment;
import com.healthiera.mobile.serivce.ScheduleService;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * @author Davit
 * @date 05.10.2016
 */

public class EventItemVew extends BaseFragment {

    ScheduleService ScheduleService = new ScheduleService();
    TextView tvRepeatType;
    TextView tvDurationType;
    Date date;
    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");
    int time;
    private com.healthiera.mobile.serivce.ProcedureService ProcedureService = new com.healthiera.mobile.serivce.ProcedureService();
    private com.healthiera.mobile.serivce.MeasurementService MeasurementService = new com.healthiera.mobile.serivce.MeasurementService();
    private com.healthiera.mobile.serivce.MedicationService MedicationService = new com.healthiera.mobile.serivce.MedicationService();
    private com.healthiera.mobile.serivce.MeasurementLogService MeasurementLogService = new com.healthiera.mobile.serivce.MeasurementLogService();
    private com.healthiera.mobile.serivce.MedicationLogService MedicationLogService = new com.healthiera.mobile.serivce.MedicationLogService();
    private View view;
    private EditText etMesurValue;
    private EditText etSystolicValue;
    private EditText etDiastolicValue;

    public EventItemVew() {
    }

    public static Object runGetter(Field field, Object m) {
        // MZ: Find the correct method
        Class o;
        o = m.getClass();
        for (Method method : o.getMethods()) {
            if ((method.getName().startsWith("get")) && (method.getName().length() == (field.getName().length() + 3))) {
                if (method.getName().toLowerCase().endsWith(field.getName().toLowerCase())) {
                    // MZ: Method found, run it
                    try {
                        return method.invoke(m);
                    } catch (IllegalAccessException e) {
                        // Logger.fatal("Could not determine method: " + method.getName());
                    } catch (InvocationTargetException e) {
                        // Logger.fatal("Could not determine method: " + method.getName());
                    }

                }
            }
        }
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.event_item_vew, container, false);
        Bundle bundle = getArguments();
//        ListView listview = (ListView) view.findViewById(R.id.lvEventItemVew);
//        listview.setAdapter(new EventItemView(getContext(), getEventView(bundle.getString("EventType"), bundle.getLong("EventId"))));

        tvRepeatType = (TextView) view.findViewById(R.id.tvRepeatType);
        tvDurationType = (TextView) view.findViewById(R.id.tvDurationType);
        Schedule s = ScheduleService.getScheduleByScheduleTimeId(bundle.getLong("EventId"));
        date = (Date) bundle.getSerializable("Date");
        time = bundle.getInt("Time");

        return setEventView(bundle.getString("EventType"), s.getId());
    }

    private void setTypes(Schedule schedule) {
        switch (schedule.getRepeatType()) {
            case DAYS_INTERVAL:
                tvRepeatType.setText("Days interval_" + schedule.getDaysInterval().toString());
                break;
            case EVERY_DAY:
                tvRepeatType.setText("Every day");
                break;
            default:
                tvRepeatType.setText("Week days_" + schedule.getDaysOfWeek());
                break;
        }

        switch (schedule.getDurationType()) {
            case CONTINOUS:
                tvDurationType.setText("Continous");
                break;
            default:
                tvDurationType.setText("Number of days_" + schedule.getNumberOfRepetition());
                break;
        }

    }

    private View setEventView(String s, Long eventId) {
        switch (s) {
            case "Appointment":
            case "Treatment": {
                final Procedure m = ProcedureService.findProcedureByScheduleId(eventId);
                if (m != null) {
                    TextView tvName = (TextView) view.findViewById(R.id.tvName);
                    TextView tvDescription = (TextView) view.findViewById(R.id.tvDescription);
                    TextView tvDoctor = (TextView) view.findViewById(R.id.tvDoctor);
                    TextView tvCode = (TextView) view.findViewById(R.id.tvCode);

                    TextView tvStartDate = (TextView) view.findViewById(R.id.tvStartDate);
                    TextView tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);


                    LinearLayout llProcedure = (LinearLayout) view.findViewById(R.id.llProcedure);
                    llProcedure.setVisibility(View.VISIBLE);

                    tvName.setText(m.getSchedule().getTitle());
                    tvDescription.setText(m.getSchedule().getDescription());
                    tvDoctor.setText(m.getDoctor().getName());
                    tvCode.setText(m.getCode());

                    Schedule schedule = m.getSchedule();

                    tvStartDate.setText(df.format(schedule.getStartDate()));
                    tvEndDate.setText(schedule.getEndDate() != null ? schedule.getEndDate().toString() : "");

                    setTypes(schedule);
                }
                break;
            }
            case "Measurement": {
                final Measurement m = MeasurementService.findMeasurementByScheduleId(eventId);
                if (m != null) {
                    TextView tvMesurName = (TextView) view.findViewById(R.id.tvMesurName);
                    TextView tvMesurDescription = (TextView) view.findViewById(R.id.tvMesurDescription);
                    TextView tvMesurType = (TextView) view.findViewById(R.id.tvMesurType);

                    etMesurValue = (EditText) view.findViewById(R.id.etMesurValue);
                    etSystolicValue = (EditText) view.findViewById(R.id.etSystolicValue);
                    etDiastolicValue = (EditText) view.findViewById(R.id.etDiastolicValue);

                    Button btnMesurementDone = (Button) view.findViewById(R.id.btnMesurementDone);
                    LinearLayout llMesurement = (LinearLayout) view.findViewById(R.id.llMesurement);

                    tvRepeatType = (TextView) view.findViewById(R.id.tvMesuremenRepeatType);
                    tvDurationType = (TextView) view.findViewById(R.id.tvMesurementDurationType);

                    TextView tvStartDate = (TextView) view.findViewById(R.id.tvMesurementStartDate);
                    TextView tvEndDate = (TextView) view.findViewById(R.id.tvMesurementEndDate);


                    llMesurement.setVisibility(View.VISIBLE);

                    tvMesurName.setText(m.getSchedule().getTitle());
                    tvMesurDescription.setText(m.getSchedule().getDescription());
                    tvMesurType.setText(m.getGoal().getName());


                    Schedule schedule = m.getSchedule();

                    tvStartDate.setText(df.format(schedule.getStartDate()));
                    tvEndDate.setText(schedule.getEndDate() != null ? schedule.getEndDate().toString() : "");
                    setTypes(schedule);

                    TableRow trGoalPressure = (TableRow)view.findViewById(R.id.trGoalPressure);
                    TableRow trGoal = (TableRow)view.findViewById(R.id.trGoal);

                    if(m.getGoal().getName().equals(getString(R.string.goal6_name)))
                        trGoalPressure.setVisibility(View.VISIBLE);
                    else
                        trGoal.setVisibility(View.VISIBLE);

                    Drawable drawable = etMesurValue.getBackground();
                    drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                    etMesurValue.setBackgroundDrawable(drawable);
                    btnMesurementDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            MeasurementLog measurementLog = new MeasurementLog();
                            measurementLog.setValue(etMesurValue.getText().toString());
                            measurementLog.setLogDateTime(new Date());
                            measurementLog.setScheduleDateTime(new Date(date.getTime() + time));
                            measurementLog.setMeasurement(m);

                            if (etMesurValue.getText().toString().isEmpty()) {
                                etMesurValue.setError("required");
                                etMesurValue.requestFocus();
                            } else {
                                getFragmentManager().beginTransaction().remove(EventItemVew.this).commit();
                                MeasurementLogService.createMeasurementLog(measurementLog);
                            }
                        }
                    });
                }
                break;
            }
            case "Medication": {
                final Medication m = MedicationService.findMedicationByScheduleId(eventId);
                if (m != null) {
                    TextView tvMedicationName = (TextView) view.findViewById(R.id.tvMedicationName);
                    TextView tvMedicationCode = (TextView) view.findViewById(R.id.tvMedicationCode);
                    TextView tvMedicationManufacturer = (TextView) view.findViewById(R.id.tvMedicationManufacturer);
                    Button btnMedicationDone = (Button) view.findViewById(R.id.btnMedicationDone);
                    LinearLayout llMedication = (LinearLayout) view.findViewById(R.id.llMedication);

                    tvRepeatType = (TextView) view.findViewById(R.id.tvMedicationRepeatType);
                    tvDurationType = (TextView) view.findViewById(R.id.tvMedicationDurationType);

                    TextView tvStartDate = (TextView) view.findViewById(R.id.tvMedicationStartDate);
                    TextView tvEndDate = (TextView) view.findViewById(R.id.tvMedicationEndDate);

                    llMedication.setVisibility(View.VISIBLE);

                    tvMedicationName.setText(m.getSchedule().getTitle());
                    tvMedicationCode.setText(m.getCode());
                    tvMedicationManufacturer.setText(m.getManufacturer());


                    Schedule schedule = m.getSchedule();

                    tvStartDate.setText(df.format(schedule.getStartDate()));
                    tvEndDate.setText(schedule.getEndDate() != null ? schedule.getEndDate().toString() : "");

                    setTypes(schedule);

                    btnMedicationDone.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Log.e("done", (new Date(df.format(date) + " " + IntToDate(time))).toString());
                            MedicationLog medicationLog = new MedicationLog();
                            medicationLog.setLogDateTime(new Date());
                            medicationLog.setScheduleDateTime(new Date(df.format(date) + " " + IntToDate(time)));
                            medicationLog.setMedication(m);

                            getFragmentManager().beginTransaction().remove(EventItemVew.this).commit();

                            MedicationLogService.createMeasurementLog(medicationLog);
                        }
                    });
                }
                break;
            }
            default:
                break;
        }
        return view;
    }

    private String IntToDate(Integer minutes) {
        String startTime = "00:00";
        int h = minutes / 60 + Integer.parseInt(startTime.substring(0, 1));
        int m = minutes % 60 + Integer.parseInt(startTime.substring(3, 4));
        return (h < 10 ? "0" : "") + h + ":" + (m < 10 ? "0" : "") + m;
    }


}
