package com.taller2.llevame;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.facebook.AccessToken;

public class SplashActivity extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private static final String TAG = "SplashActivity";

    @Override
    protected void onCreate(Bundle icicle) {
        Log.v(TAG,"calling splash");
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = getIntentToShow();
                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    private Intent getIntentToShow(){
        boolean isLoggedIn = (AccessToken.getCurrentAccessToken() != null);
        Intent intent = (isLoggedIn) ? new Intent(this,ProfileActivity.class) : new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

}

