package com.example.project;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.synnapps.carouselview.ImageListener;

import com.jama.carouselview.CarouselView;
import com.jama.carouselview.CarouselViewListener;
import com.jama.carouselview.enums.IndicatorAnimationType;
import com.jama.carouselview.enums.OffsetType;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ShowNotifContentActivity extends AppCompatActivity {

    String[]   sampleImages ={"https://i.pinimg.com/originals/f6/ce/c8/f6cec89109524179fd97a6764687d975.jpg",
            "https://i.pinimg.com/originals/68/2e/92/682e9269582cf59529dfcd5000755b52.jpg",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcT4aEtofO8xxGwdUTrYz52FDxoo6h9t87uVCg&usqp=CAU"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notif_content);
        getSupportActionBar().setTitle("Reclamation sur un Article ");

        com.jama.carouselview.CarouselView carouselView = findViewById(R.id.carouselView);

        carouselView.setSize(sampleImages.length);
        carouselView.setResource(R.layout.center_carousel_item);

        carouselView.setCarouselViewListener(new CarouselViewListener() {
            @Override
            public void onBindView(View view, int position) {
                // Example here is setting up a full image carousel
                ImageView imageView = view.findViewById(R.id.imageView);
//                Picasso.get().load(sampleImages[0]).into(imageView);
            }

        });
        // After you finish setting up, show the CarouselView
        carouselView.show();
    }
}
