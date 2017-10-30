package com.healthiera.mobile.component.base;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.Doctor;
import com.healthiera.mobile.fragment.BaseFragment;
import com.healthiera.mobile.fragment.Dashboard;

import java.util.ArrayList;

/**
 * @author Davit Ter-Arakelyan
 * @date 12.08.2016
 */
public class BaseEditText extends EditText {

    public BaseEditText(Context context) {
        super(context);
    }

    public BaseEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter);
    }

    public static class CustomAdapter extends BaseAdapter {

        private static LayoutInflater inflater = null;

        private ArrayList<Doctor> doctors;

        private Context context;

        public CustomAdapter(Dashboard mainActivity, ArrayList<Doctor> doctors1) {
            // TODO Auto-generated constructor stub
            doctors = doctors1;
            context = mainActivity.getActivity();
            inflater = (LayoutInflater) context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return doctors.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final Holder holder = new Holder();
            final View rowView = inflater.inflate(R.layout.provideritem, null);
            holder.textViewName = (TextView) rowView.findViewById(R.id.tvProviderName);
            holder.textViewSpecification = (TextView) rowView.findViewById(R.id.tvProviderSpecification);
            holder.img = (ImageView) rowView.findViewById(R.id.ivImage);
            holder.textViewName.setText(doctors.get(position).getName());
            holder.textViewSpecification.setText(doctors.get(position).getSpecification());
            holder.img.setImageResource(R.drawable.provider_icon);
            rowView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    Toast.makeText(context, "You Clicked " + doctors.get(position).getName() + "doctor", Toast.LENGTH_LONG).show();

                    Intent myIntent = new Intent(context, BaseFragment.ProviderDescription.class);
                    myIntent.putExtra("CurrentProvider", doctors.get(position).getName()); //Optional parameters
                    context.startActivity(myIntent);
                }
            });
            return rowView;
        }

        class Holder {
            TextView textViewName;
            TextView textViewSpecification;
            ImageView img;
        }
    }
}
