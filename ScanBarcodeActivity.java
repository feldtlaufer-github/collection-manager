package reiokyu.muhich.mymangacollection;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;

public class ScanBarcodeActivity extends AppCompatActivity {
    SurfaceView svCameraPreview;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_barcode);

        svCameraPreview = findViewById(R.id.svCameraPreview);

        createBarcodeCameraSource();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        switch(requestCode){
            case MY_PERMISSIONS_REQUEST_CAMERA:
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //if the permission is granted after the activity was started
                    startActivity(new Intent(ScanBarcodeActivity.this, ScanBarcodeActivity.class));
                    finish();
                }else{
                    if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
                        //if you click deny, but not do not ask again
                        new AlertDialog
                                .Builder(this)
                                .setTitle("Camera Permission")
                                .setMessage("You need to grant camera permission to use barcode scanning feature." +
                                        " Retry and grant it.").show();
                    }else{
                        //if do not ask again was
                        new AlertDialog
                                .Builder(this)
                                .setTitle("Camera Permisison Denied.")
                                .setMessage("You denied camera permission. So the barcode scanner will be disabled." +
                                        " To enable it, go to your device's settings and grant camera permission for this app.").show();
                    }
                }
                break;
        }
    }
    private void createBarcodeCameraSource() {
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).build();
        final CameraSource cameraSource = new CameraSource.Builder(this, barcodeDetector)
                .setAutoFocusEnabled(true)
                .setRequestedPreviewSize(1600, 1024)
                .build();
        svCameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(ScanBarcodeActivity.this, Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ScanBarcodeActivity.this, new String[]{Manifest.permission.CAMERA},
                            MY_PERMISSIONS_REQUEST_CAMERA);
                }else{
                    //if the permission was granted prior to starting activity
                    try{
                        cameraSource.start(svCameraPreview.getHolder());
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>(){

            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if(barcodes.size() > 0){
                    Intent intent = new Intent();
                    intent.putExtra("barcode", barcodes.valueAt(0).displayValue);
                    setResult(CommonStatusCodes.SUCCESS, intent);
                    finish();

                }
            }
        });
    }
}
