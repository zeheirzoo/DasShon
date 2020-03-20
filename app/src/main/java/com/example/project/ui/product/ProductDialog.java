package com.example.project.ui.product;

import androidx.annotation.NonNull;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.R;
import com.example.project.ReclamationActivity;
import com.example.project.controllers.ValidatController;
import com.example.project.models.Valid;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

public class ProductDialog extends Dialog {
    Result result;
Context activity;
TextView article_name,cliet_name,num_consiption,order;
    Button accept,reject;
    JsonObject jsonObject;

    String discriminator,token;
    int userId,num_con,orderArt;
    public ProductDialog(@NonNull Context context, Result result) {
        super(context);
        this.result=result;
        this.activity=context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
//        =======================================
        SharedPreferences sharedPref =getContext().getSharedPreferences("user_prefs",Context.MODE_PRIVATE);
        userId = sharedPref.getInt("user_id", -1);
        token = sharedPref.getString("token", "");
        discriminator = sharedPref.getString("discriminator", "");

//        =======================================

        accept= findViewById(R.id.accept);
        reject=findViewById(R.id.reject);
        cliet_name=findViewById(R.id.client_name);
        num_consiption=findViewById(R.id.num_conception);
        article_name=findViewById(R.id.article_name);
        order=findViewById(R.id.order_val);
        Gson gson=new Gson();
         jsonObject=gson.fromJson(this.result.getText(),JsonObject.class);

        try {
            cliet_name.setText(jsonObject.get("nomClient")+"" );
            num_consiption.setText(jsonObject.get("num_conception")+"" );
            article_name.setText(jsonObject.get("nomArticle")+"" );
            order.setText(jsonObject.get("order")+"" );
        } catch (JsonIOException e) {
            e.printStackTrace();
        }

        reject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                Intent intent=new Intent(activity, ReclamationActivity.class);
                intent.putExtra("result",result.getText());
                activity.startActivity(intent);

            }
        });



        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                ValidatController validatController=new ValidatController(getContext(),getOwnerActivity());
                orderArt=Integer.parseInt(order.getText().toString());
                num_con=Integer.parseInt(num_consiption.getText().toString());
                validatController.ValidateArticle(new Valid(userId,orderArt,discriminator,num_con),token);
                progress();

            }
        });
    }



    public void progress(){
      final   ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("sendig"); // Setting Message
        progressDialog.setTitle("wait"); // Setting Title
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
