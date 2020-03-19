package com.example.project.controllers;

  import android.content.Context;
        import android.content.Intent;
  import android.widget.Toast;

  import com.android.volley.Request;
        import com.android.volley.RequestQueue;
        import com.android.volley.Response;
        import com.android.volley.Response.ErrorListener;
        import com.android.volley.VolleyError;
        import com.android.volley.toolbox.JsonObjectRequest;
        import com.android.volley.toolbox.Volley;
  import com.example.project.models.Valid;
  import com.google.gson.Gson;

        import org.json.JSONObject;

  import java.util.HashMap;
  import java.util.Map;


public class ValidatController {

    private final int port = 9090;
    private final String ip = "192.168.43.207";
    private String route = "/valid";
    private Context context = getContext();

    public ValidatController(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void ValidateArticle(Valid valid, final String _token){

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        String url = "http://" + ip + ":" + port + route;
        Gson gson =new Gson();
        String jsonObject = gson.toJson(valid,Valid.class);
        JSONObject jsonBody = gson.fromJson(jsonObject,JSONObject.class);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PATCH, url, jsonBody, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {

                if(response.length()>0){

                    Toast.makeText(context, "Response:  " , Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(context,"response empty"+response,Toast.LENGTH_SHORT).show();

                }
            }

        },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Response:  " + error.toString(),Toast.LENGTH_LONG).show();
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