package com.flamerestaurant.ifsay.hue;

import android.os.CountDownTimer;
import android.os.Handler;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLightState;

/**
 * Created by lineplus on 2016. 5. 21..
 */
public class HueManager {
    private static final PHHueSDK phHueSDK = PHHueSDK.getInstance();

    public static void connect(PHAccessPoint accessPoint) {
        if (phHueSDK.isAccessPointConnected(accessPoint) == false) {
            phHueSDK.connect(accessPoint);
        }
        return;

    }
    public static void connect(PHSDKListener listener) {
        PHBridgeSearchManager sm = (PHBridgeSearchManager) phHueSDK.getSDKService(PHHueSDK.SEARCH_BRIDGE);
        sm.search(true, true);
        phHueSDK.getNotificationManager().registerSDKListener(listener);
    }

    public static void fadeIn() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        PHLightState lightState = new PHLightState();
        float xy[] = PHUtilities.calculateXYFromRGB(255, 211, 78, bridge.getResourceCache().getAllLights().get(2).getModelNumber());
        lightState.setOn(true);
        lightState.setX(xy[0]);
        lightState.setY(xy[1]);
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
    }
    public static void fadeOut() {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        PHLightState lightState = new PHLightState();
        lightState.setOn(false);
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
    }

    public static void twinkle(final int times) {
        new CountDownTimer(1200 * (times + 1), 1200) {
            public void onTick(long millisUntilFinished) {
                PHBridge bridge = phHueSDK.getSelectedBridge();
                PHLightState lightState = new PHLightState();
                float xy[] = PHUtilities.calculateXYFromRGB(23, 63, 28, bridge.getResourceCache().getAllLights().get(2).getModelNumber());
                lightState.setX(xy[0]);
                lightState.setY(xy[1]);
                bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        PHBridge bridge = phHueSDK.getSelectedBridge();
                        PHLightState lightState = new PHLightState();
                        float xyDefault[] = PHUtilities.calculateXYFromRGB(255, 211, 78, bridge.getResourceCache().getAllLights().get(2).getModelNumber());
                        lightState.setX(xyDefault[0]);
                        lightState.setY(xyDefault[1]);
                        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
                    }
                }, 500);
            }

            public void onFinish() {
            }
        }.start();

    }
}
