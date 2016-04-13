package com.cleverox.nuevopudahuel.ui.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cleverox.nuevopudahuel.R;

/**
 * Created by vmadalin on 5/4/16.
 */
public class CustomProgressDialog extends ProgressDialog {

    private ProgressBar progressBar;

    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        setCanceledOnTouchOutside(false);
//        setCancelable(false);
    }


    @Override
    public void show() {
        super.show();
        setContentView(R.layout.view_progress_dialog);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }

    public void dismissDialog() {
        dismiss();
    }
}
