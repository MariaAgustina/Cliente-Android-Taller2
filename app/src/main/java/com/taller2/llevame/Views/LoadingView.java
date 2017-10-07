package com.taller2.llevame.Views;

import android.view.View;

import com.taller2.llevame.BaseAtivity;
import com.taller2.llevame.R;

/**
 * Created by MaríaAgustina on 7/10/2017.
 */

public class LoadingView {

    public void setLoadingViewVisible(BaseAtivity activity){
        activity.findViewById(R.id.loadingPanel).setVisibility(View.VISIBLE);
    }

    public void setLoadingViewInvisible(BaseAtivity activity){
        activity.findViewById(R.id.loadingPanel).setVisibility(View.INVISIBLE);
    }

}
