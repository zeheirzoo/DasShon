package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.network.WifiConnect;

import org.json.JSONException;
import org.json.JSONObject;

//import retrofit2.Response;
//
//import retrofit2.Call;
//import retrofit2.Callback;
//import retrofit2.Response;
//import retrofit2.Retrofit;
//import retrofit2.converter.gson.GsonConverterFactory;

import com.android.volley.Request;
import com.android.volley.VolleyError;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;

public class Login extends AppCompatActivity {
    private WifiManager wifiManager;
    TextInputLayout nomE,passwordE;
    String nom,password;
    Button login ;
    JSONObject body;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    private  String ip ;
    private int port ;
    private String route = "/user/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();

//        ===============================

        this.ip=new WifiConnect(getApplicationContext()).getIp();
        this.port=new WifiConnect(getApplicationContext()).getPort();
//=================================
        sharedPref =getApplicationContext().getSharedPreferences("user_prefs",Context.MODE_PRIVATE);
        editor = sharedPref.edit();

//=================================
        nomE=findViewById(R.id.til_email);
        passwordE=findViewById(R.id.til_password);
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//
                nom=nomE.getEditText().getText().toString();
                password=passwordE.getEditText().getText().toString();
                if (nom.isEmpty() && password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "champs  vides", Toast.LENGTH_LONG).show();
                }else{
                    loginUser(nom, password);
                }

            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

//        ===========================================
        WifiConnect wifiConnect=new WifiConnect(this);
        wifiConnect.connect();
//        ============================================
    }

    public void loginUser(String nom, String password){
        progress();
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());


        String url = "http://" + ip + ":" + port + route;;
//        String url="https://api.myjson.com/bins/11ka5w";

        body = new JSONObject();
        try {

            body.put("nom",nom);
            body.put("password",password);
        }catch (JSONException e){

        }

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, body, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                if (response!=null){
                    try {
                        JSONObject success= (JSONObject) response.get("success");
                        JSONObject user= (JSONObject) success.get("user");

                        editor.putString("token", success.getString("token"));
                        editor.putInt("user_id", user.getInt("id"));
                        editor.putString("discriminator", user.getString("discriminator"));
                        editor.commit();
                        progressDialog.dismiss();

                        Toast.makeText(Login.this, " welcome ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        progressDialog.dismiss();

                        e.printStackTrace();
                    }
                }else{
                    nomE.setError("Nom D\'utilisateur incorrect !");
                    passwordE.setError("Mot de Pass incorrect !");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Server Error !! Check your connection " , Toast.LENGTH_SHORT).show();



            }
        });
        queue.add(jsonObjectRequest);



//
//        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                if (!response.isEmpty()){
//                    Gson gson=new Gson();
//                    JsonObject resJson=gson.fromJson(response, JsonObject.class);
//
//                    try {
//                        JsonObject success= (JsonObject) resJson.get("success");
//                        JsonObject user= (JsonObject) success.get("user");
//
//                        editor.putString("token", success.get("token")+"");
//                        editor.putString("discriminator", user.get("discriminator")+"");
//                        editor.putInt("user_id",Integer.parseInt(user.get("id")+""));
//
//                        editor.commit();
//
//
//
//                        SharedPreferences sharedPref =getPreferences(Context.MODE_PRIVATE);
//                        String discriminator = sharedPref.getString("discriminator", "");
//
//                        Toast.makeText(Login.this, " welcome " + discriminator, Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } catch (JsonIOException e) {
//                        e.printStackTrace();
//                        Toast.makeText(Login.this, "exception  " +e.getMessage(), Toast.LENGTH_SHORT).show();
//                    }
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(Login.this, "error  " +error.toString(), Toast.LENGTH_SHORT).show();
//
//            }
//        });
//                queue.add(request);
//

    }
     ProgressDialog progressDialog;

    public void progress(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading ....... "); // Setting Message
        progressDialog.setTitle("Login "); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
//        progressDialog.set // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }


}