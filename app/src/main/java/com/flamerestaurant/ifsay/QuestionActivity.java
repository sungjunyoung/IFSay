package com.flamerestaurant.ifsay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.flamerestaurant.ifsay.hue.HueManager;
import com.flamerestaurant.ifsay.realm.Ifsay;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;

import java.util.List;

import io.realm.Realm;

public class QuestionActivity extends Activity {

    private static int PAGE_SIZE = 5;

    private Realm realm;

    private ViewPager pager;
    private EditText edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        realm = Realm.getDefaultInstance();

        pager = (ViewPager) findViewById(R.id.today_pager);
        pager.setAdapter(new Adapter());

        edit = (EditText) findViewById(R.id.today_write_text);

        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.bridge_info_file), Context.MODE_PRIVATE);
        String ip = sharedPreferences.getString(getString(R.string.bridge_ip_key), null);
        String userName = sharedPreferences.getString(getString(R.string.bridge_user_name_key), null);
        Log.d("HUE", "ID " + ip + " NAME " + userName);
        if (ip != null && userName != null) {
            HueManager.connect(new PHAccessPoint(ip, userName, ""));
        } else {
            HueManager.connect(new PHSDKListener() {
                @Override
                public void onAccessPointsFound(List accessPoint) {
                    Log.i("HUE", "onAccessPointsFound: " + accessPoint);
                    if (accessPoint.size() == 1) {
                        HueManager.connect((PHAccessPoint) accessPoint.get(0));
                    }
                }

                @Override
                public void onCacheUpdated(List cacheNotificationsList, PHBridge bridge) {
                    Log.i("HUE",  "onCacheUpdated  " + bridge.getResourceCache().getBridgeConfiguration().getUsername());
                    if (cacheNotificationsList.contains(PHMessageType.LIGHTS_CACHE_UPDATED)) {
                        System.out.println("Lights Cache Updated ");
                    }
                }

                @Override
                public void onBridgeConnected(PHBridge b, String username) {
                    Log.i("HUE",  "onBridgeConnected");
                    PHHueSDK instance = PHHueSDK.getInstance();
                    instance.setSelectedBridge(b);
                    instance.enableHeartbeat(b, PHHueSDK.HB_INTERVAL);
                    SharedPreferences bridgeInfo = getSharedPreferences(getString(R.string.bridge_info_file), Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = bridgeInfo.edit();
                    edit.putString(getString(R.string.bridge_ip_key), b.getResourceCache().getBridgeConfiguration().getIpAddress());
                    edit.putString(getString(R.string.bridge_user_name_key), b.getResourceCache().getBridgeConfiguration().getUsername());
                    edit.commit();
                }

                @Override
                public void onAuthenticationRequired(PHAccessPoint accessPoint) {
                    Log.i("HUE",  "onAuthenticationRequired");
                    PHHueSDK.getInstance().startPushlinkAuthentication(accessPoint);
                }

                @Override
                public void onConnectionResumed(PHBridge bridge) {
                    Log.i("HUE",  "onConnectionResumed " + bridge.getResourceCache().getBridgeConfiguration().getUsername());
                }

                @Override
                public void onConnectionLost(PHAccessPoint accessPoint) {
                    Log.e("HUE",  "onConnectionLost");

                }

                @Override
                public void onError(int code, final String message) {
                    Log.e("HUE",  "onError - " + code + " : " + message);
                }

                @Override
                public void onParsingErrors(List parsingErrorsList) {
                    Log.i("HUE",  "vonParsingErrors ; " +  parsingErrorsList);
                }
            });
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }

    public void onClickWite(View view) {
        realm.beginTransaction();

        Ifsay ifsay = realm.createObject(Ifsay.class);
        ifsay.setQuestionId(pager.getCurrentItem());
        ifsay.setContent(edit.getText().toString());

        realm.commitTransaction();

        startActivity(new Intent(this, IfsayActivity.class));
    }

    private class Adapter extends PagerAdapter {

        @Override
        public int getCount() {
            return PAGE_SIZE;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View view = getLayoutInflater().inflate(R.layout.layout_today_page, container, false);

            TextView title = (TextView) view.findViewById(R.id.today_title);
            TextView body = (TextView) view.findViewById(R.id.today_body);

            title.setText(String.format("제목 %d", position));
            body.setText(String.format("내용 %d", position));

            container.addView(view);
            return view;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}
