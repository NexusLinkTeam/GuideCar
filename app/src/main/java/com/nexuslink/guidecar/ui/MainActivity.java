package com.nexuslink.guidecar.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,IMainView {

    private static final int SET_REQUEST = 0;
    private static final String TAG = "MY_TAG";
    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        presenter = new MainPresenter(this);
        presenter.requestWeather("重庆");
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
                        Log.d(TAG, "accept: "+ throwable.getMessage());
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            IntentUtil.simpleStartForresultActivity(this,SettingActivity.class,SET_REQUEST);
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
        if(resultCode == RESULT_OK){
            switch (requestCode){
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
}
