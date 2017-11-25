package com.taller2.llevame;

import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.taller2.llevame.Models.AvailableDriver;
import com.taller2.llevame.Models.Step;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by amarkosich on 11/19/17.
 */

public class BaseFragmentActivity extends FragmentActivity{

    /**
     * to override
     */
    public void onAvailableDriverSuccess(List<AvailableDriver> drivers) {

    }

    public void onGetWaySuccess(ArrayList<Step> steps) {

    }

        /**
         * onServiceDidFailed shows Toast to inform the user the server has failed
         * @param error to log in console
         */
    public void onServiceDidFailed(VolleyError error){
        Log.e("error en la resupuesta", error.toString());
        Toast.makeText(getApplicationContext(),R.string.server_failed,Toast.LENGTH_SHORT).show();
    }
}
