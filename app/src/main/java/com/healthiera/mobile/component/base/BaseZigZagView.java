package com.healthiera.mobile.component.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;


/**
 * Created by USER on 08.04.2017.
 */

public class BaseZigZagView extends View {
    float dy = 3f;
    float dp;


    public BaseZigZagView(Context context) {
        super(context);
        init();
    }

    public BaseZigZagView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        dp = (float) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics());
        dy = 2 * dp;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int w = getWidth();
        int height = getHeight();
        int n = w / (height);
        if (n % 2 == 1) {
            n++;
        }
        float h = (float) w / n;
        int k = 1;

        Path path = new Path();
        path.moveTo(0, 0);
        for (int i = 1; i < n; i++) {
//            path.lineTo(k * h, h - dy);
            path.lineTo(k * h, h);
            path.lineTo((k + 1) * h, 0);
            k += 2;
        }
        path.close();
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        paint.setShadowLayer(dy, 0, 0, Color.parseColor("#40000000"));
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        canvas.drawPath(path, paint);
    }
}
