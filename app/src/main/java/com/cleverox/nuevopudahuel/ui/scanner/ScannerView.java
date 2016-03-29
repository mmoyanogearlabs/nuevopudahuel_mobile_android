package com.cleverox.nuevopudahuel.ui.scanner;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.hardware.Camera;
import android.os.Build;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.cleverox.nuevopudahuel.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.BarcodeDetector;


import java.io.IOException;


/**
 * Created by iaguila on 13/1/16.
 */
public class ScannerView extends RelativeLayout {

    private boolean started;
    public void setBarcodeDetectorInterface(BarcodeDetectorInterface barcodeDetectorInterface) {
        this.barcodeDetectorInterface = barcodeDetectorInterface;
    }

    public boolean isStarted() {
        return started;
    }

    public interface BarcodeDetectorInterface {
        void onBarcodeDetected(String barcode);
    }

    private BarcodeDetectorInterface barcodeDetectorInterface;

    private static final int RC_HANDLE_GMS = 9001;

    // constants used to pass extra data in the intent
    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String BarcodeObject = "Barcode";

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
    private ImageView scannerOverlay;
    private LinearLayout textLayout;

    // helper objects for detecting taps and pinches.
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    public ScannerView(Context context) {
        super(context);
        init();
    }

    public ScannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ScannerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ScannerView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        inflate(getContext(), R.layout.scanner_view, this);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = (GraphicOverlay) findViewById(R.id.graphicOverlay);
    }

    public void startCamera(final boolean autoFocus, final boolean useFlash) {
        started = true;
        final Context context = getContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        final BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, new BarcodeDetectorInterface() {
            @Override
            public void onBarcodeDetected(String barcode) {
                if (barcodeDetectorInterface != null)
                    barcodeDetectorInterface.onBarcodeDetected(barcode);
            }
        });
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());


        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        mPreview.post(new Runnable() {
            @Override
            public void run() {
                CameraSource.Builder builder = new CameraSource.Builder(context, barcodeDetector)
                        .setFacing(CameraSource.CAMERA_FACING_BACK)
                        .setRequestedPreviewSize(mPreview.getWidth(), mPreview.getHeight())
                        .setRequestedFps(15.0f);

                // make sure that auto focus is an available option
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
                    builder = builder.setFocusMode(
                            autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
                }

                mCameraSource = builder
                        .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                        .build();

                startCameraSource();

                /*int outWidth = mPreview.getRight() - mPreview.getLeft();
                int outHeight = mGraphicOverlay.getBottom() - mGraphicOverlay.getTop();
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(outWidth,
                        outHeight - textLayout.getHeight() + mPreview.getTop());
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
               // params.addRule(RelativeLayout.BELOW, R.id.scanner_text_layout);
                scannerOverlay.setLayoutParams(params);
                scannerOverlay.setVisibility(VISIBLE);*/
            }
        });
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        startCamera(true, false);
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog((Activity) getContext(), code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    public void stop() {
        started = false;
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        stop();
        super.onDetachedFromWindow();
    }
}
