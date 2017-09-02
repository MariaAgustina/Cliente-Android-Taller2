package com.taller2.llevame;
import android.app.Application;
import android.hardware.camera2.params.Face;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
/**
 * Created by MaríaAgustina on 2/9/2017.
 */

public class LlevameApp extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        AppEventsLogger.activateApp(this);
    }
}
