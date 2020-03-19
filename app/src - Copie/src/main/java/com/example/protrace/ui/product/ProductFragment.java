package com.example.protrace.ui.product;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.budiyev.android.codescanner.CodeScanner;
import com.budiyev.android.codescanner.CodeScannerView;
import com.budiyev.android.codescanner.DecodeCallback;
import com.example.protrace.R;
import com.google.zxing.Result;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class ProductFragment extends Fragment {

    private CodeScanner mCodeScanner;
Vibrator vibrator;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        if (!CheckPermissions())RequestPermissions();
        final Activity activity = getActivity();
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);


        final CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        scannerView.setFrameColor(Color.BLUE);
        mCodeScanner.setAutoFocusEnabled(true);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductDialog productDialog=new ProductDialog(activity,result);
//                        productDialog.setCancelable(false);
                        scannerView.setFrameColor(Color.GREEN);
                        if (vibrator.hasVibrator()) {
                            vibrator.vibrate(200); // for 500 ms
                        }
                        productDialog.show();
                         mCodeScanner.startPreview();
                        scannerView.setFrameColor(Color.BLUE);

                    }

                });
            }


    });
        scannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCodeScanner.startPreview();
            }
        });




        return root;
    }



    @Override
    public void onResume() {
        super.onResume();
        mCodeScanner.startPreview();
    }

    @Override
    public void onPause() {
        mCodeScanner.releaseResources();
        super.onPause();
    }

    public boolean CheckPermissions() {
        int result1 = ActivityCompat.checkSelfPermission(getContext(), CAMERA);
        int result2 = ActivityCompat.checkSelfPermission(getContext(), INTERNET);
        int result3 = ActivityCompat.checkSelfPermission(getContext(), WRITE_EXTERNAL_STORAGE);
        return result2 == PackageManager.PERMISSION_GRANTED&&result1 == PackageManager.PERMISSION_GRANTED&&result3 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{CAMERA,INTERNET,WRITE_EXTERNAL_STORAGE}, 1);
    }

}