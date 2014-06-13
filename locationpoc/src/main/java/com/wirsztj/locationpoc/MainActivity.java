package com.wirsztj.locationpoc;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.wirsztj.locationpoc.Utils.LocationUtils;


public class MainActivity extends ActionBarActivity {

    LocationUtils locationUtils;

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (locationUtils == null)
            locationUtils = new LocationUtils(getSystemService(LOCATION_SERVICE), this);

        locationUtils.launchUpdateLocation();
    }
}
