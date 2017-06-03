package com.nexuslink.guidecar.ui.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.clj.fastble.data.ScanResult;
import com.nexuslink.guidecar.R;
import com.nexuslink.guidecar.ui.DeviceListActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rye on 2017/6/2.
 */

public class ResultAdapter extends BaseAdapter{
    private List<ScanResult> results;
    private Context context;

    public ResultAdapter(Context context) {
        this.context = context;
        results = new ArrayList<>();
    }

    public void addNewDevice(ScanResult scanResult){
        results.add(scanResult);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public ScanResult getItem(int position) {
        return results.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        }else {
            convertView = View.inflate(context, R.layout.adapter_scan_result, null);
            holder = new ResultAdapter.ViewHolder();
            convertView.setTag(holder);
            holder.txt_name = (TextView) convertView.findViewById(R.id.txt_name);
            holder.txt_mac = (TextView) convertView.findViewById(R.id.txt_mac);
            holder.txt_rssi = (TextView) convertView.findViewById(R.id.txt_rssi);
        }
        ScanResult result = results.get(position);
        BluetoothDevice device = result.getDevice();
        String name = device.getName();
        String mac = device.getAddress();
        int rssi = result.getRssi();
        holder.txt_name.setText(name);
        holder.txt_mac.setText(mac);
        holder.txt_rssi.setText(String.valueOf(rssi));
        return convertView;
    }

    private class ViewHolder{
        TextView txt_name;//蓝牙名称
        TextView txt_mac;//蓝牙MAC地址
        TextView txt_rssi; //蓝牙信号强度
    }
}
