package com.healthiera.mobile.fragment.healthData;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.component.base.BaseToggleButton;
import com.healthiera.mobile.entity.HealthDate;
import com.healthiera.mobile.entity.enumeration.HealthDateType;
import com.healthiera.mobile.serivce.HealthDateService;


public class Heredity extends Fragment implements CompoundButton.OnCheckedChangeListener,
        AdapterView.OnItemSelectedListener, View.OnTouchListener, View.OnLongClickListener {

    private static final HealthDateService HEALTH_DATA_SERVICE = new HealthDateService();
    private LinearLayout bulimiaLayout;
    private LinearLayout diabetesMelitusLayout;

    private CheckBox checkBoxFather;
    private CheckBox checkBoxMother;
    private CheckBox checkBoxGFather;
    private CheckBox checkBoxGMother;

    private Spinner spDiabetTypeFather;
    private Spinner spDiabetTypeMother;
    private Spinner spDiabetTypeGrandMother;
    private Spinner spDiabetTypeGrandFather;

    private TextView minusAgeFather;
    private TextView plusAgeFather;
    private TextView minusAgeMoter;
    private TextView plusAgeMoter;

    private TextView minusAgeGFather;
    private TextView plusAgeGFather;
    private TextView minusAgeGMoter;
    private TextView plusAgeGMoter;

    private EditText inputAgeFather;
    private EditText inputAgeMoter;
    private EditText inputAgeGFather;
    private EditText inputAgeGMoter;

    private BaseToggleButton toggleButtonBulimia;
    private BaseToggleButton toggleButtonDiabetesMelitus;
    private boolean longTouched = false;
    private boolean firstTouched = true;

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();

        v.getLayoutParams().height = 1;
        v.setVisibility(View.VISIBLE);
        Animation a = new Animation() {
            @Override
            public void setDuration(long durationMillis) {
                super.setDuration(300);
            }

            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                v.getLayoutParams().height = interpolatedTime == 1
                        ? LinearLayout.LayoutParams.WRAP_CONTENT
                        : (int) (targetHeight * interpolatedTime);
                v.requestLayout();
            }

            @Override
            public boolean willChangeBounds() {
                return true;
            }
        };

        // 1dp/ms
        a.setDuration((int) (targetHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    public static void collapse(final View v) {
        final int initialHeight = v.getMeasuredHeight();

        Animation a = new Animation() {
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

        // 1dp/ms
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_heredity, container, false);
        checkBoxFather = (CheckBox) view.findViewById(R.id.checkBoxF);
        checkBoxMother = (CheckBox) view.findViewById(R.id.checkBoxM);
        checkBoxGFather = (CheckBox) view.findViewById(R.id.checkBoxGM);
        checkBoxGMother = (CheckBox) view.findViewById(R.id.checkBoxGF);

        bulimiaLayout = (LinearLayout) view.findViewById(R.id.bulimia);
        diabetesMelitusLayout = (LinearLayout) view.findViewById(R.id.diabetes_melitus);
        LinearLayout ln = (LinearLayout) view.findViewById(R.id.ln1);
        toggleButtonBulimia = new BaseToggleButton(getContext());
        //////////
        spDiabetTypeFather = (Spinner) view.findViewById(R.id.spinner_diabet_typeF);
        spDiabetTypeMother = (Spinner) view.findViewById(R.id.spinner_diabet_typeM);
        spDiabetTypeGrandMother = (Spinner) view.findViewById(R.id.spinner_diabet_typeGM);
        spDiabetTypeGrandFather = (Spinner) view.findViewById(R.id.spinner_diabet_typeGF);
        spDiabetTypeFather.setOnItemSelectedListener(this);
        spDiabetTypeMother.setOnItemSelectedListener(this);
        spDiabetTypeGrandMother.setOnItemSelectedListener(this);
        spDiabetTypeGrandFather.setOnItemSelectedListener(this);
        minusAgeFather = (TextView) view.findViewById(R.id.age_downF);
        plusAgeFather = (TextView) view.findViewById(R.id.age_upF);
        minusAgeMoter = (TextView) view.findViewById(R.id.age_downM);
        plusAgeMoter = (TextView) view.findViewById(R.id.age_upM);
        minusAgeGFather = (TextView) view.findViewById(R.id.age_downGF);
        plusAgeGFather = (TextView) view.findViewById(R.id.age_upGF);
        minusAgeGMoter = (TextView) view.findViewById(R.id.age_downGM);
        plusAgeGMoter = (TextView) view.findViewById(R.id.age_upGM);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        minusAgeFather.setOnLongClickListener(this);
        minusAgeMoter.setOnLongClickListener(this);
        minusAgeGMoter.setOnLongClickListener(this);
        minusAgeGFather.setOnLongClickListener(this);
        plusAgeFather.setOnLongClickListener(this);
        plusAgeMoter.setOnLongClickListener(this);
        plusAgeGFather.setOnLongClickListener(this);
        plusAgeGMoter.setOnLongClickListener(this);
        minusAgeFather.setOnTouchListener(this);
        minusAgeMoter.setOnTouchListener(this);
        minusAgeGMoter.setOnTouchListener(this);
        minusAgeGFather.setOnTouchListener(this);
        plusAgeFather.setOnTouchListener(this);
        plusAgeMoter.setOnTouchListener(this);
        plusAgeGFather.setOnTouchListener(this);
        plusAgeGMoter.setOnTouchListener(this);
        inputAgeFather = (EditText) view.findViewById(R.id.et_ageF);
        inputAgeMoter = (EditText) view.findViewById(R.id.et_ageM);
        inputAgeGFather = (EditText) view.findViewById(R.id.et_ageGF);
        inputAgeGMoter = (EditText) view.findViewById(R.id.et_ageGM);
        bulimiaLayout = (LinearLayout) view.findViewById(R.id.bulimia);
        //////////

        HealthDate hd;
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.BULIMIA_FATHER);
        if (hd != null) checkBoxFather.setChecked(Boolean.valueOf(hd.getValue()));
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.BULIMIA_MOTHER);
        if (hd != null) checkBoxMother.setChecked(Boolean.valueOf(hd.getValue()));
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.BULIMIA_GF);
        if (hd != null) checkBoxGMother.setChecked(Boolean.valueOf(hd.getValue()));
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.BULIMIA_GM);
        if (hd != null) checkBoxGFather.setChecked(Boolean.valueOf(hd.getValue()));
        if (checkBoxFather.isChecked() || checkBoxMother.isChecked() || checkBoxGFather.isChecked() || checkBoxGMother.isChecked()) {
            toggleButtonBulimia.switchToggle();
        }
        ln.addView(toggleButtonBulimia);
        toggleButtonBulimia.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerForToggle1();
            }
        });
        toggleButtonBulimia.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerForToggle1();
            }
        });

        toggleButtonDiabetesMelitus = new BaseToggleButton(getContext());
        LinearLayout ln2 = (LinearLayout) view.findViewById(R.id.ln2);
        ln2.addView(toggleButtonDiabetesMelitus);
        toggleButtonDiabetesMelitus.thumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerForToggle2();
            }
        });
        toggleButtonDiabetesMelitus.track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handlerForToggle2();
            }
        });

        checkBoxFather.setOnCheckedChangeListener(this);
        checkBoxMother.setOnCheckedChangeListener(this);
        checkBoxGFather.setOnCheckedChangeListener(this);
        checkBoxGMother.setOnCheckedChangeListener(this);

        return view;
    }

    private void handlerForToggle1() {
        if (bulimiaLayout.getVisibility() == View.GONE) {
            expand(bulimiaLayout);
        } else
            collapse(bulimiaLayout);
    }

    private void handlerForToggle2() {
        if (diabetesMelitusLayout.getVisibility() == View.GONE) {
            expand(diabetesMelitusLayout);
        } else
            collapse(diabetesMelitusLayout);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (checkBoxFather.isChecked() || checkBoxMother.isChecked() || checkBoxGFather.isChecked() || checkBoxGMother.isChecked()) {
            if (!toggleButtonBulimia.selected)
                toggleButtonBulimia.switchToggle();
            HealthDate healthDate = new HealthDate();
            switch (buttonView.getId()) {
                case R.id.checkBoxF:
                    healthDate.setValue(String.valueOf(isChecked));
                    healthDate.setHealthDateType(HealthDateType.BULIMIA_FATHER);
                    HEALTH_DATA_SERVICE.createHealthDate(healthDate);
                    break;
                case R.id.checkBoxM:
                    healthDate.setValue(String.valueOf(isChecked));
                    healthDate.setHealthDateType(HealthDateType.BULIMIA_MOTHER);
                    HEALTH_DATA_SERVICE.createHealthDate(healthDate);
                    break;
                case R.id.checkBoxGF:
                    healthDate.setValue(String.valueOf(isChecked));
                    healthDate.setHealthDateType(HealthDateType.BULIMIA_GF);
                    HEALTH_DATA_SERVICE.createHealthDate(healthDate);
                    break;
                case R.id.checkBoxGM:
                    healthDate.setValue(String.valueOf(isChecked));
                    healthDate.setHealthDateType(HealthDateType.BULIMIA_GM);
                    HEALTH_DATA_SERVICE.createHealthDate(healthDate);
                    break;
            }
        } else if (toggleButtonBulimia.selected)
            toggleButtonBulimia.switchToggle();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        if (spDiabetTypeFather.getSelectedItemPosition() > 0 || spDiabetTypeMother.getSelectedItemPosition() > 0 ||
                spDiabetTypeGrandFather.getSelectedItemPosition() > 0 || spDiabetTypeGrandMother.getSelectedItemPosition() > 0) {
            if (!toggleButtonDiabetesMelitus.selected) toggleButtonDiabetesMelitus.switchToggle();
        } else if (toggleButtonDiabetesMelitus.selected) toggleButtonDiabetesMelitus.switchToggle();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (longTouched || firstTouched) {
            switch (v.getId()) {
                case R.id.age_upF:
                    if (Integer.parseInt(inputAgeFather.getText().toString()) != 99) {
                        String s = Integer.parseInt(inputAgeFather.getText().toString()) + 1 + "";
                        inputAgeFather.setText(s);
                    }
                    break;
                case R.id.age_downF:
                    if (Integer.parseInt(inputAgeFather.getText().toString()) != 0) {
                        String s = Integer.parseInt(inputAgeFather.getText().toString()) - 1 + "";
                        inputAgeFather.setText(s);
                    }
                    break;
                case R.id.age_upM:
                    if (Integer.parseInt(inputAgeMoter.getText().toString()) != 99) {
                        String s = Integer.parseInt(inputAgeMoter.getText().toString()) + 1 + "";
                        inputAgeMoter.setText(s);
                    }
                    break;
                case R.id.age_downM:
                    if (Integer.parseInt(inputAgeMoter.getText().toString()) != 0) {
                        String s = Integer.parseInt(inputAgeMoter.getText().toString()) - 1 + "";
                        inputAgeMoter.setText(s);
                    }
                    break;
                case R.id.age_upGF:
                    if (Integer.parseInt(inputAgeGFather.getText().toString()) != 99) {
                        String s = Integer.parseInt(inputAgeGFather.getText().toString()) + 1 + "";
                        inputAgeGFather.setText(s);
                    }
                    break;
                case R.id.age_downGF:
                    if (Integer.parseInt(inputAgeGFather.getText().toString()) != 0) {
                        String s = Integer.parseInt(inputAgeGFather.getText().toString()) - 1 + "";
                        inputAgeGFather.setText(s);
                    }
                    break;
                case R.id.age_upGM:
                    if (Integer.parseInt(inputAgeGMoter.getText().toString()) != 99) {
                        String s = Integer.parseInt(inputAgeGMoter.getText().toString()) + 1 + "";
                        inputAgeGMoter.setText(s);
                    }
                    break;
                case R.id.age_downGM:
                    if (Integer.parseInt(inputAgeGMoter.getText().toString()) != 0) {
                        String s = Integer.parseInt(inputAgeGMoter.getText().toString()) - 1 + "";
                        inputAgeGMoter.setText(s);
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
}
