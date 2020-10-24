package com.example.project.controllers;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.project.HomeActivity;
import com.example.project.R;
import com.example.project.ShowNotifContentActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

import io.crossbar.autobahn.websocket.WebSocketConnection;
import io.crossbar.autobahn.websocket.WebSocketConnectionHandler;
import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.types.ConnectionResponse;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class SocketController {
    WebSocketConnection connection;
    public Activity activity;
    private int i=0;
    private static final String CHANNEL_ID = "1";

    public SocketController(Activity activity) {
            this.activity = activity;
        }

    public void openConnection() {
        connection = new WebSocketConnection();
        SharedPreferences sharedPref =activity.getSharedPreferences("user_prefs",Context.MODE_PRIVATE);
        final int  userId = sharedPref.getInt("user_id", -1);
        String token = sharedPref.getString("token", "");
        final String discriminator = sharedPref.getString("discriminator", "");


        try {
            connection.connect("ws://192.168.43.8:8090",new WebSocketConnectionHandler(){
                @Override
                public void onConnect(ConnectionResponse response) {
                    Log.i("WebSocket","Connected to server");
                }

                @Override
                public void onOpen() {
                    JSONObject register = new JSONObject();
                    try {
                        register.put("command","register");
                        register.put("type",discriminator);
                        register.put("userId",userId);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                            connection.sendMessage(register.toString()
);
                }

                @Override
                public void onClose(int code, String reason) {
                    Log.i("WebSocket","Connection closed");
                }



                @Override
                public void onMessage(String payload) {
                    //Log.i("WebSocket","Received message: " + payload);
                    try {
                        JSONObject json = new JSONObject(payload);
                        switch(json.get("command").toString()) {
                            case "qualite":
                                createNotificationChannel(activity);
                                showNotification("rak nkhtlha mha l lqualiti",activity);
                                break;
                                case "stock":
                                createNotificationChannel(activity);
                                showNotification("rak nkhtlha mha mn 3nd stoc",activity);
                                break;
                            default:

                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (WebSocketException e) {
            e.printStackTrace();
        }
    }

    public void createNotificationChannel(Context ctx) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel name";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription("Notification channel description");
            NotificationManager notificationManager = ctx.getSystemService(NotificationManager.class);
            Objects.requireNonNull(notificationManager).createNotificationChannel(channel);
        }

    }


    public void   showNotification (String msg, Activity ctx){
        // Create an explicit intent for an Activity in your app
        Intent intent = new Intent(ctx, ShowNotifContentActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(ctx, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(ctx, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_logo_round)
                .setContentTitle("Das Schön")
                .setContentText(msg)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        builder.setSound(alarmSound);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(ctx);

        notificationManager.notify(i++, builder.build());
    }





}



