package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

//    @BindView(R.id.text)
//    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
    }
}
