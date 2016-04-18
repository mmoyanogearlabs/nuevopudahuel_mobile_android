package com.massiva.nuevopudahuel.ui.custom;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Switch;

/**
 * Created by iaguila on 29/3/16.
 */
public class NPSwitch extends Switch {

    private OnCheckedChangeListener checkedChangeListener;

    public NPSwitch(Context context) {
        super(context);
        init();
    }

    public NPSwitch(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NPSwitch(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NPSwitch(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        configOnTouch();
    }

    private void configOnTouch() {
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        setOnCheckedChangeListener(checkedChangeListener);
                        break;
                    case MotionEvent.ACTION_UP:
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                setOnCheckedChangeListener(null);
                            }
                        }, 50);
                        break;
                }
                return false;
            }
        });
    }

    public void setCheckedChangeListener(OnCheckedChangeListener checkedChangeListener) {
        this.checkedChangeListener = checkedChangeListener;
    }
}
