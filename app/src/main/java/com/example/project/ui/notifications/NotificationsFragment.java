package com.example.project.ui.notifications;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.project.R;
import com.example.project.R;
import com.example.project.models.Reserve;
import com.example.project.network.WifiConnect;

import java.util.ArrayList;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class NotificationsFragment extends Fragment {
    ListView nLV;
    ArrayList<Reserve> NotificationList;
    NotificationAdapter notificationAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);


////        =============================
//        WifiConnect wifiConnect=new WifiConnect(getContext());
//        wifiConnect.connect();
////        ========================================

        nLV=root.findViewById(R.id.notification_list);
        NotificationList=new ArrayList<>();
        NotificationList.add(0,new Reserve(0));
        notificationAdapter=new NotificationAdapter(getContext(),R.layout.notificatin_item,NotificationList);
        nLV.setAdapter(notificationAdapter);
        return root;
    }








}