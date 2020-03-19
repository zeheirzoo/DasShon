package com.example.project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.project.controllers.ReserveController;
import com.example.project.models.Reserve;
import com.example.project.network.WifiConnect;
import com.example.project.ui.product.GridProductAdapter;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class ReclamationActivity extends AppCompatActivity {
    private final static int CAMERA_REQUEST = 2;
    private final static int READ_EXTARNAL_STORAGE = 99;
    String stringImage;
    private ImageView imageView;
    private static final int MY_CAMERA_PERMISSION_CODE = 100;
    private GridView gridView;
    ArrayList<Bitmap> bitmaps;
    GridProductAdapter adapter;
    ArrayList<String> stringImages = new ArrayList<String>();
    String discriminator,toker;
    int userId;
    String article_name,cliet_name;
      int num_consiption,order;
    Button accept,reject;
    JsonObject jsonObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclamation);
       getSupportActionBar().setTitle("Reclamation");

//        =============================
        WifiConnect wifiConnect=new WifiConnect(this);
        wifiConnect.connect();
//        ========================================
//============================================
        SharedPreferences sharedPref =getPreferences(Context.MODE_PRIVATE);
         userId = sharedPref.getInt("user_id", -1);
         toker = sharedPref.getString("token", "");
         discriminator = sharedPref.getString("discriminator", "");

//============================================
//        Gson gson=new Gson();
//        jsonObject=gson.fromJson(this.result.getText(), JsonObject.class);
//
//        try {
//            cliet_name=jsonObject.get("nomClient")+"" ;
//            num_consiption=IntijsonObject.get("num_conception");
//            article_name.setText(jsonObject.get("nomArticle")+"" );
//            order.setText(jsonObject.get("order")+"" );
//        } catch (JsonIOException e) {
//            e.printStackTrace();
//        }
//============================================

        gridView=findViewById(R.id.grid);
        bitmaps=new ArrayList<>();
        adapter =new GridProductAdapter(this,R.layout.grid_item,bitmaps);
        gridView.setAdapter(adapter);

        ImageButton buCam=(ImageButton) findViewById(R.id.camera);
        buCam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                        ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                    return;
                }

                onTakePictureClick();

            }
        });

        Button button=findViewById(R.id.send);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapter.getCount()==0){
                    Toast.makeText(ReclamationActivity.this, "Capturer la panne", Toast.LENGTH_SHORT).show();
                }else{
                    ReserveController reserveController=new ReserveController(getApplicationContext());
//                        public Reserve(int id_user, int orderArticle, String discriminator, int num_conception, List<String> photos, String _token) {

//                        reserveController.ReserveArticle(new Reserve());

                }


            }
        });




    }





    public void onTakePictureClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTARNAL_STORAGE);
            return;
        }
        TakePicture();
    }
    public void TakePicture() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, CAMERA_REQUEST);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");

            bitmaps.add(photo);
            Toast.makeText(this, bitmaps.size()+"", Toast.LENGTH_SHORT).show();
            adapter.add(photo);
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            stringImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
            stringImages.add(stringImage);


        }
    }


    public void progress(){
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(""); // Setting Message
        progressDialog.setTitle(""); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(10000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }




}
