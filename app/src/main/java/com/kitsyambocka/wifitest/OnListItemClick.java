package com.kitsyambocka.wifitest;

import android.net.wifi.ScanResult;

/**
 * Created by Developer on 04.01.2017.
 */

public interface OnListItemClick {
    void onWifiClick(ScanResult scanResult);
}
