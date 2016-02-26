package com.perspikyliator.user.accelerometer.service;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import com.firebase.client.Firebase;
import com.perspikyliator.user.accelerometer.MainActivity;
import com.perspikyliator.user.accelerometer.model.AccelData;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AccelService extends Service {

    Timer timer;
    Firebase mRef;

    private SensorManager mSensorManager;
    private float[] values = new float[3];

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new Binder();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        Sensor mSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mSensorManager.registerListener(listener, mSensor, SensorManager.SENSOR_DELAY_NORMAL);

        mRef = new Firebase(MainActivity.URL);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                setData();
            }
        };
        timer.schedule(task, 0, 1000);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        timer.cancel();
        mSensorManager.unregisterListener(listener);
    }

    /**
     * Method writes the data to the firebase
     */
    public void setData() {
        String currentDateTimeStringEng = DateFormat.getDateTimeInstance(
                DateFormat.DEFAULT, DateFormat.DEFAULT, Locale.ENGLISH).format(new Date());
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        AccelData mData = new AccelData(currentDateTimeString,
                String.format("%.2f", values[0]),
                String.format("%.2f", values[1]),
                String.format("%.2f", values[2]));
        mRef.child(currentDateTimeStringEng).setValue(mData);
    }

    SensorEventListener listener = new SensorEventListener() {

        /**
         * Method gets the data from Accelerometer's sensor
         * @param event contains changed coordinates
         */
        @Override
        public void onSensorChanged(SensorEvent event) {
            switch (event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    System.arraycopy(event.values, 0, values, 0, 3);
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

}
