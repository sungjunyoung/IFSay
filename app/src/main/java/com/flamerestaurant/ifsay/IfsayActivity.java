package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.os.Bundle;

public class IfsayActivity extends Activity {

    private String ifsay_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ifsay);
    }
}
