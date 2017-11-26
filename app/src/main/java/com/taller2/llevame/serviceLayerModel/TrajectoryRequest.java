package com.taller2.llevame.serviceLayerModel;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.BaseFragmentActivity;
import com.taller2.llevame.CookieHolder;
import com.taller2.llevame.Models.Step;
import com.taller2.llevame.Models.Trajectory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.facebook.FacebookSdk.getApplicationContext;

/**
 * Created by amarkosich on 11/25/17.
 */

public class TrajectoryRequest extends HTTPRequest {

    private static final String TAG = "TrajectoryRequest";


    public TrajectoryRequest(){
        this.endponintUrl = "/api/v1/trajectories";
        this.configureUrl();

    }

    /**
     * the trajectory request
     * @param delegate the delegate that will be notified when the async request has success or error
     * @param trajectory the trajectory
     */
    public void getWay(final BaseAtivity delegate, Trajectory trajectory){


        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        Gson gson = new Gson();
        String json = gson.toJson(trajectory);

        try {
            JSONObject jsonObject = new JSONObject(json);
            Log.v(TAG,jsonObject.toString());


            // Request a string response from the provided URL.
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, this.url, jsonObject,
                    new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            Log.v(TAG,"Response is: "+ response);

                            try {

                                JSONObject routesJson = response.getJSONArray("routes").getJSONObject(0);
                                JSONObject legsJson = routesJson.getJSONArray("legs").getJSONObject(0);
                                JSONArray jsonStepsArray = legsJson.getJSONArray("steps");
                                Log.v(TAG,jsonStepsArray.toString());
                                ArrayList<Step> stepsArray = new ArrayList<Step>();

                                for(int i = 0; i < jsonStepsArray.length(); i++){
                                    JSONObject stepJson = jsonStepsArray.getJSONObject(i);
                                    Gson gson = new Gson();
                                    Step step = gson.fromJson(stepJson.toString(),Step.class);
                                    stepsArray.add(step);
                                }

                                delegate.onGetWaySuccess(stepsArray);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    delegate.onServiceDidFailed(error);
                    Log.v(TAG,"Error: " +error.getMessage());
                }
            }){
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Set-Cookie", CookieHolder.INSTANCE.getCookie());
                    headers.put("Cookie",CookieHolder.INSTANCE.getCookie());
                    return headers;
                }
            };


            queue.add(stringRequest);
        }catch (JSONException e){
            Log.e(TAG,"error when converting json to JSONObject");
        }


    }

}
