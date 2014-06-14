package com.wirsztj.locationpoc.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.util.Log;

import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jerome on 13/06/14.
 */
/*
 *  Need to be implemented as a service
 */
public class LocationUtils implements LocationListener {

    LocationManager locationManager;

    LocationProvider locationProvider;

    String provider;

    Context context;

    private Timer timer = new Timer();

    private int minAccuracy = 100;
    private int timeBetweenUpdate = 60000;
    private int timeOut = 15000;

    List<String> availables = new LinkedList<String>();

    private LocationUtils() {}

    /**
     * Send getSystemService(Context.LOCATION_SERVICE) to the constructor as first argument
     */
    public LocationUtils(Object locationManager, Context context) {
        this.locationManager = (LocationManager)locationManager;
        this.context = context;
        availables  = this.locationManager.getProviders(true);
    }

    public int getMinAccuracy() {
        return minAccuracy;
    }

    public void setMinAccuracy(int minAccuracy) {
        this.minAccuracy = minAccuracy;
    }

    private boolean isAvailable(String s) {
        if (availables.indexOf(s) != -1) {
            return true;
        }
        return false;
    }

    public void launchUpdateLocation() {
        if (isAvailable(LocationManager.NETWORK_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeBetweenUpdate, 0, this);
            Log.e("test", "location request with network");
        }
        else if (isAvailable(LocationManager.GPS_PROVIDER)) {
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeBetweenUpdate, 0, this);
            Log.e("test", "location request with gps");
        }
    }

    @Override
    public void onLocationChanged(final Location location) {
        if (location.getAccuracy() > minAccuracy && location.getProvider().equals(LocationManager.NETWORK_PROVIDER)) {
            Log.e("test", "Not enought accuracy (" + location.getAccuracy() + "), trying with GPS");
            locationManager.removeUpdates(this);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, timeBetweenUpdate, 0, this);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    locationManager.removeUpdates(LocationUtils.this);
                    Log.e("test", "Time is out ! Fetch failed with " + location.getProvider());
                    if (isAvailable(LocationManager.NETWORK_PROVIDER)) { /* We change again to NETWORK for battery drains */
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeBetweenUpdate, 0, LocationUtils.this);
                    }
                }
            }, timeOut);
        }
        else if (location.getAccuracy() <= minAccuracy && location.getProvider() == LocationManager.GPS_PROVIDER) {
            locationManager.removeUpdates(this);
            if (isAvailable(LocationManager.NETWORK_PROVIDER)) { /* We change again to NETWORK for battery drains */
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, timeBetweenUpdate, 0, this);
            }
        }
        if (location.getAccuracy() <= minAccuracy) { /* It's ok, we have good locations data */
            // DO SOMETHING WITH COORDINATES
            Log.e("test", "That's ok, I have an accuracy of " + location.getAccuracy());
        }
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
        Log.e("test", "onStatusChanged");
    }

    @Override
    public void onProviderEnabled(String s) {
        Log.e("test", "onProviderEnabled");
    }

    @Override
    public void onProviderDisabled(String s) {
        Log.e("test", "onProviderDisabled");
    }

    public class MyGps extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.e("test", "received !");

            Location location = intent.getParcelableExtra(LocationManager.KEY_LOCATION_CHANGED);
            onLocationChanged(location);
        }
    }

}
