package com.wirsztj.locationpoc;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.location.Geofence;
import com.wirsztj.locationpoc.Utils.LocationRegister;
import com.wirsztj.locationpoc.Utils.LocationUtils;
import com.wirsztj.locationpoc.geofencing.FMGeofence;
import com.wirsztj.locationpoc.geofencing.GeofenceRequester;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    LocationUtils locationUtils;
    LocationRegister locationRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
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
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

/*    private void init() {
        LocationUtils locationUtils = new LocationUtils(getSystemService(LOCATION_SERVICE), this);

        //locationUtils.initBestProvider();
        locationUtils.updateLocation();
*//*        Log.e("test-lastLocationLatitude", locationUtils.getLastKnowLocation().getLatitude() + "");
        Log.e("test-lastLocationLongitude", locationUtils.getLastKnowLocation().getLongitude() + "");*//*
    }*/

    private void init() {
        addLocation();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initLocationUtils() {
        if (locationUtils == null) {
            locationUtils = new LocationUtils(getSystemService(LOCATION_SERVICE), this);
        }

        locationUtils.launchUpdateLocation();
    }

    private void initLocationRegister() {
        if (locationRegister == null) {
            locationRegister = new LocationRegister(getSystemService(LOCATION_SERVICE), this);
        }

        Log.e("test", "I'm instantiating LocationRegister");

        List<Location> list = new LinkedList<Location>();

        Location l = new Location(LocationManager.NETWORK_PROVIDER);

        l.setLongitude(2.3529471);
        l.setLatitude(48.8909279);

        list.add(l);

        locationRegister.setLocationList(list);
    }

    private void addLocation() {
        GeofenceRequester gr = new GeofenceRequester(this);

        initFakeLocation(gr);

        //FMGeofence fm = new FMGeofence("toto42", 48.8909279, 2.3529471, 1000, -1, Geofence.GEOFENCE_TRANSITION_DWELL, 5000); // PurchEase

        //LocationSaver.getInstance().addGeofence(fm);

        //gr.addGeofence(fm.toGeofence());
    }

    private void initFakeLocation(GeofenceRequester gr) {
        Integer id = 0;
        List<String> strings = new LinkedList<String>();

        strings.add("26 rue ordener 75018 Paris");
        strings.add("26 rue ordener 75018 Paris");
        strings.add("37 bis boulevard de la Chapelle 75018 Paris");

        List<Address> addresses = null;
        List<Geofence> tmp = new ArrayList<Geofence>();
        Geocoder geocoder = new Geocoder(this);

        for (String s : strings) {
            try {
                addresses = geocoder.getFromLocationName(s, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (addresses.size() > 0) {
                Address a = addresses.get(0);
                FMGeofence fm = new FMGeofence(id.toString(), a.getLatitude(), a.getLongitude(), 100, -1, Geofence.GEOFENCE_TRANSITION_ENTER, 0);

                LocationSaver.getInstance().addGeofence(fm);

                PLog.e("test", fm.toGeofence() + "");

                tmp.add(fm.toGeofence());

                id++;
            }
        }

        gr.addGeofences(tmp);
    }

}
