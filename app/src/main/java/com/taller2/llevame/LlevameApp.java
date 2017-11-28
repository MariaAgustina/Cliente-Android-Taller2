package com.taller2.llevame;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;
import android.util.Log;

import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.iid.FirebaseInstanceId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by MaríaAgustina on 2/9/2017.
 */

public class LlevameApp extends Application{

    private static final String TAG = "LlevameApp";

    /**
     * the first activity presented, will show splash  after this
     */
    @Override
    public void onCreate() {

        super.onCreate();
        AppEventsLogger.activateApp(this);
        Log.v(TAG,"onCreate called");
        logHashKey();

        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        if(refreshedToken != null){
            Log.d(TAG, "Refreshed token: " + refreshedToken);
        }
    }

    private void logHashKey(){
        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.taller2.llevame", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }

    }

}
