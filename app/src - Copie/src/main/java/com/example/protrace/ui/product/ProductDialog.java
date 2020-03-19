package com.example.protrace.ui.product;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.protrace.R;
import com.google.zxing.Result;

public class ProductDialog extends Dialog {
    Result result;
Context activity;
TextView article_name;
    Button accept,reject;
    public ProductDialog(@NonNull Context context, Result result) {
        super(context);
        this.result=result;
        this.activity=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        accept= findViewById(R.id.accept);
        reject=findViewById(R.id.reject);
        article_name=findViewById(R.id.article_name);
        article_name.setText(this.result.getText()  );

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,ReclamationActivity.class);
                activity.startActivity(intent);
                dismiss();
            }
        });

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(activity,ReclamationActivity.class);
                activity.startActivity(intent);
                dismiss();
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progress();
                dismiss();
            }
        });
    }



    public void progress(){
      final   ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("sendig"); // Setting Message
        progressDialog.setTitle("wail"); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }
}
