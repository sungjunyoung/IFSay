package com.flamerestaurant.ifsay.hue;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
import com.philips.lighting.hue.sdk.utilities.PHUtilities;
import com.philips.lighting.model.PHBridge;
import com.philips.lighting.model.PHLightState;

import java.util.Random;

/**
 * Created by lineplus on 2016. 5. 21..
 */
public class HueManager {
    private static final PHHueSDK phHueSDK = PHHueSDK.getInstance();

    public static void connect(PHAccessPoint accessPoint) {
        if (phHueSDK.isAccessPointConnected(accessPoint) == false) {
            phHueSDK.connect(accessPoint);
            fadeIn();
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
        try {
            float xy[] = PHUtilities.calculateXYFromRGB(255, 211, 78, bridge.getResourceCache().getAllLights().get(2).getModelNumber());
            lightState.setOn(true);
            lightState.setX(xy[0]);
            lightState.setY(xy[1]);
            bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
        } catch (Exception e) {
            Log.e("HUE", e.getMessage());
        }
    }
    public static void fadeOut() {
        try {
            PHBridge bridge = phHueSDK.getSelectedBridge();
            PHLightState lightState = new PHLightState();
            lightState.setOn(false);
            bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
        } catch (Exception e) {
            Log.e("HUE", e.getMessage());
        }
    }

    public static void twinkle(final int times) {
        new CountDownTimer(700 * (times + 1), 600) {
            public void onTick(long millisUntilFinished) {
                PHBridge bridge = phHueSDK.getSelectedBridge();
                onAndOff(PHUtilities.calculateXYFromRGB(255, 211, 78, bridge.getResourceCache().getAllLights().get(2).getModelNumber()),
                PHUtilities.calculateXYFromRGB(23, 63, 28, bridge.getResourceCache().getAllLights().get(2).getModelNumber()));
            }
            public void onFinish() {
            }
        }.start();
    }
    public static void alert(final int times) {
        new CountDownTimer(700 * (times + 1), 600) {
            public void onTick(long millisUntilFinished) {
                PHBridge bridge = phHueSDK.getSelectedBridge();
                Random random = new Random();
                onAndOff(PHUtilities.calculateXYFromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255), bridge.getResourceCache().getAllLights().get(2).getModelNumber()),
                PHUtilities.calculateXYFromRGB(random.nextInt(255), random.nextInt(255), random.nextInt(255), bridge.getResourceCache().getAllLights().get(2).getModelNumber()));
            }
            public void onFinish() {
                PHBridge bridge = phHueSDK.getSelectedBridge();
                PHLightState lightState = new PHLightState();
                float[] floats = PHUtilities.calculateXYFromRGB(255, 211, 78, bridge.getResourceCache().getAllLights().get(2).getModelNumber());
                lightState.setX(floats[0]);
                lightState.setY(floats[1]);
                bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
            }
        }.start();
    }

    private static void onAndOff(final float[] defaultXy, final float[] changedXy) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        PHLightState lightState = new PHLightState();
        lightState.setX(changedXy[0]);
        lightState.setY(changedXy[1]);
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                PHBridge bridge = phHueSDK.getSelectedBridge();
                PHLightState lightState = new PHLightState();
                lightState.setX(defaultXy[0]);
                lightState.setY(defaultXy[1]);
                bridge.updateLightState(bridge.getResourceCache().getAllLights().get(2), lightState);
            }
        }, 600);
    }
}
