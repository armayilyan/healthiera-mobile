package com.healthiera.mobile.activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.healthiera.mobile.R;
import com.healthiera.mobile.activity.main.MainActivity;
import com.healthiera.mobile.entity.Goal;
import com.healthiera.mobile.serivce.GoalService;

import java.util.ArrayList;
import java.util.List;


public class GoalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    GoalService service = new GoalService();
    GoalListAdapter goalListAdapter;
    List<Goal> goals;
    String[] value;
    Boolean dialogState = false;
    String message;
    private Button btn;
    private boolean flag = false;
    private String[] name;
    private String[] valueType;
    private int dp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        dp = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());

        setTitle("Goals");

        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(navigationView.getWindowToken(), 0);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        name = new String[]{
                getString(R.string.goal1_name), getString(R.string.goal2_name), getString(R.string.goal3_name)
                , getString(R.string.goal4_name), getString(R.string.goal5_name), getString(R.string.goal6_name),
                getString(R.string.goal7_name)};

        valueType = new String[]{
                getString(R.string.goal1_value), getString(R.string.goal2_value), getString(R.string.goal3_value),
                getString(R.string.goal4_value), getString(R.string.goal5_value), getString(R.string.goal6_value),
                getString(R.string.goal6_value)};


        value = new String[name.length];
        btn = (Button) findViewById(R.id.button_edit_goals);

        goals = new ArrayList<Goal>();
        goals = service.getAllGoals();

        btnState();

        goalListAdapter = new GoalListAdapter();

        ListView listView = (ListView) findViewById(R.id.list_view_goals);
        listView.setAdapter(goalListAdapter);
        listView.setItemsCanFocus(true);

        Intent intent = getIntent();
        message = intent.getStringExtra("ViewEditGoals");

        if ("Edit".equals(message)) {
            editGoalOnClick();
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean noNullValues = true;

                if (flag) { //click save
                    saveGoalOnClick();
                } else {
                    editGoalOnClick();
                }
            }
        });
        btn.callOnClick();
    }

    private void editGoalOnClick() {
        btn.setText(getString(R.string.save));
        flag = true;

        goalListAdapter.notifyDataSetChanged();
    }

    private void saveGoalOnClick() {

        flag = false;
        btn.setText(getString(R.string.edit));

        List<Goal> goals = service.getAllGoals();

//        for (Goal item : goals)
//        {
//            if(item.getName()==goal.getName())
//                item.setValue(goalValue.getText().toString());
//            GoalService.updateGoal(item);
//        }

//        service.deleteGoals();
//
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
        if ("Edit".equals(message)) {
            finish();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putBoolean("dialog", dialogState);
        outState.putBoolean("flag_btn", flag);
        outState.putStringArray("value", value);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        dialogState = savedInstanceState.getBoolean("dialog");
        flag = savedInstanceState.getBoolean("flag_btn");
        value = savedInstanceState.getStringArray("value");


        if (dialogState) {
            calldialog();
        }
        btnState();

    }


    public void btnState() {

        if (flag) {
            btn.setText(getString(R.string.save));
        } else {

            btn.setText(getString(R.string.edit));

            for (int i = 0; i < goals.size() && i < 7; i++) {
                value[i] = goals.get(i).getValue();
            }

        }
    }

    private void calldialog() {
        dialogState = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(GoalActivity.this);
        builder.setTitle(getString(R.string.goal_page_dialog_titl))
                .setMessage(getString(R.string.goal_page_dialog_message))
                .setCancelable(false)
                .setNegativeButton(getString(R.string.ok),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialogState = false;
                                dialog.cancel();

                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            Intent i = new Intent(this.getApplication(), MainActivity.class);
            startActivity(i);

        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Handler handler = new Handler();
        int id = item.getItemId();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (id == R.id.dashboard) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), MainActivity.class);
                            startActivity(i);
                        }
                    }, 260L);

//        } else if (id == R.id.health_data) {
//
//            drawer.closeDrawer(GravityCompat.START);
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), HealthDataActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);

        } else if (id == R.id.health_data1) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), HealthData_1.class);
                            startActivity(i);
                        }
                    }, 260L);

        } else if (id == R.id.care_plan_activity1) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), CarePlanActivity_1.class);
                            startActivity(i);
                        }
                    }, 260L);

        } else if (id == R.id.goals) {

            drawer.closeDrawer(GravityCompat.START);


//        } else if (id == R.id.care_plan_activity) {
//
//            drawer.closeDrawer(GravityCompat.START);
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), CarePlanActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);
//
//
        } else if (id == R.id.medical_id) {

            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), MedicalIdActivity.class);
                            startActivity(i);
                        }
                    }, 260L);

        } else if (id == R.id.educational_tips) {
            drawer.closeDrawer(GravityCompat.START);
            handler.postDelayed(
                    new Runnable() {
                        public void run() {
                            Intent i = new Intent(getApplication(), EducationalTipsActivity.class);
                            startActivity(i);
                        }
                    }, 260L);


//        } else if (id == R.id.health_data) {
//            drawer.closeDrawer(GravityCompat.START);
//            handler.postDelayed(
//                    new Runnable() {
//                        public void run() {
//                            Intent i = new Intent(getApplication(), EducationalTipsActivity.class);
//                            startActivity(i);
//                        }
//                    }, 260L);
//

        }
        return true;
    }

    //////////////////////////////////
    //          CARDS SECTION       //
    //////////////////////////////////

    private void CardImplementation(View v) {

        final ImageView dropDownArrow = (ImageView) v.findViewById(R.id.dropDownArrow);
        final LinearLayout content = (LinearLayout) v.findViewById(R.id.content);
        final LinearLayout visibleContent = (LinearLayout) v.findViewById(R.id.visibleContent);
        final LinearLayout hiddenLayout = (LinearLayout) v.findViewById(R.id.hiddenLayout);
        final TextView hiddenText = (TextView) v.findViewById(R.id.hiddenText);
        final TextView etalon = (TextView) v.findViewById(R.id.goal_item_etalon_header);
        final Animation fadeIn;
        final Animation fadeOut;
        final Animation rotateIn;
        final Animation rotateOut;
        Animation.AnimationListener a;
        LinearLayout header = (LinearLayout) v.findViewById(R.id.header);

        rotateIn = new RotateAnimation(180, 0, 8 * dp, 8 * dp);
        rotateIn.setDuration(300);
        rotateIn.setFillAfter(true);
        rotateOut = new RotateAnimation(0, 180, 8 * dp, 8 * dp);
        rotateOut.setDuration(300);
        rotateOut.setFillAfter(true);
        fadeIn = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeIn.setInterpolator(new LinearInterpolator());
        fadeIn.setFillAfter(true);
        fadeOut = AnimationUtils.loadAnimation(this, R.anim.fadein);
        fadeOut.setInterpolator(new LinearInterpolator());
        fadeOut.setFillAfter(true);

//        hiddenText.setVisibility(View.INVISIBLE);
        header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (visibleContent.getVisibility() == View.GONE) {
                    content.clearAnimation();
                    content.startAnimation(fadeOut);
                } else {
                    content.clearAnimation();
                    content.startAnimation(fadeIn);
                }
            }
        });

        a = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (animation == fadeIn) {
                    dropDownArrow.startAnimation(rotateIn);
                    collapse(visibleContent, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            hiddenLayout.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else if (animation == fadeOut) {
                    dropDownArrow.startAnimation(rotateOut);
                    expand(visibleContent, new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            hiddenLayout.setVisibility(View.INVISIBLE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                } else if (animation == rotateIn) {
                    content.clearAnimation();
                } else if (animation == rotateOut) {
                    content.clearAnimation();
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        };
        rotateIn.setAnimationListener(a);
        rotateOut.setAnimationListener(a);
        fadeIn.setAnimationListener(a);
        fadeOut.setAnimationListener(a);
    }

    public void collapse(final View v, Animation.AnimationListener onEnd) {
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
        a.setAnimationListener(onEnd);
        v.startAnimation(a);
    }

    public void expand(final View v, Animation.AnimationListener onEnd) {
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
        a.setAnimationListener(onEnd);
        v.startAnimation(a);
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

            final ViewHolder holder;
            if (convertView == null) {

                holder = new ViewHolder();
                LayoutInflater inflater = getLayoutInflater();
                convertView = inflater.inflate(R.layout.goal_item, null);
                holder.textName = (TextView) convertView.findViewById(R.id.goal_item_name);
                holder.inputValue = (EditText) convertView.findViewById(R.id.goal_item_ed_text);
                holder.valueType = (TextView) convertView.findViewById(R.id.goal_item_etalon);
                convertView.setTag(holder);

            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            holder.ref = position;
            holder.textName.setText(name[position]);

            if (value.length > 0) {
                if (position == 5) {
                    holder.inputValue.setText(value[5].split(",")[0]);//goals.get(position).getValue().toString());
                } else if (position == 6 && value[5].split(",").length > 1) {
                    holder.inputValue.setText(value[5].split(",")[1]);//goals.get(position).getValue().toString());
                } else {
                    holder.inputValue.setText(value[position]);//goals.get(position).getValue().toString());
                }
            } else {
                holder.inputValue.setText("");
            }


            // @Sectoiion_Arman_Edit

            holder.valueType.setText(valueType[position]);
            TextView hiddenText = (TextView) convertView.findViewById(R.id.hiddenText);
            TextView etalonHeader = (TextView) convertView.findViewById(R.id.goal_item_etalon_header);
            String s = holder.inputValue.getText().toString().trim();

            if (s.isEmpty() || s.compareTo("null") == 0) {
                etalonHeader.setVisibility(View.INVISIBLE);
                hiddenText.setText("Not Set");
                holder.inputValue.setText("");
            } else {
                hiddenText.setText(s);
                etalonHeader.setVisibility(View.VISIBLE);
                etalonHeader.setText(valueType[position]);
            }
            CardImplementation(convertView);
            // @ensSectoiion_Arman_Edit


            if (flag)

            {
                holder.inputValue.setEnabled(true);
//                holder.editTextValue.setBackground(getDrawable(R.drawable.background_goal_edite_text));

            } else

            {
                holder.inputValue.setEnabled(false);
//                holder.inputValue.setBackground(null);
            }

            holder.inputValue.addTextChangedListener(
                    new TextWatcher() {
                        @Override
                        public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
                            value[position] = arg0.toString();
                        }

                        @Override
                        public void beforeTextChanged(CharSequence arg0, int arg1, int arg, int arg3) {
                        }

                        @Override
                        public void afterTextChanged(Editable arg0) {
                        }
                    }

            );

            return convertView;
        }

        private class ViewHolder {
            TextView textName;
            EditText inputValue;
            TextView valueType;
            int ref;
        }
    }
}
