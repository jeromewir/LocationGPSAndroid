package com.wirsztj.locationpoc;

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

    /**
     * @param geofenceId
     *            The Geofence's request ID
     * @param latitude
     *            Latitude of the Geofence's center.
     * @param longitude
     *            Longitude of the Geofence's center.
     * @param radius
     *            Radius of the geofence circle.
     * @param expiration
     *            Geofence expiration duration
     * @param transition
     *            Type of Geofence transition.
     */

    private void addLocation() {
        GeofenceRequester gr = new GeofenceRequester(this);

        FMGeofence fm = new FMGeofence("toto42", 48.8909279, 2.3529471, 1000, -1, Geofence.GEOFENCE_TRANSITION_DWELL, 20000);

        gr.addGeofence(fm.toGeofence());
    }
}
