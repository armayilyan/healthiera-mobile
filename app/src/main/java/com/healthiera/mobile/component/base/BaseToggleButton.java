package com.healthiera.mobile.component.base;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.healthiera.mobile.R;

/**
 * Created by USER on 08.10.2016.
 */

public class BaseToggleButton extends FrameLayout implements View.OnClickListener, Animation.AnimationListener {
    public ImageView thumb, track;
    Animation toggle_on, toggle_off;
    public Boolean selected = false;

    public BaseToggleButton(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {

        int px1 = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, context.getResources().getDisplayMetrics());
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER_VERTICAL;
        setLayoutParams(layoutParams);

        track = new ImageView(context);
        track.setLayoutParams(new LayoutParams(px1 * 60, LinearLayout.LayoutParams.WRAP_CONTENT));
        track.setImageResource(R.drawable.switch_track_off);
        thumb = new ImageView(context);
        thumb.setLayoutParams(new LayoutParams(px1 * 30, LinearLayout.LayoutParams.WRAP_CONTENT));
        thumb.setImageResource(R.drawable.switch_thumb_off);
        track.setOnClickListener(this);
        thumb.setOnClickListener(this);

        int toX = track.getLayoutParams().width - thumb.getLayoutParams().width;
        int to_X = thumb.getLayoutParams().width - track.getLayoutParams().width;

        toggle_on = new TranslateAnimation(0, toX, 0, 0);
        toggle_on.setInterpolator(new LinearInterpolator());
        toggle_on.setFillAfter(true);
        toggle_on.setDuration(200);
        toggle_off = new TranslateAnimation(0, to_X, 0, 0);
        toggle_off.setInterpolator(new LinearInterpolator());
        toggle_off.setFillAfter(true);
        toggle_off.setDuration(200);
        toggle_on.setAnimationListener(this);
        toggle_off.setAnimationListener(this);
        addView(track);
        addView(thumb);
    }

    public BaseToggleButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void switchToggle() {
        if (!selected)
            thumb.startAnimation(toggle_on);
        else
            thumb.startAnimation(toggle_off);
        selected = !selected;

    }

    @Override
    public void onAnimationStart(Animation animation) {
        thumb.setClickable(false);
        track.setClickable(false);
        if (animation == toggle_on) {
            thumb.setImageResource(R.drawable.switch_thumb);
            track.setImageResource(R.drawable.switch_track);
        } else {
            thumb.setImageResource(R.drawable.switch_thumb_off);
            track.setImageResource(R.drawable.switch_track_off);
        }
    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animation == toggle_on) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) thumb.getLayoutParams();
            layout.gravity = Gravity.RIGHT;
            thumb.setLayoutParams(layout);
            thumb.clearAnimation();
            thumb.setClickable(true);
            track.setClickable(true);

        } else if (animation == toggle_off) {
            FrameLayout.LayoutParams layout = (FrameLayout.LayoutParams) thumb.getLayoutParams();
            layout.gravity = Gravity.LEFT;
            thumb.setLayoutParams(layout);
            thumb.clearAnimation();
            thumb.setClickable(true);
            track.setClickable(true);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onClick(View v) {
        switchToggle();
    }
}
