package com.nexuslink.guidecar.ui;


import android.app.ProgressDialog;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.clj.fastble.conn.BleCharacterCallback;
import com.clj.fastble.conn.BleGattCallback;
import com.clj.fastble.data.ScanResult;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.ListScanCallback;
import com.clj.fastble.utils.HexUtil;
import com.nexuslink.guidecar.R;
import com.nexuslink.guidecar.service.BlueToothService;
import com.nexuslink.guidecar.ui.adapter.ResultAdapter;
import com.nexuslink.guidecar.ui.base.BaseActivity;
import com.nexuslink.guidecar.util.ToastUtil;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DeviceListActivity extends BaseActivity {

    private static final String TAG = "DeviceListActivity";
    private ResultAdapter mAdapter;
    private BlueToothService blueToothService;
    private UICallBack uiCallBack;
    private ProgressDialog progressDialog;

    @BindView(R.id.list_device)
    ListView deviceList;

    @BindView(R.id.load_fab)
    FloatingActionButton loadFab;

    @BindView(R.id.bluetooth_switch)
    Switch blueToothSwitch;

    @BindView(R.id.flag)
    TextView flagText;

    @OnClick(R.id.load_fab) void scan() {

        if (blueToothService.isBlueEnable()) {
            //为防止用户连续点击，在刷新期间会将按钮设置为不可点击
            Animation animation = AnimationUtils.loadAnimation(this,R.anim.rotate);
            loadFab.setAnimation(animation);

            if(blueToothService == null) {
                bindService();//连接成功后会有回调扫描device
            }else {
                blueToothService.cancelScan();
                blueToothService.setScanCallback(uiCallBack);
                blueToothService.scanDevice();
            }
        } else {
            Toast.makeText(DeviceListActivity.this,"状态栏上还没有蓝牙标志哦",Toast.LENGTH_SHORT).show();
        }
    }

      // (垃圾排版啊，明天与硬件测试下~)
    @OnClick(R.id.imageView12) void testNavi () {
        new AlertDialog
                .Builder(this)
                .setTitle("提示")
                .setMessage("要去”位置A“吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        /*完成数据发送才跳转*/
                        /*为演示视屏*/
                        if(blueToothService != null){
                            BluetoothGatt gatt =  blueToothService.getGatt();
                            List<BluetoothGattService> bluetoothGattServiceList = gatt.getServices();
                            /*这里获得了所有的的service*/
                            for (BluetoothGattService bluetoothGattService : bluetoothGattServiceList) {
                                for ( BluetoothGattCharacteristic characteristic : bluetoothGattService.getCharacteristics()) {
                                    Log.d(TAG, "characteristic" + bluetoothGattService.getUuid().toString());
                                    if (Objects.equals(characteristic.getUuid().toString(), "0000fff1-0000-1000-8000-00805f9b34fb")) {
                                        blueToothService.write(characteristic.getService().getUuid().toString(),
                                                "0000fff1-0000-1000-8000-00805f9b34fb",
                                                "f1",
                                                new BleCharacterCallback() {
                                                    @Override
                                                    public void onSuccess(BluetoothGattCharacteristic characteristic) {
                                                        // 后面再写
                                                        runOnUiThread(new Runnable() {
                                                            @Override
                                                            public void run() {
                                                                ToastUtil.shortToast("开始导航");
                                                                Intent intent = new Intent(DeviceListActivity.this,NaviActivity.class);
                                                                startActivityForResult(intent,0);
                                                            }
                                                        });
                                                    }

                                                    @Override
                                                    public void onFailure(BleException exception) {
                                                        ToastUtil.shortToast("发送失败");
                                                    }
                                                });
                                    }
                                }
                            }
                        } else {
                            ToastUtil.shortToast("blueToothService为空");
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initCallBack();

        mAdapter = new ResultAdapter(this);

        Log.d(TAG, "onCreate: " + mAdapter.getCount());
        deviceList.setAdapter(mAdapter);
        mAdapter.cleanAll();
        deviceList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ScanResult scanResult = mAdapter.getItem(position);
                if (blueToothService != null) {
                    progressDialog = new ProgressDialog(DeviceListActivity.this);
                    progressDialog.setMessage("匹配中...");
                    progressDialog.show();
                    blueToothService.cancelScan();
                    blueToothService.connetDevice(scanResult,false);
                }else {
                    Toast.makeText(DeviceListActivity.this,"捕获了一只野生的错误：blueToothService空",Toast.LENGTH_SHORT).show();
                }
            }
        });


        Animation animation = AnimationUtils.loadAnimation(this,R.anim.rotate);
        loadFab.setAnimation(animation);
        Log.d(TAG, "onCreate: " + (blueToothService == null));
        if(blueToothService == null) {
            bindService();//连接成功后会有回调扫描device
        }else {
            blueToothService.setScanCallback(uiCallBack);
            blueToothService.scanDevice();
        }

        blueToothSwitch.setChecked(true);

        blueToothSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    blueToothService.enableBluetooth();
                    deviceList.setVisibility(View.VISIBLE);
                    flagText.setVisibility(View.GONE);
                } else {
                    blueToothService.disableBluetooth();
                    deviceList.setVisibility(View.GONE);
                    flagText.setVisibility(View.VISIBLE);
                    mAdapter.cleanAll();
                }
            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(blueToothService != null) {
            unBindService();
            blueToothService.onDestroy();
            Log.d(TAG, "onDestroy");
        }
    }

    private void initCallBack() {
        if(uiCallBack == null) {
            uiCallBack = new UICallBack() {
                @Override
                public void showNewItem(ScanResult result) {
                    mAdapter.addNewDevice(result);
                }

                @Override
                public void showScanResult(ScanResult[] results) {
                    loadFab.setClickable(true);
                    loadFab.clearAnimation();
                    Toast.makeText(DeviceListActivity.this,
                            "扫描完成，捕获" + results.length + "个蓝牙设备",
                            Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onConnSuccess() {
                    Toast.makeText(DeviceListActivity.this,
                            "连接成功",
                            Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

                @Override
                public void onConnFail(String failInfo) {
                    Toast.makeText(DeviceListActivity.this,failInfo,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void startScan() {
                    loadFab.setClickable(false);
                    mAdapter.cleanAll();
                    Toast.makeText(DeviceListActivity.this,
                            "开始扫描",
                            Toast.LENGTH_SHORT).show();
                }
            };
        }


    }

    public void bindService() {
        Intent intent = new Intent(DeviceListActivity.this, BlueToothService.class);
        bindService(intent,connection,BIND_AUTO_CREATE);
        Log.d(TAG, "bindService: 服务绑定了");
    }

    public void unBindService() {
        unbindService(connection);
        Log.d(TAG, "unBindService: 服务解绑了");
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //Service连接成功后就开始扫描，扫描的逻辑写在service里，（还有连接的逻辑）
            //为完成回调先获得service后执行回调
            blueToothService = ((BlueToothService.BlueBinder)service).getService();
            blueToothService.setScanCallback(uiCallBack);
            blueToothService.scanDevice();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            blueToothService = null;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 0) {
            new AlertDialog.Builder(DeviceListActivity.this)
                    .setTitle("提示")
                    .setMessage("导航完成")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();
        }
    }
}
