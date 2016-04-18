package com.massiva.nuevopudahuel.ui.scanner;

/**
 * Created by iaguila on 13/1/16.
 */
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.Tracker;
import com.google.android.gms.vision.barcode.Barcode;

/**
 * Factory for creating a tracker and associated graphic to be associated with a new barcode.  The
 * multi-processor uses this factory to create barcode trackers as needed -- one for each barcode.
 */
class BarcodeTrackerFactory implements MultiProcessor.Factory<Barcode> {
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;
    private ScannerView.BarcodeDetectorInterface detectorInterface;

    BarcodeTrackerFactory(GraphicOverlay<BarcodeGraphic> barcodeGraphicOverlay, ScannerView.BarcodeDetectorInterface detectorInterface) {
        mGraphicOverlay = barcodeGraphicOverlay;
        this.detectorInterface = detectorInterface;
    }

    @Override
    public Tracker<Barcode> create(Barcode barcode) {
        BarcodeGraphic graphic = new BarcodeGraphic(mGraphicOverlay);
        return new BarcodeGraphicTracker(mGraphicOverlay, graphic, detectorInterface);
    }

}
