package com.massiva.nuevopudahuel.ui.fragments;

import android.content.Intent;
import android.view.View;

import com.massiva.nuevopudahuel.R;
import com.massiva.nuevopudahuel.base.HomeFragment;
import com.massiva.nuevopudahuel.ui.activities.DetailWebViewActivity;
import com.massiva.nuevopudahuel.ui.scanner.ScannerView;

/**
 * Created by moddity on 18/3/16.
 */
public class PromocionesFragment extends HomeFragment implements ScannerView.BarcodeDetectorInterface {

    ScannerView scannerView;
    boolean barcodeScanned;

    public static PromocionesFragment newInstance() {
        PromocionesFragment fragment = new PromocionesFragment();
        return fragment;
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_promociones;
    }

    @Override
    protected void configView(View parentView) {
        scannerView = $(R.id.promociones_scanner);
        scannerView.setBarcodeDetectorInterface(this);
    }

    @Override
    public void onBarcodeDetected(String barcode) {
        if (barcode != null && !barcodeScanned) {
            if (!barcode.startsWith("http"))
                barcode = "http://" + barcode;
            barcodeScanned = true;
            Intent intent = new Intent(getBaseActivity(), DetailWebViewActivity.class);
            intent.putExtra(DetailWebViewActivity.EXTRA_URL, barcode);
            startActivityForResult(intent, 11);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (scannerView != null && !scannerView.isStarted()) {
            try {
                scannerView.startCamera(true, false);
            } catch (Exception e) {}
        }
    }

    @Override
    public void onStop() {
        if (scannerView != null)
            scannerView.stop();
        super.onStop();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 11) {
            barcodeScanned = false;
        }
    }
}
