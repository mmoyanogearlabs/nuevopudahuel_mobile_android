package com.cleverox.nuevopudahuel.ui.fragments;

import android.view.View;

import com.bzutils.LogBZ;
import com.cleverox.nuevopudahuel.R;
import com.cleverox.nuevopudahuel.base.HomeFragment;
import com.cleverox.nuevopudahuel.ui.scanner.ScannerView;

/**
 * Created by moddity on 18/3/16.
 */
public class PromocionesFragment extends HomeFragment implements ScannerView.BarcodeDetectorInterface {

    ScannerView scannerView;

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
        LogBZ.d("Barcode: " + barcode);
    }
}
