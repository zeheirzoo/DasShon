package com.example.project;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import com.synnapps.carouselview.CarouselView;
import com.synnapps.carouselview.ImageListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowNotifContentActivity extends AppCompatActivity {
    CarouselView carouselView;
    ImageListener imageListener;
//    String[]   sampleImages ={"https://cdn.pixabay.com/photo/2015/04/23/22/00/tree-736885_960_720.jpg",
//            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_1.jpg",
//            "https://raw.githubusercontent.com/sayyam/carouselview/master/sample/src/main/res/drawable/image_2.jpg"};
    String[]   sampleImages ={"https://oc.ocstatic.com/images/favicon/android-chrome-512x512.png","https://oc.ocstatic.com/images/favicon/android-chrome-512x512.png"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notif_content);
        getSupportActionBar().setTitle("Reclamation sur un Article ");
        carouselView=findViewById(R.id.carouselView);
        imageListener = new ImageListener() {
            @Override
            public void setImageForPosition(int position, ImageView imageView) {
                Picasso.get().load("https://oc.ocstatic.com/images/favicon/android-chrome-512x512.png").into(imageView);
        }
        };
        carouselView.setPageCount(sampleImages.length);
        carouselView.setImageListener(imageListener);
    }








}
