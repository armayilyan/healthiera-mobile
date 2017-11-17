package com.healthiera.mobile.fragment.healthData;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.HealthDate;
import com.healthiera.mobile.entity.enumeration.HealthDateType;
import com.healthiera.mobile.entity.enumeration.Hormone;
import com.healthiera.mobile.serivce.HealthDateService;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


/**
 * @author Davit Ter-Arakelyan
 * @date 02.11.2016
 */

public class AnamnesisVitae extends Fragment implements CompoundButton.OnCheckedChangeListener {

    private static final HealthDateService HEALTH_DATA_SERVICE = new HealthDateService();
    private Spinner sphormone;
    private Button btnSave;
    private TextView tvStartDate, tvEndDate;
    private Date startDate, endDate;
    private SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    private CheckBox cbHypertension, cbGlucocorticoidsintake;
    private LinearLayout lGlucocorticoidsintake;

    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View v) {

            if (!tvEndDate.getText().toString().isEmpty() && endDate.compareTo(startDate) < 0) {
                tvEndDate.setError("End date must be greater than Start date");
            }
            HEALTH_DATA_SERVICE.createGlucocorticoidsIntake(startDate, endDate, Hormone.values()[sphormone.getSelectedItemPosition()]);

        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_anamnesis_vitae, container, false);
        sphormone = (Spinner) view.findViewById(R.id.sphormone);
        sphormone.setAdapter(new ArrayAdapter<Hormone>(getContext(), android.R.layout.simple_list_item_single_choice, Hormone.values()));
        tvStartDate = (TextView) view.findViewById(R.id.tvstartDate);
        tvEndDate = (TextView) view.findViewById(R.id.tvEndDate);
        initDates();
        cbGlucocorticoidsintake = (CheckBox) view.findViewById(R.id.cbGlucocorticoidsintake);
        cbGlucocorticoidsintake.setOnCheckedChangeListener(this);
        lGlucocorticoidsintake = (LinearLayout) view.findViewById(R.id.lGlucocorticoidsintake);
        final List<HealthDate> g = new HealthDateService().findGlucocorticoidsIntake();
        if (g.size() == 3) {
            cbGlucocorticoidsintake.setChecked(true);
            lGlucocorticoidsintake.setVisibility(View.VISIBLE);
            switch (g.get(0).getValue()) {
                case "PREDNIZALONE":
                    sphormone.setSelection(0);
                    break;
                case "DEXAMETHAZONE":
                    sphormone.setSelection(1);
                    break;
                case "HYDROCORTIZONE":
                    sphormone.setSelection(2);
                    break;
            }
            startDate = g.get(1).getDate();
            endDate = g.get(2).getDate();
            tvStartDate.setText(g.get(1).getValue());
            tvEndDate.setText(g.get(2).getValue());
        } else if (g.size() == 1) {
            cbGlucocorticoidsintake.setChecked(false);
        }
        btnSave = (Button) view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(onClickListener);
        cbHypertension = (CheckBox) view.findViewById(R.id.cbHypertension);
        cbHypertension.setOnCheckedChangeListener(this);
        final HealthDate hd = new HealthDateService().findHealthDatesByType(HealthDateType.HYPERTENSION);
        if (hd != null) cbHypertension.setChecked(Boolean.valueOf(hd.getValue()));
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.cbHypertension) {
            final HealthDate healthDate = new HealthDate();
            healthDate.setValue(String.valueOf(isChecked));
            healthDate.setHealthDateType(HealthDateType.HYPERTENSION);
            HEALTH_DATA_SERVICE.createHealthDate(healthDate);
        } else if (buttonView.getId() == R.id.cbGlucocorticoidsintake) {
            if (isChecked)
                lGlucocorticoidsintake.setVisibility(View.VISIBLE);
            else {
                final HealthDate healthDate = new HealthDate();
                healthDate.setValue(String.valueOf(false));
                healthDate.setHealthDateType(HealthDateType.GLUCOCRITICOIDS_INTAKE);
                HEALTH_DATA_SERVICE.createHealthDate(healthDate);
                lGlucocorticoidsintake.setVisibility(View.GONE);
            }
        }
    }

    private void initDates() {
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        startDate = new Date(i - 1900, i1, i2);
                        tvStartDate.setText(sdf.format(startDate));
                    }
                };
                java.util.Calendar calendar = java.util.Calendar.getInstance();

                new DatePickerDialog(getActivity(), listener,
                        calendar.get(
                                java.util.Calendar.YEAR),
                        calendar.get(java.util.Calendar.MONTH),
                        calendar.get(java.util.Calendar.DAY_OF_MONTH)
                ).show();
            }
        });
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        endDate = new Date(i - 1900, i1, i2);
                        tvEndDate.setText(sdf.format(endDate));
                    }
                };
                java.util.Calendar calendar = java.util.Calendar.getInstance();

                new DatePickerDialog(getActivity(), listener,
                        calendar.get(
                                java.util.Calendar.YEAR),
                        calendar.get(java.util.Calendar.MONTH),
                        calendar.get(java.util.Calendar.DAY_OF_MONTH)
                ).show();
            }
        });

    }
}
