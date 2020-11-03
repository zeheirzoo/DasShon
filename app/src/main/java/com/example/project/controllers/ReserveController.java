package com.example.project.controllers;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.FileUtils;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.project.models.Photo;
import com.example.project.models.Reclamation;
import com.example.project.models.Reserve;
import com.example.project.network.WifiConnect;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.crossbar.autobahn.websocket.WebSocketConnection;
import io.crossbar.autobahn.websocket.WebSocketConnectionHandler;
import io.crossbar.autobahn.websocket.exceptions.WebSocketException;
import io.crossbar.autobahn.websocket.types.ConnectionResponse;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ReserveController {
    WebSocketConnection connection;
    JSONObject reclamationSocket;
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


//    public void ReserveArticle(final Reserve reserve, final String token){
//        connection=new WebSocketConnection();
//
//        if (connection.isConnected()){}
//
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//        String url = "http://" + ip + ":" + port + route;
////        Gson gson =new Gson();
////        String jsonObject = gson.toJson(reserve,Reserve.class);
////        JSONObject jsonBody = gson.fromJson(jsonObject,JSONObject.class);
////        Toast.makeText(context, "json body"+jsonBody, Toast.LENGTH_SHORT).show();
//
//        JSONObject jsonBody  = new JSONObject();
//        try {
//            jsonBody.put("user_id",reserve.getId_user());
//            jsonBody.put("order",reserve.getOrderArticle());
//            jsonBody.put("discriminator",reserve.getDiscriminator());
//            jsonBody.put("num_conception",reserve.getNum_conception());
//            jsonBody.putOpt("filenames",reserve.getPhotos().toString());
//        }catch (JSONException e){
//
//        }
////
//
//
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonBody, new Response.Listener<JSONObject>() {
//
//            @Override
//            public void onResponse(JSONObject response) {
//
//                if(response.length()>0){
//
//                    Toast.makeText(context, "Response:  " +response, Toast.LENGTH_SHORT).show();
//
//                         reclamationSocket = new JSONObject();
//                        try {
//                            String command="";
//                            if (reserve.getDiscriminator()=="quality")
//                                command="productionReclamation";
//                            else command="qualityReclamation";
//                            reclamationSocket.put("command",command);
//                            reclamationSocket.put("userId",response.getJSONObject("article").get("id"));
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                            if (connection.isConnected()){
//                             connection. sendMessage(reclamationSocket.toString());
//                            }else {
//                                try {
//                                    connection.connect("ws://192.168.1.11:8090",new WebSocketConnectionHandler(){
//                                        @Override
//                                        public void onConnect(ConnectionResponse response) {
//                                            Log.i("WebSocket","Connected to server");
//                                            connection. sendMessage(reclamationSocket.toString());
//                                        }
//
//                                        });
//
//                                } catch (WebSocketException e) {
//
//                                }
//                            }
//                }else{
//
//                    Toast.makeText(context,"response empty"+response,Toast.LENGTH_SHORT).show();
//
//                }
//            }
//        },new Response.ErrorListener(){
//
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(context, "Response:  " + error,Toast.LENGTH_LONG).show();
//            }
//        }){
//            @Override
//            public Map<String, String> getHeaders() {
//                Map<String, String> params = new HashMap<>();
//                params.put("Accept", "application/json");
//                params.put("Authorization", "Bearer "+token);
//
//                return params;
//            }
//        };
//        requestQueue.add(jsonObjectRequest);
//    }




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




    public void ReserveArticle(final Reserve reserve, final String token, List<Uri> uriList) throws IOException {
        String url = "http://" + ip + ":" + port ;
        JSONObject jsonBody  = new JSONObject();
        try {
            jsonBody.put("user_id",reserve.getId_user());
            jsonBody.put("order",reserve.getOrderArticle());
            jsonBody.put("discriminator",reserve.getDiscriminator());
            jsonBody.put("num_conception",reserve.getNum_conception());
            jsonBody.putOpt("filenames",reserve.getPhotos().toString());
        }catch (JSONException e){

        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

// create list of file parts (photo, video, ...)
        List<MultipartBody.Part> parts = new ArrayList<>();

// create upload service client
        RetrofitRequest service = retrofit.create(RetrofitRequest.class);

        if (uriList != null) {
            // create part for file (photo, video, ...)
            for (int i = 0; i < uriList.size(); i++) {
                parts.add(prepareFilePart("image"+i, uriList.get(i)));
            }
        }


// finally, execute the request
        Call<ResponseBody> call =  service.uploadImages("application/json","Bearer "+token,jsonBody,parts);

        call.enqueue(new Callback<ResponseBody>() {
           
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                Log.i("request ", response.toString());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.i("request ", t.toString());

            }
        });
    }


    @NonNull
    private MultipartBody.Part prepareFilePart(String partName, Uri fileUri) throws IOException {
        // use the FileUtils to get the actual file by uri
        File myFile = new File(fileUri.getPath());
        // create RequestBody instance from file
        String mimeType = Files.probeContentType(Paths.get(myFile.getPath()));
        RequestBody requestFile = RequestBody.create (MediaType.parse(mimeType), myFile);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, myFile.getName(), requestFile);
    }


}
