package com.example.project;

import androidx.appcompat.app.AppCompatActivity;

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

public class Login extends AppCompatActivity {
    private WifiManager wifiManager;
    TextInputLayout nomE,passwordE;
    String nom,password;
    Button login ;
    JSONObject body;
    SharedPreferences.Editor editor;
    SharedPreferences sharedPref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
//=================================
        sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        editor = sharedPref.edit();

//=================================
        nomE=findViewById(R.id.til_email);
        passwordE=findViewById(R.id.til_password);
        nom=nomE.getEditText().getText().toString();
        password=passwordE.getEditText().getText().toString();
        login=findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (nom.isEmpty() && password.isEmpty()) {
//                    Toast.makeText(getApplicationContext(), "champs  vides", Toast.LENGTH_LONG).show();
//                }
                loginUser(nom, password);

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

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="http://192.168.43.207:8000/user/loginA";
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
                        Toast.makeText(Login.this, " welcome ", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else
                    Toast.makeText(Login.this, "responce  " +response.toString(), Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "error " +error.getCause(), Toast.LENGTH_SHORT).show();

            }
        });
        queue.add(jsonObjectRequest);

    }


}