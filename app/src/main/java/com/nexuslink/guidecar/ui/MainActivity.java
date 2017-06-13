package com.nexuslink.guidecar.ui;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.nexuslink.guidecar.R;
import com.nexuslink.guidecar.model.bean.ForecastBean;
import com.nexuslink.guidecar.net.RetrofitClient;
import com.nexuslink.guidecar.net.WeatherApiService;
import com.nexuslink.guidecar.presenter.IMainPresenter;
import com.nexuslink.guidecar.presenter.MainPresenter;
import com.nexuslink.guidecar.ui.base.BaseActivity;
import com.nexuslink.guidecar.util.Config;
import com.nexuslink.guidecar.util.IntentUtil;
import com.nexuslink.guidecar.util.ToastUtil;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, IMainView {

    private static final int SET_REQUEST = 0;
    private static final String TAG = "MainActivity";
    private IMainPresenter presenter;

    // 为录视频而写的
    @OnClick(R.id.imageView12) void testNavi () {
        new AlertDialog
                .Builder(this)
                .setTitle("提示")
                .setMessage("要去”位置A“吗？")
                .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, NaviActivity.class);
                        startActivity(intent);
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

    @OnClick(R.id.fab_bluetooth)
    void connect() {

        //测试蓝牙连接
        if (!getBleManager().isBlueEnable()) {
            new AlertDialog.Builder(this)
                    .setTitle("提示")
                    .setMessage("连接小车需要打开蓝牙,是否开启")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getBleManager().enableBluetooth();
                            Toast.makeText(MainActivity.this, "蓝牙开启成功", Toast.LENGTH_SHORT).show();
                            showDeviceListPage();
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .show();
        } else {
            showDeviceListPage();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        requestPermission();


/*
        startLocation();
*/
        presenter = new MainPresenter(this);
        //测试和风天气接口
        /*presenter.requestWeather("重庆");*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void requestPermission() {
        RxPermissions.getInstance(this)
                .request(Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(@NonNull Boolean aBoolean) throws Exception {

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {

                    }
                });

    }


    private void test() {
        RetrofitClient.Create(WeatherApiService.class)
                .requestForecast("重庆", Config.KEY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ForecastBean>() {
                    @Override
                    public void accept(@NonNull ForecastBean forecastBean) throws Exception {
                        String city = forecastBean.getHeWeather5().get(0).getBasic().getCity();
                        Log.d(TAG, "accept: " + city);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(@NonNull Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: " + throwable.getMessage());
                    }
                });
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // TODO: 2017/6/13 名字等等改
        if (id == R.id.nav_camera) {
            // Handle the camera action
        /*} else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {*/

        } else if (id == R.id.nav_manage) {
            IntentUtil.simpleStartForresultActivity(this, SettingActivity.class, SET_REQUEST);
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case SET_REQUEST:
                    recreate();
                    break;
            }
        }
    }

    /**
     * 刷新天气信息
     * @param weatherList 从当天算起的10条天气信息
     */

    @Override
    public void updateWeatherInfo(List<ForecastBean.HeWeather5Bean> weatherList) {
        // TODO: 2017/5/10 更新UI
        ToastUtil.shortToast(weatherList.get(0).getDaily_forecast().get(0).getDate());


    }

    public void showDeviceListPage() {
        Intent intent = new Intent(MainActivity.this, DeviceListActivity.class);
        startActivity(intent);
    }
/*
    //定位(好像用不到)
    public void startLocation() {
        final Long start = System.currentTimeMillis();
        Log.d(TAG, "startLocation: start: "+ start);
        final LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.d(TAG, "startLocation: end - start: "+ (System.currentTimeMillis() - start) + "ms");
                Log.d(TAG, "onLocationChanged: "+location.getAltitude()+"");
                locationManager.removeUpdates(this);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }*/
}
