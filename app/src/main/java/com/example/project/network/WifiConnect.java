package com.example.project.network;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.google.zxing.common.StringUtils;

import java.util.List;

import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.ACCESS_WIFI_STATE;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.CHANGE_WIFI_STATE;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class WifiConnect {
    Context context;
    Activity activity;

    private  int port = 8000;
    private  String ip = "192.168.43.8";

    private WifiManager wifiManager;
    public WifiConnect(Context context,Activity activity) {
        this.context=context;
        this.activity=activity;
    }

    public void connect(){

        if (!CheckPermissions())RequestPermissions();
        wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.setWifiEnabled(true);
        Toast.makeText(activity, "Wifi enabled", Toast.LENGTH_SHORT).show();
        String networkSSID = "Moghref";
        String networkPass = "biancoboy";

        WifiConfiguration conf = new WifiConfiguration();
        conf.SSID = "\"" + networkSSID + "\"";
        conf.preSharedKey = "\""+ networkPass +"\"";

        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        wifiManager.addNetwork(conf);

        if (getCurrentSsid(activity)==null&&!getCurrentSsid(activity).equals(networkSSID)){
            @SuppressLint("MissingPermission") List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for( WifiConfiguration i : list ) {
                if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();
                    break;
                }
            }
        }

    }


    public boolean CheckPermissions() {
        int result1 = ActivityCompat.checkSelfPermission(context, CAMERA);
        int result2 = ActivityCompat.checkSelfPermission(context, INTERNET);
        int result3 = ActivityCompat.checkSelfPermission(context, CAMERA);
        int result4 = ActivityCompat.checkSelfPermission(context,ACCESS_WIFI_STATE );
        int result5 = ActivityCompat.checkSelfPermission(context,CHANGE_WIFI_STATE );
        int result6 = ActivityCompat.checkSelfPermission(context,ACCESS_NETWORK_STATE );
        return result4 == PackageManager.PERMISSION_GRANTED&&result5 == PackageManager.PERMISSION_GRANTED&&result6 == PackageManager.PERMISSION_GRANTED&&result2 == PackageManager.PERMISSION_GRANTED&&result1 == PackageManager.PERMISSION_GRANTED&&result3 == PackageManager.PERMISSION_GRANTED;
    }
    private void RequestPermissions() {
        ActivityCompat.requestPermissions(activity, new String[]{CAMERA,INTERNET,WRITE_EXTERNAL_STORAGE,ACCESS_NETWORK_STATE,CHANGE_WIFI_STATE,ACCESS_WIFI_STATE }, 1);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }


    public static String getCurrentSsid(Context context) {
        String ssid = null;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo == null) {
            return null;
        }

        if (networkInfo.isConnected()) {
            final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            final WifiInfo connectionInfo = wifiManager.getConnectionInfo();
            if (connectionInfo != null ) {
                ssid = connectionInfo.getSSID();
            }
        }

        return ssid;
    }
}
