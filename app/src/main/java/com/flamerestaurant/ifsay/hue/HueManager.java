package com.flamerestaurant.ifsay.hue;

import com.philips.lighting.hue.sdk.PHAccessPoint;
import com.philips.lighting.hue.sdk.PHBridgeSearchManager;
import com.philips.lighting.hue.sdk.PHHueSDK;
import com.philips.lighting.hue.sdk.PHSDKListener;
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

    public static void twinkle(int times) {
        PHBridge bridge = phHueSDK.getSelectedBridge();
        PHLightState lightState = new PHLightState();
        lightState.setHue(12345);
        bridge.updateLightState(bridge.getResourceCache().getAllLights().get(3), lightState);
    }
}
