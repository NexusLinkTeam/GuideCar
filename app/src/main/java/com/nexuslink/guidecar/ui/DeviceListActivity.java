package com.nexuslink.guidecar.ui;


import android.bluetooth.BluetoothGatt;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.clj.fastble.conn.BleGattCallback;
import com.clj.fastble.data.ScanResult;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.ListScanCallback;
import com.nexuslink.guidecar.R;
import com.nexuslink.guidecar.ui.adapter.ResultAdapter;
import com.nexuslink.guidecar.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DeviceListActivity extends BaseActivity {

    private static final String TAG = "DeviceListActivity";
    private ResultAdapter mAdapter;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    @BindView(R.id.list_device)
    ListView deviceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);

        mAdapter = new ResultAdapter(this);
        deviceList.setAdapter(mAdapter);
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScanResult target = mAdapter.getItem(position);
                getBleManager().connectDevice(target, true, new BleGattCallback() {
                    @Override
                    public void onNotFoundDevice() {

                    }

                    @Override
                    public void onFoundDevice(ScanResult scanResult) {

                    }

                    @Override
                    public void onConnectSuccess(BluetoothGatt gatt, int status) {
                        Log.d(TAG, "onConnectSuccess: connect_Success");
                        gatt.discoverServices();
                        finish();
                    }

                    @Override
                    public void onConnectFailure(BleException exception) {
                        Log.d(TAG, "onConnectFailure: connect_fail");
                    }
                });
            }
        });

        // 10s 为测试
        Boolean s = getBleManager().scanDevice(new ListScanCallback(5000L) {
            @Override
            public void onScanning(final ScanResult result) {
                //该回调在子线程中,更新UI在主线程中运行
                runOnMainThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.addNewDevice(result);
                    }
                });
                result.getRssi();
                Log.d(TAG, "onScanning: "+result.getRssi());
            }

            @Override
            public void onScanComplete(ScanResult[] results) {
                
                Log.d(TAG, "onScanComplete: "+ results.length);
            }
        });
        Log.d(TAG, "onCreate: "+s);
    }
    public void runOnMainThread(Runnable runnable){
        if(Looper.myLooper() == Looper.getMainLooper()){
            runnable.run();
        }else {
            mHandler.post(runnable);
        }
    }
}
