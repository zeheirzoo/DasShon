package com.example.project.ui.notifications;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.HomeActivity;
import com.example.project.R;
import com.example.project.R;
import com.example.project.ReclamationActivity;
import com.example.project.ShowNotifContentActivity;
import com.example.project.controllers.SocketController;
import com.example.project.models.Reclamation;
import com.example.project.models.Reserve;
import com.example.project.network.WifiConnect;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;


public class NotificationsFragment extends Fragment {
    ListView nLV;
    Activity activity;
    ArrayList<Reserve> NotificationList;
    NotificationAdapter notificationAdapter;
    private WebSocket webSocket;

    private  int port ;
    private  String ip ;

    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        activity=getActivity();
        instantiateWebSocket();
//        ============================================
        sharedPref =getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        SharedPreferences sharedPref =getActivity().getSharedPreferences("user_prefs",Context.MODE_PRIVATE);
        int  userId = sharedPref.getInt("user_id", -1);




        nLV=root.findViewById(R.id.notification_list);
        NotificationList=new ArrayList<>();
        NotificationList.add(0,new Reserve(0));
        notificationAdapter=new NotificationAdapter(getContext(),R.layout.notificatin_item,NotificationList);
        nLV.setAdapter(notificationAdapter);
        nLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), ShowNotifContentActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }
//
//
//        ============================================



    private void instantiateWebSocket() {
        OkHttpClient client = new OkHttpClient();
        ip=new WifiConnect(getContext(),activity).getIp();
       port=new WifiConnect(getContext(),activity).getPort();
        String url = "ws://" + ip + ":" + port + "/api/reserve";
        //replace x.x.x.x with your machine's IP Address
        Request request = new Request.Builder().url(url).build();


        SocketController socketController = new SocketController((HomeActivity) activity);
        webSocket = client.newWebSocket(request, socketController);
        socketController. createNotificationChannel(activity);
        socketController.  showNotification("on message!",activity);

    }


    private void getAllReclamation(int fautif_id, final String token) {
        List<Reclamation> reclamationList=new ArrayList<>();
        RequestQueue requestQueue = Volley.newRequestQueue(activity);
        ip=new WifiConnect(getContext(),activity).getIp();
        port=new WifiConnect(getContext(),activity).getPort();
        String url = "http://" + ip + ":" + port + "/reclamation";

        JSONObject jsonBody  = new JSONObject();
        try {
            jsonBody.put("user_id",fautif_id);
        }catch (JSONException e){

        }
//        Toast.makeText(context, "json body"+jsonBody, Toast.LENGTH_SHORT).show();
//

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(com.android.volley.Request.Method.POST, url, jsonBody, new com.android.volley.Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(response.length()>0){

                    Toast.makeText(activity, "Response:  " +response, Toast.LENGTH_SHORT).show();


                }else{

                    Toast.makeText(activity,"response empty"+response,Toast.LENGTH_SHORT).show();

                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, "Response:  " + error,Toast.LENGTH_LONG).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+token);

                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }



}

