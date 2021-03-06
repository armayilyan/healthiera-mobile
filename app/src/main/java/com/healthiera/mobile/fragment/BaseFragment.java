package com.healthiera.mobile.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.Doctor;

/**
 * Created by Davit on 11.09.2016.
 */
public class BaseFragment extends Fragment {
    public static class ProviderDescription extends AppCompatActivity {

        private TextView textViewName;

        private TextView textViewSpecification;

        private TextView textViewPN;

        private TextView textViewEmail;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.provider_description);

            final Intent intent = getIntent();
            final Doctor doctor = (Doctor) intent.getSerializableExtra("CurrentProvider");

            textViewName = (TextView) findViewById(R.id.textViewName);
            textViewSpecification = (TextView) findViewById(R.id.textViewSpecification);
            textViewPN = (TextView) findViewById(R.id.textViewPhoneNumber);
            textViewEmail = (TextView) findViewById(R.id.textViewEmail);

            textViewName.setText(doctor.getName());
            textViewSpecification.setText(doctor.getSpecification());
            textViewPN.setText(doctor.getPhone());
            textViewEmail.setText(doctor.getEmail());
        }

    }
}
