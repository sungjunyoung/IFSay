package com.flamerestaurant.ifsay;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.flamerestaurant.ifsay.hue.HueManager;
import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHMessageType;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.model.PHBridge;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class IfsayApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Context context = getApplicationContext();

        RealmConfiguration config = new RealmConfiguration.Builder(context)
                .name("ifsay.realm")
                .build();
        Realm.setDefaultConfiguration(config);

        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());

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

}