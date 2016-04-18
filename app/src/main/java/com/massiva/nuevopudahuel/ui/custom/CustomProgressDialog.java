package com.massiva.nuevopudahuel.ui.custom;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.ProgressBar;

import com.massiva.nuevopudahuel.R;

/**
 * Created by vmadalin on 5/4/16.
 */
public class CustomProgressDialog extends ProgressDialog {

    private ProgressBar progressBar;

    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
        setContentView(R.layout.view_progress_dialog);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
    }
}
