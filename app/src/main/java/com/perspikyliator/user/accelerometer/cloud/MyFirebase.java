package com.perspikyliator.user.accelerometer.cloud;

import com.firebase.client.Firebase;

/**
 * Sets the firebase to Application context
 */
public class MyFirebase extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
