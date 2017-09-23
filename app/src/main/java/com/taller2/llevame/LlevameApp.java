package com.taller2.llevame;
import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.appevents.AppEventsLogger;

import android.content.pm.*;
import android.content.pm.Signature;
import android.util.Base64;
import java.security.*;

/**
 * Created by MaríaAgustina on 2/9/2017.
 */

public class LlevameApp extends Application{

    @Override
    public void onCreate() {

        super.onCreate();
        AppEventsLogger.activateApp(this);
        showSplashActivity();
//        goToLoginIfShould();

        //logHashKey();
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

    private void goToLoginIfShould(){
        boolean isLoggedIn = (AccessToken.getCurrentAccessToken() != null);
        if(!isLoggedIn){
            goToMainActivity();
            return;
        }

        goToMainMenuActivity();
    }

    private void showSplashActivity(){
        Intent intent = new Intent(this,SplashActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToMainActivity(){
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goToMainMenuActivity(){
        Intent intent = new Intent(this,MainMenuActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
