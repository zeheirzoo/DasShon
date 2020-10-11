package com.example.project.ui.product;

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
import com.example.project.R;
import com.example.project.R;
import com.example.project.network.WifiConnect;
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
////        ===========================================
//        WifiConnect wifiConnect=new WifiConnect(getContext());
//        wifiConnect.connect();
//        ============================================
        final Activity activity = getActivity();
        vibrator = (Vibrator) activity.getSystemService(Context.VIBRATOR_SERVICE);


        final CodeScannerView scannerView = root.findViewById(R.id.scanner_view);
        mCodeScanner = new CodeScanner(activity, scannerView);
        mCodeScanner.setAutoFocusEnabled(true);

        mCodeScanner.setDecodeCallback(new DecodeCallback() {
            @Override
            public void onDecoded(@NonNull final Result result) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ProductDialog productDialog=new ProductDialog(activity,result,mCodeScanner);
//                        productDialog.setCancelable(false);
                        scannerView.setFrameColor(Color.GREEN);
                        if (vibrator.hasVibrator()) {
                            vibrator.vibrate(200); // for 500 ms
                        }
                        productDialog.show();

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

}