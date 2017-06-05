package com.nexuslink.guidecar.ui;

import android.bluetooth.BluetoothGatt;

import com.clj.fastble.data.ScanResult;

/**
 * Created by Rye on 2017/6/4.
 */

public interface UICallBack {
    void onConnSuccess();

    void onConnFail(String failInfo);

    void startScan();

    void showNewItem(ScanResult result);

    void showScanResult(ScanResult[] results);
}
