package com.perspikyliator.user.accelerometer;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import com.firebase.client.Firebase;
import com.perspikyliator.user.accelerometer.adapter.CustomPagerAdapter;
import com.perspikyliator.user.accelerometer.service.AccelService;

public class MainActivity extends AppCompatActivity {

    public static final String URL = "https://dazzling-heat-1034.firebaseio.com";
    public static final String COORDINATE = " Coordinate Graph";
    public static final String ERROR = "Error!!!";
    public static final String LIST = "List of Coordinates";
    public static final String GRAPH = "Graph of Coordinates";
    public static final String DATE = "date";
    public static final String X = "x";
    public static final String Y = "y";
    public static final String Z = "z";

    private Intent mService;
    private ServiceConnection mConn;
    private Firebase mRef;

    boolean bound; // Service connection indicator


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mRef = new Firebase(MainActivity.URL);
        setServiceConnection();

        bindService(mService, mConn, 0);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT)
            setTabLayout();
    }

    /**
     * Method configures TabLayout with ViewPager in the portrait orientation
     */
    private void setTabLayout() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab);
        tabLayout.addTab(tabLayout.newTab().setText(LIST));
        tabLayout.addTab(tabLayout.newTab().setText(GRAPH));

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final CustomPagerAdapter adapter = new CustomPagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }

    public void setServiceConnection() {
        mService = new Intent(this, AccelService.class);

        mConn = new ServiceConnection() {
            public void onServiceConnected(ComponentName name, IBinder binder) {
                bound = true;
            }

            public void onServiceDisconnected(ComponentName name) {
                bound = false;
            }
        };
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_start_service:
                //starts service or binds to the working service
                if (!bound)
                    startService(mService);
                bindService(mService, mConn, 0);
                break;
            case R.id.action_stop_service:
                //stops service
                stopService(mService);
                break;
            case R.id.action_clear:
                //clears the firebase
                mRef.removeValue();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
