package com.nexuslink.guidecar.service;

import android.app.Service;
import android.bluetooth.BluetoothGatt;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.clj.fastble.BleManager;
import com.clj.fastble.conn.BleGattCallback;
import com.clj.fastble.data.ScanResult;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.ListScanCallback;
import com.nexuslink.guidecar.ui.UICallBack;
import com.nexuslink.guidecar.util.BleManagerUtil;

import javax.security.auth.callback.Callback;

/**
 * Created by Rye on 2017/6/4.
 */

public class BlueToothService extends Service{
    private static final long SCAN_TIME_OUT = 4000;//扫描8秒超时
    private static final String TAG = "BlueToothService";
    private BlueBinder blueBinder = new BlueBinder();
    private BleManager bleManager;
    private Handler mThreadHandler = new Handler(Looper.getMainLooper());

    private UICallBack uiCallback;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return blueBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        bleManager = BleManagerUtil.getInstance();
        bleManager.enableBluetooth();//开启蓝牙
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void cancelScan() {
        bleManager.cancelScan();
    }

    public class BlueBinder extends Binder{
        public BlueToothService getService(){
            return BlueToothService.this;
        }
    }

    public void scanDevice() {
        Log.d(TAG, "start");

        if(uiCallback != null) {
            uiCallback.startScan();
        }else {
            Log.e(TAG, "scanDevice: scanCallback 为空");
        }

        Log.d(TAG, "scanDevice: scan");
        Boolean b = bleManager.scanDevice(new ListScanCallback(SCAN_TIME_OUT) {
            @Override
            public void onScanning(final ScanResult result) {
                //当前线程不是UI线程，更新UI需要放到主线程当中执行
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        uiCallback.showNewItem(result);
                    }
                });
            }

            @Override
            public void onScanComplete(ScanResult[] results) {
                uiCallback.showScanResult(results);
            }
        });
        Log.d(TAG, b + "");
    }

    // 感觉这个项目里这个方法执行都是在子线程吧(所以那个if意义何在？？)
    private void runOnMainThread(Runnable runnable) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            runnable.run();
        }else {
             mThreadHandler.post(runnable);
        }
    }

    public void connetDevice(ScanResult scanResult, Boolean autoConnect) {
        bleManager.connectDevice(scanResult, autoConnect, new BleGattCallback() {
            @Override
            public void onNotFoundDevice() {
                uiCallback.onConnFail("NotFoundDevice");
            }

            @Override
            public void onFoundDevice(ScanResult scanResult) {
            }

            @Override
            public void onConnectSuccess(BluetoothGatt gatt, int status) {
                gatt.discoverServices();
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        uiCallback.onConnSuccess();
                    }
                });
            }

            @Override
            public void onConnectFailure(BleException exception) {
                Log.d(TAG, "onConnectFailure");
                uiCallback.onConnFail(exception.toString());
            }
        });
    }

    public void setScanCallback(UICallBack uiCallback) {
        Log.d(TAG, "setCallback");
        this.uiCallback = uiCallback;
    }

    public void enableBluetooth() {
        bleManager.enableBluetooth();
    }

    public void disableBluetooth () {
        bleManager.disableBluetooth();
    }

    public Boolean isBlueEnable() {
        return bleManager.isBlueEnable();
    }

}
