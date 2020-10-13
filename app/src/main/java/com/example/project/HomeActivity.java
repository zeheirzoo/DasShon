package com.example.project;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.project.controllers.SocketController;
import com.example.project.network.WifiConnect;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;


public class HomeActivity extends AppCompatActivity {
    private WebSocket webSocket;

    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
//        =============================
        WifiConnect wifiConnect=new WifiConnect(this,this);
        wifiConnect.connect();
//        ===============================
        //        =======================================

         sharedPref =this.getSharedPreferences("user_prefs",Context.MODE_PRIVATE);
        int  userId = sharedPref.getInt("user_id", -1);
        String token = sharedPref.getString("token", "");
        String discriminator = sharedPref.getString("discriminator", "");
//        Toast.makeText(this, ""+sharedPref.getAll(), Toast.LENGTH_SHORT).show();
//
        if (userId==-1 || discriminator.isEmpty() || token.isEmpty()){
            Intent intent = new Intent(getApplicationContext(), Login.class);
            startActivity(intent);
            finish();
        }
//        =======================================
        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder( R.id.navigation_dashboard, R.id.navigation_notifications).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
//

    }

    @Override
    protected void onStart() {
        super.onStart();
        instantiateWebSocket();
        }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    // handle button activities
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            AlertDialog.Builder builder= new AlertDialog.Builder(this);
            builder.setTitle("Deconnecter ");
            builder.setMessage("menk bsa7.......... ?");
            builder.setIcon(R.drawable.ic_warning_black_24dp);
            builder.setNegativeButton("exit", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Deconnecter ", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    logout();
                }
            });
            builder.create();
            builder.show();

            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    private void logout() {

        sharedPref.edit().clear().commit();
        startActivity(new Intent(HomeActivity.this,Login.class));
        finish();
    }
//


    private void instantiateWebSocket() {
        OkHttpClient client = new OkHttpClient();
        String ip=new WifiConnect(getBaseContext(),this).getIp();
       int port=new WifiConnect(getBaseContext(),this).getPort();
        String url = "ws://" + ip + ":" + port + "/api/reserve";
        //replace x.x.x.x with your machine's IP Address
        Request request = new Request.Builder().url(url).build();
        SocketController socketListener = new SocketController(this);
        webSocket = client.newWebSocket(request, socketListener);

    }


}
