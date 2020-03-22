package com.example.project.ui.notifications;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.example.project.R;
import com.example.project.R;
import com.example.project.ReclamationActivity;
import com.example.project.models.Reserve;
import com.example.project.network.WifiConnect;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NotificationsFragment extends Fragment {
    private static final String CHANNEL_ID = "1";
    ListView nLV;
    ArrayList<Reserve> NotificationList;
    NotificationAdapter notificationAdapter;

    private  int port = 9090;
    private  String ip = "192.168.43.207";

    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        createNotificationChannel();


//        ============================================
        sharedPref =getContext().getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        SharedPreferences sharedPref =getActivity().getSharedPreferences("user_prefs",Context.MODE_PRIVATE);
        int  userId = sharedPref.getInt("user_id", -1);

        String route = "/notification/"+userId;
            String url = "http://" + ip + ":" + port + route;
//    String url="https://api.myjson.com/bins/11ka5w";

        try {
            run(url);
        } catch (IOException e) {
            e.printStackTrace();
        }

        nLV=root.findViewById(R.id.notification_list);
        NotificationList=new ArrayList<>();
        NotificationList.add(0,new Reserve(0));
        notificationAdapter=new NotificationAdapter(getContext(),R.layout.notificatin_item,NotificationList);
        nLV.setAdapter(notificationAdapter);
        return root;



    }

    void run(String url) throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            JSONObject json = new JSONObject(myResponse);
                            Toast.makeText(getContext(), "responce from socket"+myResponse, Toast.LENGTH_SHORT).show();
                            showNotification();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });
    }
        
//        ============================================




public void   showNotification (){
    // Create an explicit intent for an Activity in your app
    Intent intent = new Intent(getContext(), ReclamationActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    PendingIntent pendingIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);

    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
            .setSmallIcon(R.mipmap.ic_logo_round)
            .setContentTitle("My notification")
            .setContentText("Hello World!")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            // Set the intent that will fire when the user taps the notification
            .setContentIntent(pendingIntent)
            .setAutoCancel(true);
    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

// notificationId is a unique int for each notification that you must define
    notificationManager.notify(1, builder.build());
}




    private void createNotificationChannel() {
        // Créer le NotificationChannel, seulement pour API 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Notification channel description");
            // Enregister le canal sur le système : attention de ne plus rien modifier après
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }

    }
//    public void showNotification() {
//        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());
//
//        NotificationCompat.Builder notifBuilder = new NotificationCompat.Builder(getActivity(), CHANNEL_ID)
//                .setSmallIcon(R.drawable.ic_code_scanner_flash_on)
//                .setContentTitle(R.string.app_name+"")
//                .setContentText("Contenu")
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
//
//        // notificationId est un identificateur unique par notification qu'il vous faut définir
//        notificationManager.notify(1, notifBuilder.build());
//    }








}