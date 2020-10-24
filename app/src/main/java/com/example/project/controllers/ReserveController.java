package com.example.project.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.models.Reclamation;
import com.example.project.models.Reserve;
import com.example.project.network.WifiConnect;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.crossbar.autobahn.websocket.WebSocketConnection;


public class ReserveController {

    private  int port;
    private  String ip;

    private String route = "/api/reserve";
    private Context context = getContext();

    public ReserveController(Context context, Activity activity) {
        this.context = context;
        this.ip=new WifiConnect(context,activity).getIp();
        this.port=new WifiConnect(context,activity).getPort();

    }



    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
    WebSocketConnection connection;

    public void ReserveArticle(final Reserve reserve, final String token){
        connection=new WebSocketConnection();

        if (connection.isConnected()){}

        RequestQueue requestQueue = Volley.newRequestQueue(context);

        String url = "http://" + ip + ":" + port + route;
//        Gson gson =new Gson();
//        String jsonObject = gson.toJson(reserve,Reserve.class);
//        JSONObject jsonBody = gson.fromJson(jsonObject,JSONObject.class);
//        Toast.makeText(context, "json body"+jsonBody, Toast.LENGTH_SHORT).show();

        JSONObject jsonBody  = new JSONObject();
        try {
            jsonBody.put("user_id",reserve.getId_user());
            jsonBody.put("order",reserve.getOrderArticle());
            jsonBody.put("discriminator",reserve.getDiscriminator());
            jsonBody.put("num_conception",reserve.getNum_conception());
            jsonBody.putOpt("filenames",reserve.getPhotos().toString());
        }catch (JSONException e){

        }
//



        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(response.length()>0){

                    Toast.makeText(context, "Response:  " +response, Toast.LENGTH_SHORT).show();

                        JSONObject reclamationSocket = new JSONObject();
                        try {
                            String command="";
                            if (reserve.getDiscriminator()=="quality")
                                command="production";
                            else command="quality";
                            reclamationSocket.put("command",command);
                            reclamationSocket.put("userId",response.getJSONObject("article").get("id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        connection.sendMessage(reclamationSocket.toString());

                }else{

                    Toast.makeText(context,"response empty"+response,Toast.LENGTH_SHORT).show();

                }
            }
        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Response:  " + error,Toast.LENGTH_LONG).show();
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




    ProgressDialog progressDialog;
    public void progress(){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage(""); // Setting Message
        progressDialog.setTitle(""); // Setting Title
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();
            }
        }).start();
    }






}
