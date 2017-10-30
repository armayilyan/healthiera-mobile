package com.healthiera.mobile.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.fragment.healthData.AnamnesisVitae;
import com.healthiera.mobile.fragment.healthData.Complains;
import com.healthiera.mobile.fragment.healthData.Heredity;

/**
 * Created by Davit on 11.09.2016.
 */
public class HealthData extends BaseFragment {
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.tvComplainsandSymptoms:
                    FragmentTransaction transaction = getFragmentManager().beginTransaction();
                    transaction
                            .addToBackStack("HD-C")
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                            .replace(R.id.Content_id_, new Complains())
                            .commit();
                    break;
                case R.id.tvHeredity:
                    getFragmentManager().beginTransaction()
                            .addToBackStack("HD-H")
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                            .replace(R.id.Content_id_, new Heredity())
                            .commit();
                    break;
                case R.id.tvStatus:
//                    getFragmentManager().beginTransaction()
//                            .addToBackStack("HD-S")
//                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
//                            .replace(R.id.Content_id_, new Status())
//                            .commit();
//                    break;
                case R.id.tvSpecialDevices:

                    break;
                case R.id.tvAnamnesisVitae:
                    getFragmentManager().beginTransaction()
                            .addToBackStack("HD-A")
                            .setCustomAnimations(R.anim.slide_left, R.anim.slide_right)
                            .replace(R.id.Content_id_, new AnamnesisVitae())
                            .commit();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.health_data, container, false);
        final TextView tvComplainsandSymptoms = (TextView) rootView.findViewById(R.id.tvComplainsandSymptoms);
        final TextView tvHeredity = (TextView) rootView.findViewById(R.id.tvHeredity);
        final TextView tvStatus = (TextView) rootView.findViewById(R.id.tvStatus);
        final TextView tvSpecialDevices = (TextView) rootView.findViewById(R.id.tvSpecialDevices);
        final TextView tvAnamnesisVitae = (TextView) rootView.findViewById(R.id.tvAnamnesisVitae);


        tvComplainsandSymptoms.setOnClickListener(onClickListener);
        tvHeredity.setOnClickListener(onClickListener);
        tvStatus.setOnClickListener(onClickListener);
        tvSpecialDevices.setOnClickListener(onClickListener);
        tvAnamnesisVitae.setOnClickListener(onClickListener);
        return rootView;
    }

}