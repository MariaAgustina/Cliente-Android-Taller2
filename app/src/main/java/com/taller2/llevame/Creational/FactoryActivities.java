package com.taller2.llevame.Creational;

import android.content.Intent;

import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.ModifyProfileActivity;

/**
 * Created by MaríaAgustina on 5/10/2017.
 */

public class FactoryActivities {

    public void goToProfileActivity(BaseAtivity activity){
        Intent intent = new Intent(activity,ModifyProfileActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }
}
