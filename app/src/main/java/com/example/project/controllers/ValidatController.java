package com.example.project.controllers;

  import android.app.Activity;
  import android.content.Context;
        import android.content.Intent;
  import android.util.Log;
  import android.widget.Toast;

  import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.Response.ErrorListener;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
  import com.example.project.models.Valid;
  import com.example.project.network.WifiConnect;
  import com.google.gson.Gson;

  import org.json.JSONException;
  import org.json.JSONObject;

  import java.util.HashMap;
  import java.util.Map;


public class ValidatController {

    private  int port ;
    private  String ip ;
    private String route = "/api/valid";

    private Context context = getContext();

    public ValidatController(Context context, Activity activity) {
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

    public void ValidateArticle(Valid valid, final String _token){
//valid.setId_user(1);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://" + ip + ":" + port + route;
        Gson gson =new Gson();
//        String jsonObject = gson.toJson(valid,Valid.class);
//        Toast.makeText(context, "json body"+jsonObject, Toast.LENGTH_SHORT).show();
//
//        JSONObject jsonBody = gson.fromJson(jsonObject,JSONObject.class);
//        Toast.makeText(context, "json body"+jsonBody, Toast.LENGTH_SHORT).show();

// int user_id;
//    @SerializedName("order")
//    int order;
//    String discriminator;
//    int num_conception;
        JSONObject jsonBody  = new JSONObject();
        try {
            jsonBody.put("user_id",valid.getId_user());
            jsonBody.put("order",valid.getOrderArticle());
            jsonBody.put("discriminator",valid.getDiscriminator());
            jsonBody.put("num_conception",valid.getNum_conception());
        }catch (JSONException e){

        }


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(response.length()>0){

                    Toast.makeText(context, "Response:  " +response, Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(context,"response empty"+response,Toast.LENGTH_SHORT).show();

                }
            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("validError", "Response error:  " + error.getCause()+"Response error:  " + error.getLocalizedMessage());
            }
        } ){
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("Authorization", "Bearer "+_token);

                return params;
            }
        }
       ;
        requestQueue.add(jsonObjectRequest);
    }
}
