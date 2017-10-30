package com.healthiera.mobile.fragment.healthData;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.healthiera.mobile.R;
import com.healthiera.mobile.entity.HealthDate;
import com.healthiera.mobile.entity.enumeration.HealthDateType;
import com.healthiera.mobile.serivce.HealthDateService;

import java.util.ArrayList;
import java.util.List;


public class Complains extends Fragment implements Animation.AnimationListener, View.OnClickListener, View.OnLongClickListener, View.OnTouchListener {
    private static final HealthDateService HEALTH_DATA_SERVICE = new HealthDateService();
    private boolean obesty = false;
    private boolean thirsty = false;
    private boolean profuseSweating = false;
    private boolean nocturia = false;
    private boolean polyuria = false;
    private boolean polydipsia = false;
    private boolean bulimia = false;
    private Animation obestyToggleOn;
    private Animation obestyToggleOff;
    private Animation thirstyToggleOn;
    private Animation thirstyToggleOff;
    private Animation profuseSweatingToggleOn;
    private Animation profuseSweatingToggleOff;
    private Animation nocturiaToggleOn;
    private Animation nocturiaToggleOff;
    private Animation polyuriaToggleOn;
    private Animation polyuriaToggleOff;
    private Animation polydipsiaToggleOn;
    private Animation polydipsiaToggleOff;
    private Animation bulimiaToggleOn;
    private Animation bulimiaToggleOff;
    private ImageView obestyThumb;
    private ImageView obestyTrack;
    private ImageView thirstyThumb;
    private ImageView thirstyTrack;
    private ImageView profuseSweatingThumb;
    private ImageView profuseSweatingTrack;
    private ImageView nocturiaThumb;
    private ImageView nocturiaTrack;
    private ImageView polyuriaThumb;
    private ImageView polyuriaTrack;
    private ImageView polydipsiaThumb;
    private ImageView polydipsiaTrack;
    private ImageView bulimiaThumb;
    private ImageView bulimiaTrack;
    private LinearLayout urination;
    private EditText nocturiaTextInput;
    private TextView txtUrination;
    private TextView minusUrination;
    private TextView plusUrination;
    private boolean longTouched = false;
    private boolean firstTouched = true;

    private int toX;
    private int to_X;

    public static void expand(final View v) {
        v.measure(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        final int targetHeight = v.getMeasuredHeight();
        // Older versions of android (pre API 21) cancel animations for views with a height of 0.
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
        a.setDuration((int) (initialHeight / v.getContext().getResources().getDisplayMetrics().density));
        v.startAnimation(a);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_complains, container, false);
        initializeView(view);
        return view;
    }

    private void initializeView(View view) {

        urination = (LinearLayout) view.findViewById(R.id.urination);
        HealthDate hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.OBESTY);
        view = initializeObesty(view, hd);
        view = initializeThirsty(view, hd);
        view = initializeProfuseSweating(view, hd);
        view = initializeNocturia(view, hd);
        view = initializePolyuria(view, hd);
        view = initializePolydipsia(view, hd);
        view = initializeBulimia(view, hd);
    }

    private View initializePolyuria(View view, HealthDate hd) {
        polyuriaTrack = (ImageView) view.findViewById(R.id.ivPolyuriaTrack);
        polyuriaThumb = (ImageView) view.findViewById(R.id.ivPolyuriaThumb);
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.POLYURIA);
        if (hd != null && hd.getValue().equals("true")) {
            polyuriaTrack.setImageResource(R.drawable.switch_track);
            polyuriaThumb.setImageResource(R.drawable.switch_thumb);
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) polyuriaThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            polyuria = !polyuria;
        }
        polyuriaToggleOn = new TranslateAnimation(0, toX, 0, 0);
        polyuriaToggleOn.setInterpolator(new LinearInterpolator());
        polyuriaToggleOn.setFillAfter(true);
        polyuriaToggleOn.setDuration(200);
        polyuriaToggleOff = new TranslateAnimation(0, to_X, 0, 0);
        polyuriaToggleOff.setInterpolator(new LinearInterpolator());
        polyuriaToggleOff.setFillAfter(true);
        polyuriaToggleOff.setDuration(200);
        polyuriaToggleOn.setAnimationListener(this);
        polyuriaToggleOff.setAnimationListener(this);
        polyuriaTrack.setOnClickListener(this);
        polyuriaThumb.setOnClickListener(this);
        return view;
    }

    private View initializePolydipsia(View view, HealthDate hd) {
        polydipsiaTrack = (ImageView) view.findViewById(R.id.ivPolydipsiaTrack);
        polydipsiaThumb = (ImageView) view.findViewById(R.id.ivPolydipsiaThumb);
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.POLYDIPSIA);
        if (hd != null && hd.getValue().equals("true")) {
            polydipsiaTrack.setImageResource(R.drawable.switch_track);
            polydipsiaThumb.setImageResource(R.drawable.switch_thumb);
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) polydipsiaThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            polydipsia = !polydipsia;
        }
        polydipsiaToggleOn = new TranslateAnimation(0, toX, 0, 0);
        polydipsiaToggleOn.setInterpolator(new LinearInterpolator());
        polydipsiaToggleOn.setFillAfter(true);
        polydipsiaToggleOn.setDuration(200);
        polydipsiaToggleOff = new TranslateAnimation(0, to_X, 0, 0);
        polydipsiaToggleOff.setInterpolator(new LinearInterpolator());
        polydipsiaToggleOff.setFillAfter(true);
        polydipsiaToggleOff.setDuration(200);
        polydipsiaToggleOn.setAnimationListener(this);
        polydipsiaToggleOff.setAnimationListener(this);
        polydipsiaTrack.setOnClickListener(this);
        polydipsiaThumb.setOnClickListener(this);
        return view;
    }

    private View initializeBulimia(View view, HealthDate hd) {
        bulimiaTrack = (ImageView) view.findViewById(R.id.ivBulimiaTrack);
        bulimiaThumb = (ImageView) view.findViewById(R.id.ivBulimiaThumb);
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.BULIMIA);
        if (hd != null && hd.getValue().equals("true")) {
            bulimiaTrack.setImageResource(R.drawable.switch_track);
            bulimiaThumb.setImageResource(R.drawable.switch_thumb);
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) bulimiaThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            bulimia = !bulimia;
        }
        bulimiaToggleOn = new TranslateAnimation(0, toX, 0, 0);
        bulimiaToggleOn.setInterpolator(new LinearInterpolator());
        bulimiaToggleOn.setFillAfter(true);
        bulimiaToggleOn.setDuration(200);
        bulimiaToggleOff = new TranslateAnimation(0, to_X, 0, 0);
        bulimiaToggleOff.setInterpolator(new LinearInterpolator());
        bulimiaToggleOff.setFillAfter(true);
        bulimiaToggleOff.setDuration(200);
        bulimiaToggleOn.setAnimationListener(this);
        bulimiaToggleOff.setAnimationListener(this);
        bulimiaTrack.setOnClickListener(this);
        bulimiaThumb.setOnClickListener(this);
        return view;
    }

    private View initializeNocturia(View view, HealthDate hd) {
        txtUrination = (TextView) view.findViewById(R.id.txt_urination);
        nocturiaTextInput = (EditText) view.findViewById(R.id.et_urination);
        nocturiaTrack = (ImageView) view.findViewById(R.id.ivNocturiaTrack);
        nocturiaThumb = (ImageView) view.findViewById(R.id.ivNocturiaThumb);
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.NOCTURIA);
        if (hd != null && !hd.getValue().equals("")) {
            nocturiaTrack.setImageResource(R.drawable.switch_track);
            nocturiaThumb.setImageResource(R.drawable.switch_thumb);
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) nocturiaThumb.getLayoutParams();
            layout.gravity = Gravity.END;
            nocturia = !nocturia;
            nocturiaTextInput.setText(hd.getValue());
        }
        nocturiaToggleOn = new TranslateAnimation(0, toX, 0, 0);
        nocturiaToggleOn.setInterpolator(new LinearInterpolator());
        nocturiaToggleOn.setFillAfter(true);
        nocturiaToggleOn.setDuration(200);
        nocturiaToggleOff = new TranslateAnimation(0, to_X, 0, 0);
        nocturiaToggleOff.setInterpolator(new LinearInterpolator());
        nocturiaToggleOff.setFillAfter(true);
        nocturiaToggleOff.setDuration(200);
        nocturiaToggleOn.setAnimationListener(this);
        nocturiaToggleOff.setAnimationListener(this);
        nocturiaTrack.setOnClickListener(this);
        nocturiaThumb.setOnClickListener(this);
        txtUrination.setOnClickListener(this);
        minusUrination = (TextView) view.findViewById(R.id.down_urination);
        plusUrination = (TextView) view.findViewById(R.id.up_urination);
        minusUrination.setOnLongClickListener(this);
        plusUrination.setOnLongClickListener(this);
        minusUrination.setOnTouchListener(this);
        plusUrination.setOnTouchListener(this);
        nocturiaTextInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                handlerForToggle4();
                if (Integer.parseInt(nocturiaTextInput.getText().toString()) != 0) {
                    if (!nocturia) {
                        nocturiaThumb.startAnimation(nocturiaToggleOn);
                        nocturia = true;
                    }
                } else {
                    if (nocturia) {
                        nocturiaThumb.startAnimation(nocturiaToggleOff);
                        nocturia = false;
                    }
                }
                resetUrination();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return view;
    }

    private View initializeProfuseSweating(View view, HealthDate hd) {
        profuseSweatingTrack = (ImageView) view.findViewById(R.id.ivProfuseSweatingTrack);
        profuseSweatingThumb = (ImageView) view.findViewById(R.id.ivProfuseSweatingThumb);
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.PROFUSE_SWEATING);
        if (hd != null && hd.getValue().equals("true")) {
            profuseSweatingTrack.setImageResource(R.drawable.switch_track);
            profuseSweatingThumb.setImageResource(R.drawable.switch_thumb);
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) profuseSweatingThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            profuseSweating = !profuseSweating;
        }
        profuseSweatingToggleOn = new TranslateAnimation(0, toX, 0, 0);
        profuseSweatingToggleOn.setInterpolator(new LinearInterpolator());
        profuseSweatingToggleOn.setFillAfter(true);
        profuseSweatingToggleOn.setDuration(200);
        profuseSweatingToggleOff = new TranslateAnimation(0, to_X, 0, 0);
        profuseSweatingToggleOff.setInterpolator(new LinearInterpolator());
        profuseSweatingToggleOff.setFillAfter(true);
        profuseSweatingToggleOff.setDuration(200);
        profuseSweatingToggleOn.setAnimationListener(this);
        profuseSweatingToggleOff.setAnimationListener(this);
        profuseSweatingTrack.setOnClickListener(this);
        profuseSweatingThumb.setOnClickListener(this);
        return view;
    }

    private View initializeThirsty(View view, HealthDate hd) {
        thirstyTrack = (ImageView) view.findViewById(R.id.ivTthirstyTrack);
        thirstyThumb = (ImageView) view.findViewById(R.id.ivThirstyThumb);
        hd = HEALTH_DATA_SERVICE.findHealthDatesByType(HealthDateType.THIRSTY);
        if (hd != null && hd.getValue().equals("true")) {
            thirstyTrack.setImageResource(R.drawable.switch_track);
            thirstyThumb.setImageResource(R.drawable.switch_thumb);
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) thirstyThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            thirsty = !thirsty;
        }
        thirstyToggleOn = new TranslateAnimation(0, toX, 0, 0);
        thirstyToggleOn.setInterpolator(new LinearInterpolator());
        thirstyToggleOn.setFillAfter(true);
        thirstyToggleOn.setDuration(200);
        thirstyToggleOff = new TranslateAnimation(0, to_X, 0, 0);
        thirstyToggleOff.setInterpolator(new LinearInterpolator());
        thirstyToggleOff.setFillAfter(true);
        thirstyToggleOff.setDuration(200);
        thirstyToggleOn.setAnimationListener(this);
        thirstyToggleOff.setAnimationListener(this);
        thirstyTrack.setOnClickListener(this);
        thirstyThumb.setOnClickListener(this);
        return view;
    }

    private View initializeObesty(View view, HealthDate hd) {
        obestyTrack = (ImageView) view.findViewById(R.id.ivObestyTrack);
        obestyThumb = (ImageView) view.findViewById(R.id.ivObestyThumb);
        if (hd != null && hd.getValue().equals("true")) {
            obestyTrack.setImageResource(R.drawable.switch_track);
            obestyThumb.setImageResource(R.drawable.switch_thumb);
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) obestyThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            obesty = !obesty;
        }
        toX = obestyTrack.getLayoutParams().width - obestyThumb.getLayoutParams().width;
        to_X = obestyThumb.getLayoutParams().width - obestyTrack.getLayoutParams().width;
        obestyToggleOn = new TranslateAnimation(0, toX, 0, 0);
        obestyToggleOn.setInterpolator(new LinearInterpolator());
        obestyToggleOn.setFillAfter(true);
        obestyToggleOn.setDuration(200);
        obestyToggleOff = new TranslateAnimation(0, to_X, 0, 0);
        obestyToggleOff.setInterpolator(new LinearInterpolator());
        obestyToggleOff.setFillAfter(true);
        obestyToggleOff.setDuration(200);
        obestyToggleOn.setAnimationListener(this);
        obestyToggleOff.setAnimationListener(this);
        obestyTrack.setOnClickListener(this);
        obestyThumb.setOnClickListener(this);
        return view;
    }

    @Override
    public void onAnimationStart(Animation animation) {
        if (animation == obestyToggleOn) {
            obestyThumb.setClickable(false);
            obestyTrack.setClickable(false);
            obestyThumb.setImageResource(R.drawable.switch_thumb);
            obestyTrack.setImageResource(R.drawable.switch_track);
        } else if (animation == obestyToggleOff) {
            obestyThumb.setClickable(false);
            obestyTrack.setClickable(false);
            obestyThumb.setImageResource(R.drawable.switch_thumb_off);
            obestyTrack.setImageResource(R.drawable.switch_track_off);
        } else if (animation == thirstyToggleOn) {
            thirstyThumb.setClickable(false);
            thirstyTrack.setClickable(false);
            thirstyThumb.setImageResource(R.drawable.switch_thumb);
            thirstyTrack.setImageResource(R.drawable.switch_track);
        } else if (animation == thirstyToggleOff) {
            thirstyThumb.setClickable(false);
            thirstyTrack.setClickable(false);
            thirstyThumb.setImageResource(R.drawable.switch_thumb_off);
            thirstyTrack.setImageResource(R.drawable.switch_track_off);
        } else if (animation == profuseSweatingToggleOn) {
            profuseSweatingThumb.setClickable(false);
            profuseSweatingTrack.setClickable(false);
            profuseSweatingThumb.setImageResource(R.drawable.switch_thumb);
            profuseSweatingTrack.setImageResource(R.drawable.switch_track);
        } else if (animation == profuseSweatingToggleOff) {
            profuseSweatingThumb.setClickable(false);
            profuseSweatingTrack.setClickable(false);
            profuseSweatingThumb.setImageResource(R.drawable.switch_thumb_off);
            profuseSweatingTrack.setImageResource(R.drawable.switch_track_off);
        } else if (animation == nocturiaToggleOn) {
            nocturiaThumb.setClickable(false);
            nocturiaTrack.setClickable(false);
            nocturiaThumb.setImageResource(R.drawable.switch_thumb);
            nocturiaTrack.setImageResource(R.drawable.switch_track);
        } else if (animation == nocturiaToggleOff) {
            nocturiaThumb.setClickable(false);
            nocturiaTrack.setClickable(false);
            nocturiaThumb.setImageResource(R.drawable.switch_thumb_off);
            nocturiaTrack.setImageResource(R.drawable.switch_track_off);
        } else if (animation == polyuriaToggleOn) {
            polyuriaThumb.setClickable(false);
            polyuriaTrack.setClickable(false);
            polyuriaThumb.setImageResource(R.drawable.switch_thumb);
            polyuriaTrack.setImageResource(R.drawable.switch_track);
        } else if (animation == polyuriaToggleOff) {
            polyuriaThumb.setClickable(false);
            polyuriaTrack.setClickable(false);
            polyuriaThumb.setImageResource(R.drawable.switch_thumb_off);
            polyuriaTrack.setImageResource(R.drawable.switch_track_off);
        } else if (animation == polydipsiaToggleOn) {
            polydipsiaThumb.setClickable(false);
            polydipsiaTrack.setClickable(false);
            polydipsiaThumb.setImageResource(R.drawable.switch_thumb);
            polydipsiaTrack.setImageResource(R.drawable.switch_track);
        } else if (animation == polydipsiaToggleOff) {
            polydipsiaThumb.setClickable(false);
            polydipsiaTrack.setClickable(false);
            polydipsiaThumb.setImageResource(R.drawable.switch_thumb_off);
            polydipsiaTrack.setImageResource(R.drawable.switch_track_off);
        } else if (animation == bulimiaToggleOn) {
            bulimiaThumb.setClickable(false);
            bulimiaTrack.setClickable(false);
            bulimiaThumb.setImageResource(R.drawable.switch_thumb);
            bulimiaTrack.setImageResource(R.drawable.switch_track);
        } else if (animation == bulimiaToggleOff) {
            bulimiaThumb.setClickable(false);
            bulimiaTrack.setClickable(false);
            bulimiaThumb.setImageResource(R.drawable.switch_thumb_off);
            bulimiaTrack.setImageResource(R.drawable.switch_track_off);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == obestyToggleOn) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) obestyThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            obestyThumb.setLayoutParams(layout);
            obestyThumb.clearAnimation();
            obestyThumb.setClickable(true);
            obestyTrack.setClickable(true);
        } else if (animation == obestyToggleOff) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) obestyThumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            obestyThumb.setLayoutParams(layout);
            obestyThumb.clearAnimation();
            obestyThumb.setClickable(true);
            obestyTrack.setClickable(true);
        } else if (animation == thirstyToggleOn) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) thirstyThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            thirstyThumb.setLayoutParams(layout);
            thirstyThumb.clearAnimation();
            thirstyThumb.setClickable(true);
            thirstyTrack.setClickable(true);
        } else if (animation == thirstyToggleOff) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) thirstyThumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            thirstyThumb.setLayoutParams(layout);
            thirstyThumb.clearAnimation();
            thirstyThumb.setClickable(true);
            thirstyTrack.setClickable(true);
        } else if (animation == profuseSweatingToggleOn) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) profuseSweatingThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            profuseSweatingThumb.setLayoutParams(layout);
            profuseSweatingThumb.clearAnimation();
            profuseSweatingThumb.setClickable(true);
            profuseSweatingTrack.setClickable(true);
        } else if (animation == profuseSweatingToggleOff) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) profuseSweatingThumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            profuseSweatingThumb.setLayoutParams(layout);
            profuseSweatingThumb.clearAnimation();
            profuseSweatingThumb.setClickable(true);
            profuseSweatingTrack.setClickable(true);
        } else if (animation == nocturiaToggleOn) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) nocturiaThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            nocturiaThumb.setLayoutParams(layout);
            nocturiaThumb.clearAnimation();
            nocturiaThumb.setClickable(true);
            nocturiaTrack.setClickable(true);
        } else if (animation == nocturiaToggleOff) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) nocturiaThumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            nocturiaThumb.setLayoutParams(layout);
            nocturiaThumb.clearAnimation();
            nocturiaThumb.setClickable(true);
            nocturiaTrack.setClickable(true);
        } else if (animation == polyuriaToggleOn) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) polyuriaThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            polyuriaThumb.setLayoutParams(layout);
            polyuriaThumb.clearAnimation();
            polyuriaThumb.setClickable(true);
            polyuriaTrack.setClickable(true);
        } else if (animation == polyuriaToggleOff) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) polyuriaThumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            polyuriaThumb.setLayoutParams(layout);
            polyuriaThumb.clearAnimation();
            polyuriaThumb.setClickable(true);
            polyuriaTrack.setClickable(true);
        } else if (animation == polydipsiaToggleOn) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) polydipsiaThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            polydipsiaThumb.setLayoutParams(layout);
            polydipsiaThumb.clearAnimation();
            polydipsiaThumb.setClickable(true);
            polydipsiaTrack.setClickable(true);
        } else if (animation == polydipsiaToggleOff) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) polydipsiaThumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            polydipsiaThumb.setLayoutParams(layout);
            polydipsiaThumb.clearAnimation();
            polydipsiaThumb.setClickable(true);
            polydipsiaTrack.setClickable(true);
        } else if (animation == bulimiaToggleOn) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) bulimiaThumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            bulimiaThumb.setLayoutParams(layout);
            bulimiaThumb.clearAnimation();
            bulimiaThumb.setClickable(true);
            bulimiaTrack.setClickable(true);
        } else if (animation == bulimiaToggleOff) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) bulimiaThumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            bulimiaThumb.setLayoutParams(layout);
            bulimiaThumb.clearAnimation();
            bulimiaThumb.setClickable(true);
            bulimiaTrack.setClickable(true);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onClick(View v) {
        HealthDate healthDate = new HealthDate();
        switch (v.getId()) {
            case R.id.ivObestyTrack:
            case R.id.ivObestyThumb:
                if (!obesty) {
                    obestyThumb.startAnimation(obestyToggleOn);
                } else
                    obestyThumb.startAnimation(obestyToggleOff);
                obesty = !obesty;
                healthDate.setValue(String.valueOf(obesty));
                healthDate.setHealthDateType(HealthDateType.OBESTY);
                break;
            case R.id.ivTthirstyTrack:
            case R.id.ivThirstyThumb:
                if (!thirsty)
                    thirstyThumb.startAnimation(thirstyToggleOn);
                else
                    thirstyThumb.startAnimation(thirstyToggleOff);
                thirsty = !thirsty;
                healthDate.setValue(String.valueOf(thirsty));
                healthDate.setHealthDateType(HealthDateType.THIRSTY);
                break;
            case R.id.ivProfuseSweatingTrack:
            case R.id.ivProfuseSweatingThumb:
                if (!profuseSweating)
                    profuseSweatingThumb.startAnimation(profuseSweatingToggleOn);
                else
                    profuseSweatingThumb.startAnimation(profuseSweatingToggleOff);
                profuseSweating = !profuseSweating;
                healthDate.setValue(String.valueOf(profuseSweating));
                healthDate.setHealthDateType(HealthDateType.PROFUSE_SWEATING);
                break;
            case R.id.ivNocturiaTrack:
            case R.id.ivNocturiaThumb:
                if (!nocturia)
                    handlerForToggle4();
                else {
                    nocturiaThumb.startAnimation(nocturiaToggleOff);
                    nocturiaTextInput.setText("0");
                }

                return;
            case R.id.txt_urination:
                handlerForToggle4();
                return;
            case R.id.ivPolyuriaTrack:
            case R.id.ivPolyuriaThumb:
                if (!polyuria)
                    polyuriaThumb.startAnimation(polyuriaToggleOn);
                else
                    polyuriaThumb.startAnimation(polyuriaToggleOff);
                polyuria = !polyuria;
                healthDate.setValue(String.valueOf(polyuria));
                healthDate.setHealthDateType(HealthDateType.POLYURIA);
                break;
            case R.id.ivPolydipsiaTrack:
            case R.id.ivPolydipsiaThumb:
                if (!polydipsia)
                    polydipsiaThumb.startAnimation(polydipsiaToggleOn);
                else
                    polydipsiaThumb.startAnimation(polydipsiaToggleOff);
                polydipsia = !polydipsia;
                healthDate.setValue(String.valueOf(polydipsia));
                healthDate.setHealthDateType(HealthDateType.POLYDIPSIA);
                break;
            case R.id.ivBulimiaTrack:
            case R.id.ivBulimiaThumb:
                if (!bulimia)
                    bulimiaThumb.startAnimation(bulimiaToggleOn);
                else
                    bulimiaThumb.startAnimation(bulimiaToggleOff);
                bulimia = !bulimia;
                healthDate.setValue(String.valueOf(bulimia));
                healthDate.setHealthDateType(HealthDateType.BULIMIA);
                break;
        }
        HEALTH_DATA_SERVICE.createHealthDate(healthDate);
    }

    private void handlerForToggle4() {
        if (urination.getVisibility() == View.GONE) {
            expand(urination);
        } else if (urination.getVisibility() == View.VISIBLE)
            collapse(urination);
    }

    private List<HealthDate> getLastRecord(List<HealthDate> healthDate, HealthDateType type) {
        final List<HealthDate> healthDateObesty = new ArrayList<>();
        for (HealthDate h : healthDate) {
            if (h.getHealthDateType() == type)
                healthDateObesty.add(h);
        }
        return healthDateObesty;
    }

    @Override
    public boolean onLongClick(View v) {
        longTouched = true;
//        resetUrination();
        return true;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (longTouched || firstTouched) {
            switch (v.getId()) {
                case R.id.up_urination:
                    if (Integer.parseInt(nocturiaTextInput.getText().toString()) <= 8) {
                        String s = Integer.parseInt(nocturiaTextInput.getText().toString()) + 1 + "";
                        nocturiaTextInput.setText(s);
                        nocturiaTextInput.setSelection(nocturiaTextInput.length());
                    }
                    break;
                case R.id.down_urination:
                    if (Integer.parseInt(nocturiaTextInput.getText().toString()) != 0) {
                        String s = Integer.parseInt(nocturiaTextInput.getText().toString()) - 1 + "";
                        nocturiaTextInput.setText(s);
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

    private void resetUrination() {
        HealthDate healthDate = new HealthDate();
//        HEALTH_DATA_SERVICE.createHealthDate(healthDate);
        healthDate.setHealthDateType(HealthDateType.NOCTURIA);
        healthDate.setValue(nocturiaTextInput.getText().toString());
        healthDate.setValue(String.valueOf(nocturia));
        healthDate.save();
    }
}