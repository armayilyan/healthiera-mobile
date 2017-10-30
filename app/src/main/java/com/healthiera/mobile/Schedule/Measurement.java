package com.healthiera.mobile.Schedule;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.healthiera.mobile.R;
import com.healthiera.mobile.component.base.FlowLayout;
import com.healthiera.mobile.entity.Goal;
import com.healthiera.mobile.entity.enumeration.DurationType;
import com.healthiera.mobile.entity.enumeration.EventType;
import com.healthiera.mobile.entity.enumeration.MeasurementType;
import com.healthiera.mobile.entity.enumeration.RepeatType;
import com.healthiera.mobile.serivce.GoalService;
import com.healthiera.mobile.serivce.MeasurementService;
import com.healthiera.mobile.serivce.ScheduleService;
import com.healthiera.mobile.serivce.ScheduleTimeService;

import org.joda.time.DateTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Measurement extends AppCompatActivity implements View.OnClickListener,
        Animation.AnimationListener, View.OnTouchListener, View.OnLongClickListener {
    private int lastRadioButtonDayIndex = 1;
    private int lastRadioButtonDurationIndex = 1;
    private TextView done;
    private ImageView close;
    private EditText name;
    private EditText description;
    private EditText measurementName;
    private ArrayList<Date> reminderTimes;
    private SimpleDateFormat simpleDateFormat;
    private SimpleDateFormat startDateFormat;
    private ArrayList mSelectedItems;
    private boolean[] checkedDays;
    private int dp;
    private TextView up;
    private TextView down;
    private EditText minute;
    private TextView remindBeforeMinutes;
    private boolean longTouched = false;
    private boolean firstTouched = true;
    private Goal goal;

    private String hiddenTextDuration = "";
    private String hiddenTextDays = "Every day";
    private TextView hiddenTextReminder;
    private TextView hiddenTextSchedule;
    private TextView tvDate;
    private RadioButton radioButtonDay1;
    private RadioButton radioButtonDay2;
    private RadioButton radioButtonDay3;
    private RadioButton radioButtonDuration1;
    private RadioButton radioButtonDuration2;
    private Spinner spGoal;
    private Button goalButton;
    private View view;
    private ImageView dropDownArrowReminder;
    private ImageView dropDownArrowSchedule;
    private CardView cvReminder;
    private CardView cvSchedule;
    private LinearLayout contentReminder;
    private LinearLayout visibleContentReminder;
    private FlowLayout reminderTimesContent;
    private LinearLayout visibleContentSchedule;
    private LinearLayout contentSchedule;
    private Animation fadeIn;
    private Animation fadeOut;
    private Animation rotateIn;
    private Animation rotateOut;
    private Animation fadeIn2;
    private Animation fadeOut2;
    private Animation rotateIn2;
    private Animation rotateOut2;

    private ArrayAdapter<String> spinnerAdapter;

    private com.healthiera.mobile.serivce.MeasurementService MeasurementService = new MeasurementService();
    private com.healthiera.mobile.serivce.ScheduleService ScheduleService = new ScheduleService();
    private com.healthiera.mobile.serivce.ScheduleTimeService ScheduleTimeService = new ScheduleTimeService();
    private com.healthiera.mobile.serivce.GoalService GoalService = new GoalService();

    private Integer NumberOfRepetition;
    private String SpecificDaysOfWeek;
    private Integer DaysInterval;
    private Date startDate;
    private TextView textViewType;
    private int day;
    private int SpinnerPosition;
    private int spinnerSelectionCount;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        simpleDateFormat = new SimpleDateFormat("HH:mm");
        startDateFormat = new SimpleDateFormat("MMM dd, yyyy");
        mSelectedItems = new ArrayList();
        checkedDays = new boolean[]{false, false, false, false, false, false, false};
        remindBeforeMinutes = (TextView) findViewById(R.id.tv_remind_before_minutes);
        day = getIntent().getIntExtra("day", 1);

        initReminderTimes();
        firstCardImplementation();
        secondCardImplementation();
        name = (EditText) findViewById(R.id.name);
        measurementName = (EditText) findViewById(R.id.measurement_name);
        description = (EditText) findViewById(R.id.description);
        close = (ImageView) findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        done = (TextView) findViewById(R.id.done);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateName()) {
                    name.setError("invalid name");
                    name.requestFocus();
                } else if (!validateMeasurementName()) {
                    measurementName.setError("invalid measurement name");
                    measurementName.requestFocus();
                } else if (!validateDescription()) {
                    description.setError("invalid description");
                    description.requestFocus();
                } else {
                    if (SaveAppointment()) {
                        finish();
                    }
                }
            }
        });
    }

    private boolean SaveAppointment() {
        EditText tilName = (EditText) findViewById(R.id.name);
        EditText etDescription = (EditText) findViewById(R.id.description);
        EditText etName = (EditText) findViewById(R.id.measurement_name);

        com.healthiera.mobile.entity.Measurement measurement = new com.healthiera.mobile.entity.Measurement();
        com.healthiera.mobile.entity.Schedule schedule = new com.healthiera.mobile.entity.Schedule();

        schedule.setStartDate(startDate);
        schedule.setDurationType(radioButtonDuration2.isChecked() ? DurationType.NUMBER_OF_DAYS : DurationType.CONTINOUS);
        schedule.setNumberOfRepetition(radioButtonDuration2.isChecked() ? NumberOfRepetition : null);
        schedule.setRepeatType(radioButtonDay1.isChecked() ? RepeatType.EVERY_DAY : (radioButtonDay2.isChecked() ? RepeatType.SPECIFIC_DAYS : RepeatType.DAYS_INTERVAL));
        schedule.setDaysInterval(DaysInterval);
        schedule.setDaysOfWeek(SpecificDaysOfWeek);
        schedule.setEventType(EventType.Measurement);
        schedule.setTitle(etName.getText().toString());
        schedule.setDescription(etDescription.getText().toString());
        schedule.setRemindBeforeMinutes(15);

        measurement.setName(tilName.getText().toString());
        measurement.setSchedule(schedule);

        String s = spGoal.getSelectedItem().toString();
        if (s.isEmpty()) {
            return false;
        }
        Goal g = GoalService.getGoalByName(s.substring(0, s.indexOf(" - ")));
        measurement.setGoal(g);
        measurement.setType(MeasurementType.Type1);

        ScheduleService.createSchedule(schedule);
        MeasurementService.createMeasurement(measurement);

        for (int index = 0; index < reminderTimesContent.getChildCount(); ++index) {
            com.healthiera.mobile.entity.ScheduleTime scheduleTime = new com.healthiera.mobile.entity.ScheduleTime();
            FrameLayout framlayout = (FrameLayout) reminderTimesContent.getChildAt(index);
            FrameLayout fadelayout = (FrameLayout) framlayout.getChildAt(0);
            LinearLayout leftlayout = (LinearLayout) fadelayout.getChildAt(0);
            String time = ((TextView) leftlayout.getChildAt(1)).getText().toString();
            Integer t = Integer.parseInt(time.split(":")[0]) * 60 + Integer.parseInt(time.split(":")[1]);
            scheduleTime.setTime(t);
            scheduleTime.setSchedule(schedule);
            ScheduleTimeService.createSchedule(scheduleTime);
        }

        return true;
    }

    private void initReminderTimes() {
        reminderTimes = new ArrayList<Date>();
        reminderTimes.add(new Date(0, 0, 0, 8, 0));
    }

    private boolean validateName() {
        String s = name.getText().toString();
        if (s != null && s.length() > 1 && s.length() < 64) {
            return true;
        } else {
            return false;
        }
    }

    private boolean validateDescription() {
        String s = description.getText().toString();
        if (s != null && s.length() > 255) {
            return false;
        } else {
            return true;
        }
    }

    private boolean validateMeasurementName() {
        String s = measurementName.getText().toString();
        if (s != null && s.length() > 1 && s.length() < 64) {
            return true;
        } else {
            return false;
        }
    }

    private void firstCardImplementation() {
        dropDownArrowReminder = (ImageView) findViewById(R.id.dropDownArrowReminder);
        visibleContentReminder = (LinearLayout) findViewById(R.id.visibleContentReminder);
        reminderTimesContent = (FlowLayout) findViewById(R.id.reminderTimesContent);
        hiddenTextReminder = (TextView) findViewById(R.id.hiddenTextReminder);
        hiddenTextReminder.setVisibility(View.INVISIBLE);
        contentReminder = (LinearLayout) findViewById(R.id.contentReminder);
        rotateIn = new RotateAnimation(0, 180, 8 * dp, 8 * dp);
        rotateIn.setDuration(300);
        rotateIn.setFillAfter(true);
        rotateIn.setAnimationListener(this);
        rotateOut = new RotateAnimation(180, 0, 8 * dp, 8 * dp);
        rotateOut.setDuration(300);
        rotateOut.setFillAfter(true);
        rotateOut.setAnimationListener(this);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeIn.setInterpolator(new LinearInterpolator());
        fadeIn.setAnimationListener(this);
        fadeIn.setFillAfter(true);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeOut.setInterpolator(new LinearInterpolator());
        fadeOut.setAnimationListener(this);
        fadeOut.setFillAfter(true);
        final FloatingActionButton fabAddReminder = (FloatingActionButton) findViewById(R.id.fab_add_reminder);
        fabAddReminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = new Date();
                reminderTimes.add(date);
                Measurement.ReminderTimeLayout layout = new Measurement.ReminderTimeLayout(date);
                FrameLayout newHour = layout.getLayout();
                reminderTimesContent.addView(newHour);
                ResetReminderHiddenText();
                layout.expand();
                newHour.callOnClick();
            }
        });
        if (reminderTimes.size() == 0) {
            Date date = new Date();
            reminderTimes.add(date);
            Measurement.ReminderTimeLayout layout = new Measurement.ReminderTimeLayout(date);
            FrameLayout newHour = layout.getLayout();
            reminderTimesContent.addView(newHour);
        } else
            for (int i = 0; i < reminderTimes.size(); i++) {
                final Measurement.ReminderTimeLayout reminderTimeLayout = new Measurement.ReminderTimeLayout(reminderTimes.get(i));
                final FrameLayout newHour = reminderTimeLayout.getLayout();
                reminderTimesContent.addView(newHour);
                reminderTimeLayout.expand();
            }
        if (reminderTimes.size() == 1) {
            FrameLayout singleHour = (FrameLayout) reminderTimesContent.getChildAt(0);
            ((FrameLayout) singleHour.getChildAt(0)).getChildAt(1).setVisibility(View.GONE);
        }
        ResetReminderHiddenText();

    }

    private void secondCardImplementation() {
        LinearLayout reminderHeader = (LinearLayout) findViewById(R.id.reminder_header);
        LinearLayout scheduleHeader = (LinearLayout) findViewById(R.id.schedule_header);
        scheduleHeader.setOnClickListener((this));
        reminderHeader.setOnClickListener((this));
        dropDownArrowSchedule = (ImageView) findViewById(R.id.dropDownArrowSchedule);
        contentSchedule = (LinearLayout) findViewById(R.id.contentSchedule);
        visibleContentSchedule = (LinearLayout) findViewById(R.id.visibleContentSchedule);
        hiddenTextSchedule = (TextView) findViewById(R.id.hiddenTextSchedule);
        hiddenTextSchedule.setVisibility(View.INVISIBLE);
        rotateIn2 = new RotateAnimation(0, 180, 8 * dp, 8 * dp);
        rotateIn2.setDuration(300);
        rotateIn2.setFillAfter(true);
        rotateIn2.setAnimationListener(this);
        rotateOut2 = new RotateAnimation(180, 0, 8 * dp, 8 * dp);
        rotateOut2.setDuration(300);
        rotateOut2.setFillAfter(true);
        rotateOut2.setAnimationListener(this);
        fadeIn2 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeIn2.setInterpolator(new LinearInterpolator());
        fadeIn2.setAnimationListener(this);
        fadeIn2.setFillAfter(true);
        fadeOut2 = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeOut2.setInterpolator(new LinearInterpolator());
        fadeOut2.setAnimationListener(this);
        fadeOut2.setFillAfter(true);
        tvDate = (TextView) findViewById(R.id.tvDate);
        if (day == 1) {
            startDate = new Date();
        } else {
            DateTime d = new DateTime();
            d = d.plusDays(1);
            startDate = d.toDate();
        }
        tvDate.setText(startDateFormat.format(startDate));
        tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startDate = new Date(i - 1900, i1, i2);
                        tvDate.setText(startDateFormat.format(startDate));
                    }
                };
                java.util.Calendar calendar = java.util.Calendar.getInstance();

                new DatePickerDialog(Measurement.this, listener,
                        calendar.get(
                                java.util.Calendar.YEAR),
                        calendar.get(java.util.Calendar.MONTH),
                        calendar.get(java.util.Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

        remindBeforeMinutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Measurement.this);
                final View reminderMinutePicker = getLayoutInflater().inflate(R.layout.frag_cal_set_reminder_before_minute, null);
                minute = (EditText) reminderMinutePicker.findViewById(R.id.minute);
                minute.setText(remindBeforeMinutes.getText().toString());
                up = (TextView) reminderMinutePicker.findViewById(R.id.up);
                down = (TextView) reminderMinutePicker.findViewById(R.id.down);
                up.setOnTouchListener(Measurement.this);
                down.setOnTouchListener(Measurement.this);
                up.setOnLongClickListener(Measurement.this);
                down.setOnLongClickListener(Measurement.this);
                builder.setView(reminderMinutePicker);
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        remindBeforeMinutes.setText(minute.getText().toString());
                    }
                });
                builder.setNegativeButton("Cancel", null);
                builder.setTitle("Set reminder before minute");
                builder.create();
                builder.show();
            }
        });
        radioButtonDuration1 = (RadioButton) findViewById(R.id.b1);
        radioButtonDuration2 = (RadioButton) findViewById(R.id.b2);

        radioButtonDuration1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastRadioButtonDurationIndex = 1;
                radioButtonDuration2.setText("Number of days");
                hiddenTextDuration = "";
                radioButtonDuration2.setChecked(false);
            }
        });

        radioButtonDuration2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonDuration1.setChecked(false);
                final AlertDialog.Builder builder = new AlertDialog.Builder(Measurement.this);
                final View dayCount = getLayoutInflater().inflate(R.layout.frag_cal_set_reminder_before_minute, null);
                minute = (EditText) dayCount.findViewById(R.id.minute);
                minute.setText(remindBeforeMinutes.getText().toString());////////////////////////////////////////////////////////////////////////
                up = (TextView) dayCount.findViewById(R.id.up);
                down = (TextView) dayCount.findViewById(R.id.down);
                up.setOnTouchListener(Measurement.this);
                down.setOnTouchListener(Measurement.this);
                up.setOnLongClickListener(Measurement.this);
                down.setOnLongClickListener(Measurement.this);
                builder.setView(dayCount);
                builder.setTitle("Set number of days (from start date)");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lastRadioButtonDurationIndex == 1) {
                            radioButtonDuration1.setChecked(true);
                            radioButtonDuration2.setChecked(false);
                        }
                    }
                });
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lastRadioButtonDurationIndex = 2;
                        radioButtonDuration1.setChecked(false);
                        radioButtonDuration2.setText(Html.fromHtml("Number of days: "
                                + "<font color='#0FB1AC'>" + minute.getText().toString() + "</font>"));
                        hiddenTextDuration = "To take " + minute.getText().toString() + " days";
                    }
                });
                builder.create();
                builder.show();
            }
        });

        radioButtonDay1 = (RadioButton) findViewById(R.id.rb1);
        radioButtonDay2 = (RadioButton) findViewById(R.id.rb2);
        radioButtonDay3 = (RadioButton) findViewById(R.id.rb3);
        radioButtonDay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonDay2.setChecked(false);
                radioButtonDay3.setChecked(false);
                hiddenTextDays = "Every day";
                lastRadioButtonDayIndex = 1;
                radioButtonDay3.setText("Days interval");
                radioButtonDay2.setText("Specific days of week");
            }
        });
        radioButtonDay2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonDay1.setChecked(false);
                radioButtonDay3.setChecked(false);

                for (int i = 0; i < 7; i++) {
                    checkedDays[i] = mSelectedItems.contains(i) ? true : false;
                }
                AlertDialog.Builder builder = new AlertDialog.Builder(Measurement.this);
                builder.setTitle("Pick Days")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (lastRadioButtonDayIndex == 1) {
                                    radioButtonDay1.setChecked(true);
                                    radioButtonDay2.setChecked(false);
                                } else if (lastRadioButtonDayIndex == 3) {
                                    radioButtonDay3.setChecked(true);
                                    radioButtonDay2.setChecked(false);
                                }
                            }
                        })
                        .setPositiveButton("Set", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                radioButtonDay1.setChecked(false);
                                radioButtonDay3.setChecked(false);
                                String s;
                                if (mSelectedItems.contains(1) && mSelectedItems.contains(2)
                                        && mSelectedItems.contains(3) && mSelectedItems.contains(4)
                                        && mSelectedItems.contains(5) && mSelectedItems.contains(6)
                                        && mSelectedItems.contains(0)) {
                                    radioButtonDay1.setChecked(true);
                                    radioButtonDay2.setChecked(false);
                                    hiddenTextDays = "Every day";
                                    radioButtonDay2.setText("Specific days of week");
                                    lastRadioButtonDayIndex = 1;
                                    for (int i = 0; i < 7; i++) {
                                        checkedDays[i] = false;
                                    }
                                } else {
                                    s = (mSelectedItems.contains(0) ? "Sun, " : "")
                                            + (mSelectedItems.contains(1) ? "Mon, " : "")
                                            + (mSelectedItems.contains(2) ? "Tue, " : "")
                                            + (mSelectedItems.contains(3) ? "Wed, " : "")
                                            + (mSelectedItems.contains(4) ? "Thu, " : "")
                                            + (mSelectedItems.contains(5) ? "Fri, " : "")
                                            + (mSelectedItems.contains(6) ? "Sat, " : "");
                                    if (s.isEmpty()) {
                                        if (lastRadioButtonDayIndex == 1) {
                                            radioButtonDay1.setChecked(true);
                                            radioButtonDay2.setChecked(false);
                                        } else if (lastRadioButtonDayIndex == 3) {
                                            radioButtonDay3.setChecked(true);
                                            radioButtonDay2.setChecked(false);
                                        }
                                    } else {
                                        radioButtonDay2.setText(Html.fromHtml("Specific days of week: "
                                                + "<font color='#0FB1AC'>" + s.substring(0, s.length() - 2) + "</font>"));
                                        hiddenTextDays = "On " + s;
                                        radioButtonDay3.setText("Days interval");
                                        lastRadioButtonDayIndex = 2;
                                    }
                                    SpecificDaysOfWeek = s;
                                }
                            }
                        }).setMultiChoiceItems(R.array.dayofweek, checkedDays,
                        new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                if (isChecked) {
                                    mSelectedItems.add(which);
                                } else if (mSelectedItems.contains(which)) {
                                    mSelectedItems.remove(Integer.valueOf(which));
                                }
                            }

                        });
                builder.create();
                builder.show();
            }
        });

        radioButtonDay3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                radioButtonDay1.setChecked(false);
                radioButtonDay2.setChecked(false);
                final AlertDialog.Builder builder = new AlertDialog.Builder(Measurement.this);
                final View dayCount = getLayoutInflater().inflate(R.layout.frag_cal_set_reminder_before_minute, null);
                minute = (EditText) dayCount.findViewById(R.id.minute);
                minute.setText(remindBeforeMinutes.getText().toString());
                up = (TextView) dayCount.findViewById(R.id.up);
                down = (TextView) dayCount.findViewById(R.id.down);
                up.setOnTouchListener(Measurement.this);
                down.setOnTouchListener(Measurement.this);
                up.setOnLongClickListener(Measurement.this);
                down.setOnLongClickListener(Measurement.this);
                builder.setView(dayCount);
                builder.setTitle("Set days interval");
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (lastRadioButtonDayIndex == 1) {
                            radioButtonDay1.setChecked(true);
                            radioButtonDay3.setChecked(false);
                        } else if (lastRadioButtonDayIndex == 2) {
                            radioButtonDay2.setChecked(true);
                            radioButtonDay3.setChecked(false);
                        }
                    }
                });
                builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        lastRadioButtonDayIndex = 3;
                        radioButtonDay2.setText("Specific days of week");
                        radioButtonDay3.setText(Html.fromHtml("Days interval: "
                                + "<font color='#0FB1AC'>" + minute.getText().toString() + "</font>"));
                        radioButtonDay1.setChecked(false);
                        hiddenTextDays = "Every " + minute.getText().toString() + " days";
                        NumberOfRepetition = Integer.parseInt(minute.getText().toString());
                        DaysInterval = Integer.parseInt(minute.getText().toString());
                    }
                });
                builder.create();
                builder.show();
            }
        });

        final List<String> spinnerArray = GoalService.getAllGoalsName();

        //spinnerArray.add(0,"");
        spinnerAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spGoal = (Spinner) findViewById(R.id.spinner);
        spGoal.setAdapter(spinnerAdapter);
        spGoal.setSelection(0, false);
        spGoal.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinnerPosition = position;
                //if(position!=0)
                {
                    goal = GoalService.getGoalByPosition(SpinnerPosition);
                    spinnerSelectionCount++;
                    if (goal.getValue().trim().isEmpty() && spinnerSelectionCount != 1) {
                        final AlertDialog.Builder builder = new AlertDialog.Builder(Measurement.this);
                        builder.setTitle("Add goal for " + goal.getName());
                        if (position != 5) {
                            final View addGoal = getLayoutInflater().inflate(R.layout.alertdialog_add_goal, null);
                            final EditText goalValue = (EditText) addGoal.findViewById(R.id.etGoalValue);
                            final TextView tvMesurUnit = (TextView) addGoal.findViewById(R.id.tvMesurUnit);

                            String[] valueType = new String[]{
                                    getString(R.string.goal1_value), getString(R.string.goal2_value), getString(R.string.goal3_value),
                                    getString(R.string.goal4_value), getString(R.string.goal5_value), getString(R.string.goal6_value)};

                            tvMesurUnit.setText(valueType[position]);

                            Drawable drawable = goalValue.getBackground();
                            drawable.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                            goalValue.setBackgroundDrawable(drawable);

                            goalValue.setRawInputType(Configuration.KEYBOARD_QWERTY);
                            goalValue.setActivated(true);
                            builder.setView(addGoal)
                                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {

                                        }
                                    });
                            alertDialog = builder.create();
                            alertDialog.show();
                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (goalValue.getText().toString().isEmpty()) {
                                        goalValue.setError("required");
                                        goalValue.requestFocus();
                                        return;
                                    } else {
                                        List<Goal> goals = GoalService.getAllGoals();
                                        for (Goal item : goals) {
                                            if (item.getName() == goal.getName())
                                                item.setValue(goalValue.getText().toString());
                                            GoalService.updateGoal(item);
                                        }

                                        final List<String> spinnerArray = GoalService.getAllGoalsName();
                                        //spinnerArray.add(0,"");
                                        spinnerAdapter = new ArrayAdapter<String>(Measurement.this, android.R.layout.simple_spinner_item, spinnerArray);
                                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                        spGoal = (Spinner) findViewById(R.id.spinner);
                                        spGoal.setAdapter(spinnerAdapter);
                                        spGoal.setSelection(SpinnerPosition, false);
                                        alertDialog.dismiss();
                                    }
                                }
                            });
                        } else if (position == 5) {
                            final View addGoal = getLayoutInflater().inflate(R.layout.alertdialog_add_goal_pressure, null);
                            final EditText etSystolicValue = (EditText) addGoal.findViewById(R.id.etSystolicValue);
                            final EditText etDiastolicValue = (EditText) addGoal.findViewById(R.id.etDiastolicValue);

                            Drawable drawableSystolicValue = etSystolicValue.getBackground();
                            drawableSystolicValue.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                            etSystolicValue.setBackgroundDrawable(drawableSystolicValue);

                            Drawable drawableDiastolicValue = etSystolicValue.getBackground();
                            drawableDiastolicValue.setColorFilter(getResources().getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_ATOP);
                            etSystolicValue.setBackgroundDrawable(drawableDiastolicValue);

                            etSystolicValue.setRawInputType(Configuration.KEYBOARD_QWERTY);
                            etDiastolicValue.setRawInputType(Configuration.KEYBOARD_QWERTY);
                            etSystolicValue.setFocusable(true);
                            builder.setView(addGoal)
                                    .setPositiveButton("Save", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                        }
                                    });
                            final AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
//                                    if (goalValue.getText().toString().isEmpty()) {
//                                            goalValue.setError("required");
//                                            goalValue.requestFocus();
//                                        } else

                                    if (etSystolicValue.getText().toString().isEmpty()) {
                                        etSystolicValue.setError("required");
                                        etSystolicValue.requestFocus();
                                        return;
                                    }
                                    if (etDiastolicValue.getText().toString().isEmpty()) {
                                        etDiastolicValue.setError("required");
                                        etDiastolicValue.requestFocus();
                                        return;
                                    }
                                    {
                                        List<Goal> goals = GoalService.getAllGoals();
                                        for (Goal item : goals) {
                                            if (item.getName() == goal.getName())
                                                item.setValue(etSystolicValue.getText().toString() + "," + etDiastolicValue.getText().toString());
                                            GoalService.updateGoal(item);
                                        }
                                        final List<String> spinnerArray = GoalService.getAllGoalsName();
                                        //spinnerArray.add(0,"");
                                        spinnerAdapter = new ArrayAdapter<String>(Measurement.this, android.R.layout.simple_spinner_item, spinnerArray);

                                        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                                        spGoal = (Spinner) findViewById(R.id.spinner);
                                        spGoal.setAdapter(spinnerAdapter);
                                        spGoal.setSelection(SpinnerPosition, false);
                                        alertDialog.dismiss();
                                    }
                                }
                            });
                        }
                    }
                }
//                if(position == spinnerArray.size()-1){
//                    Intent intent = new Intent(Measurement.this, GoalActivity.class);
//                    intent.putExtra("ViewEditGoals", "Edit");
//                    startActivityForResult(intent, 1000);
//                    spGoal.setSelection(0);
//                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        final List<String> spinnerArray = GoalService.getAllGoalsName();
        spinnerArray.add("Edit Goals");
        spinnerArray.add(0, "");
        spinnerAdapter.clear();
        spinnerAdapter.addAll(spinnerArray);
        spinnerAdapter.notifyDataSetChanged();
        spGoal.invalidate();
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == fadeIn) {
            dropDownArrowReminder.startAnimation(rotateIn);
            hiddenTextReminder.setVisibility(View.VISIBLE);
            collapse(visibleContentReminder);
        } else if (animation == fadeOut) {
            hiddenTextReminder.setVisibility(View.INVISIBLE);
            dropDownArrowReminder.startAnimation(rotateOut);
            expand(visibleContentReminder);
        } else if (animation == rotateIn) {
            contentReminder.clearAnimation();
        } else if (animation == rotateOut) {
            contentReminder.clearAnimation();
        } else if (animation == fadeIn2) {
            hiddenTextSchedule.setVisibility(View.VISIBLE);
            dropDownArrowSchedule.startAnimation(rotateIn2);
            collapse(visibleContentSchedule);
        } else if (animation == fadeOut2) {
            hiddenTextSchedule.setVisibility(View.INVISIBLE);
            dropDownArrowSchedule.startAnimation(rotateOut2);
            expand(visibleContentSchedule);
        } else if (animation == rotateIn2) {
            contentSchedule.clearAnimation();
        } else if (animation == rotateOut2) {
            contentSchedule.clearAnimation();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.reminder_header:
                if (visibleContentReminder.getVisibility() == View.GONE) {
                    contentReminder.startAnimation(fadeOut);
                } else
                    contentReminder.startAnimation(fadeIn);
                break;
            case R.id.schedule_header:
                if (visibleContentSchedule.getVisibility() == View.GONE) {
                    contentSchedule.startAnimation(fadeOut2);
                } else {
                    contentSchedule.startAnimation(fadeIn2);
                    if (hiddenTextDuration.length() > 0)
                        hiddenTextSchedule.setText(hiddenTextDays + ": " + hiddenTextDuration);
                    else
                        hiddenTextSchedule.setText(hiddenTextDays);
                }
                break;
        }
    }

    public void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        final Animation a = new Animation() {
            @Override
            public void setDuration(long durationMillis) {
                super.setDuration(300);
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                    v.setVisibility(View.VISIBLE);
                    v.requestLayout();
                } else {
                    v.getLayoutParams().height = (int) (targetHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();
        final Animation a = new Animation() {
            @Override
            public void setDuration(long durationMillis) {
                super.setDuration(300);
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                if (interpolatedTime == 1) {
                    v.setVisibility(View.GONE);
                } else {
                    v.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                    v.requestLayout();
                }
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    private void ResetReminderHiddenText() {
        String s = "";
        for (int i = 0; i < reminderTimes.size(); i++) {
            s += simpleDateFormat.format(reminderTimes.get(i)) + ", ";
        }
        hiddenTextReminder.setText(s.substring(0, s.length() - 2));
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (longTouched || firstTouched) {
            switch (v.getId()) {
                case R.id.up:
                    if (Integer.parseInt(minute.getText().toString()) != 120) {
                        String s = Integer.parseInt(minute.getText().toString()) + 1 + "";
                        minute.setText(s);
                    }
                    break;
                case R.id.down:
                    if (Integer.parseInt(minute.getText().toString()) != 0) {
                        String s = Integer.parseInt(minute.getText().toString()) - 1 + "";
                        minute.setText(s);
                    }
                    break;
            }
        }
        if ((event.getAction() == MotionEvent.ACTION_UP)) {
            longTouched = false;
            firstTouched = true;
        }
        if (!longTouched && (event.getAction() == MotionEvent.ACTION_DOWN)) {
            firstTouched = false;
        }
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        longTouched = true;
        return true;
    }

    private class ReminderTimeLayout {
        private FrameLayout frameLayout;
        private LinearLayout leftGroup;
        private FrameLayout fadeLayout;
        private TextView tvTime;
        private ImageView delete;
        private Date date;
        private int iDelete = 1;
        private int iFade = 0;

        public ReminderTimeLayout(Date d) {
            final FrameLayout frameLayout0 = (FrameLayout) reminderTimesContent.getChildAt(0);
            if (frameLayout0 != null) {
                ((FrameLayout) frameLayout0.getChildAt(0)).getChildAt(iDelete).setVisibility(View.VISIBLE);
            }
            Measurement.ReminderTimeLayout.this.date = d;
            String time = simpleDateFormat.format(date);
            frameLayout = new FrameLayout(Measurement.this);
            leftGroup = new LinearLayout(Measurement.this);
            fadeLayout = new FrameLayout(Measurement.this);
            tvTime = new TextView(Measurement.this);
            delete = new ImageView(Measurement.this);

            final LinearLayout.LayoutParams frameLayoutParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            frameLayoutParams.setMargins(0, 0, 0, 8 * dp);
            frameLayout.setLayoutParams(frameLayoutParams);

            final FrameLayout.LayoutParams fadeLayoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, 32 * dp);
            fadeLayout.setLayoutParams(fadeLayoutParams);

            final FrameLayout.LayoutParams leftLayoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            leftLayoutParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
            leftLayoutParams.setMargins(8 * dp, 0, 4 * dp, 0);

            final FrameLayout.LayoutParams tvDeleteLayoutParams = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            tvDeleteLayoutParams.gravity = Gravity.END | Gravity.CENTER_VERTICAL;
            tvDeleteLayoutParams.setMargins(4 * dp, 0, 4 * dp, 0);

            final LinearLayout.LayoutParams vlParams = new LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            vlParams.gravity = Gravity.CENTER_VERTICAL;
            vlParams.setMargins(8 * dp, 0, 32 * dp, 0);

            leftLayoutParams.gravity = Gravity.START | Gravity.CENTER_VERTICAL;
            leftGroup.setLayoutParams(leftLayoutParams);
            tvTime.setTextColor(ContextCompat.getColor(Measurement.this, R.color.colorPrimary));
            tvTime.setLayoutParams(vlParams);
            tvTime.setText(time);
            tvTime.setTypeface(Typeface.create("serif-monospace", Typeface.NORMAL));

            delete.setLayoutParams(tvDeleteLayoutParams);
//            delete.setImageResource(R.drawable.delete);
            delete.setBackgroundResource(R.drawable.delete);
            delete.setPadding(4 * dp, 0, 4 * dp, 0);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DeleteLayout();
                }
            });
            ImageView alarmIcon = new ImageView(Measurement.this);
            alarmIcon.setImageResource(R.drawable.alarm);
            leftGroup.addView(alarmIcon);
            leftGroup.addView(tvTime);
            fadeLayout.addView(leftGroup);
            fadeLayout.addView(delete);
            fadeLayout.setBackgroundResource(R.drawable.reminder_background);
            frameLayout.addView(fadeLayout);
            frameLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Measurement.this);
                    final View dayCount = getLayoutInflater().inflate(R.layout.frag_cal_set_hour_count, null);
                    final TimePicker timePicker = (TimePicker) dayCount.findViewById(R.id.timepicker);
                    timePicker.setIs24HourView(true);
                    timePicker.setCurrentHour(date.getHours());
                    timePicker.setCurrentMinute(date.getMinutes());
                    builder.setView(dayCount);
                    builder.setPositiveButton("Set", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            int pos = reminderTimes.indexOf(date);
                            date = new Date(0, 0, 0, timePicker.getCurrentHour(), timePicker.getCurrentMinute());
                            tvTime.setText(simpleDateFormat.format(date));
                            reminderTimes.set(pos, date);
                            ResetReminderHiddenText();
                        }
                    });
                    builder.setNegativeButton("Cancel", null);
                    builder.setTitle("Set Time");
                    builder.create();
                    builder.show();
                }
            });
            fadeLayout.setVisibility(View.INVISIBLE);
            collapse(0);
        }

        public FrameLayout getLayout() {
            return frameLayout;
        }

        private void DeleteLayout() {
            for (int i = 0; i < reminderTimes.size(); i++) {
                FrameLayout frlay = (FrameLayout) reminderTimesContent.getChildAt(i);
                ((FrameLayout) frlay.getChildAt(0)).getChildAt(iDelete).setClickable(false);
            }
            final Animation fade = new AlphaAnimation(1, 0);
            fade.setDuration(200);
            fade.setFillAfter(true);
            frameLayout.startAnimation(fade);
            fade.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    collapse(300);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });

        }

        public void collapse(final int duration) {
            fadeLayout.setVisibility(View.INVISIBLE);
            final int initialHeight = frameLayout.getMeasuredHeight();
            final Animation collapse = new Animation() {
                @Override
                public void setDuration(long durationMillis) {
                    super.setDuration(duration);
                }

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        frameLayout.setVisibility(View.GONE);
                    } else {
                        frameLayout.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        frameLayout.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            collapse.setDuration(duration);
            frameLayout.setAnimation(collapse);
            collapse.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    ((ViewManager) frameLayout.getParent()).removeView(frameLayout);
                    int pos = reminderTimes.indexOf(date);
                    reminderTimes.remove(pos);
                    ResetReminderHiddenText();

                    if (reminderTimes.size() == 1) {
                        FrameLayout frameLayout = (FrameLayout) reminderTimesContent.getChildAt(0);
                        ((FrameLayout) frameLayout.getChildAt(0)).getChildAt(iDelete).setVisibility(View.GONE);
                    }
                    for (int i = 0; i < reminderTimes.size(); i++) {
                        FrameLayout frlay = (FrameLayout) ((FrameLayout) reminderTimesContent.getChildAt(i)).getChildAt(0);
                        frlay.getChildAt(iDelete).setClickable(true);
                    }

                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
        }

        public void expand() {
            fadeLayout.setVisibility(View.VISIBLE);
            frameLayout.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            final int targetHeight = frameLayout.getMeasuredHeight();
            frameLayout.getLayoutParams().height = 1;
//            fadeLayout.setVisibility(View.INVISIBLE);
            frameLayout.clearAnimation();
            final Animation a = new Animation() {
                @Override
                public void setDuration(long durationMillis) {
                    super.setDuration(300);
                }

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        frameLayout.getLayoutParams().height = LinearLayout.LayoutParams.WRAP_CONTENT;
                        frameLayout.requestLayout();
                        fadeLayout.setVisibility(View.VISIBLE);
                        fadeLayout.getChildAt(iFade).setVisibility(View.VISIBLE);
                        if (reminderTimes.size() > 1) {
                            ((FrameLayout) frameLayout.getChildAt(0)).getChildAt(iDelete).setVisibility(View.VISIBLE);
                        }
                    } else {
                        frameLayout.getLayoutParams().height = (int) (targetHeight * interpolatedTime);
                        frameLayout.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };
            a.setDuration(300);
            frameLayout.startAnimation(a);
        }
    }

}
