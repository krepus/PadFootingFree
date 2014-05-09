package com.padfootingfree;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import com.google.android.gms.ads.*;


public class MainActivity extends FragmentActivity {
    //logging
    public static final String mDebugTag = "main activity";
    public static final boolean mDebugLog = true;

    private FragmentTabHost mTabHost;
    private InterstitialAd interstitial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        PreferenceManager.setDefaultValues(this, R.xml.preference, false);
        // Create the interstitial.
        interstitial = new InterstitialAd(this);
        interstitial.setAdUnitId(getString(R.string.ad_unit_ID));
        // Create ad request.
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)        // All emulators
                .addTestDevice("E204AA8798DCD03CAF2D96BEAEFB39B3")  // My Galaxy Nexus test phone
                .build();
        // Begin loading your interstitial.
        interstitial.loadAd(adRequest);


        Context mCtxt = this;
        FragmentManager fragmentManager = getSupportFragmentManager();
        mTabHost = new FragmentTabHost(mCtxt);
        mTabHost.setup(mCtxt, fragmentManager, R.id.fragment_container);

        // mTabHost.addTab(mTabHost.newTabSpec("design brief").setIndicator("design brief"),
        //         DesignBriefFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("design input").setIndicator("DESIGN INPUT"),
                DesignInputFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("design out").setIndicator("DESIGN OUT"),
                DesignOutFragment.class, null);
        setContentView(mTabHost);


    }

    @Override
    public void onPause() {
        super.onPause();
        if (interstitial.isLoaded()) {
            interstitial.show();
        }

        logDebug("on pause");

    }

    public void onDestroy() {
        super.onDestroy();
        /*if (interstitial.isLoaded()) {
            interstitial.show();

        }*/
        logDebug("on destroy");
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
        /*int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);*/

        switch (item.getItemId()) {

            case R.id.preferences_id:
                // Display the fragment as the menu_main content.

                Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                startActivityForResult(intent, 0);
                return true;

            case R.id.About:

                String msg = "This app will determine the bearing pressure under a rectangular" +
                        "isolated/pad footing" + "\r\n\r\n" +
                        "For sanity check only";
                alert(msg);

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    void alert(String message) {
        AlertDialog.Builder bld = new AlertDialog.Builder(this);
        bld.setMessage(message);
        bld.setNeutralButton("OK", null);
        //Log.d(TAG, "Showing alert dialog: " + message);
        bld.create().show();
    }

    void logDebug(String msg) {
        if (mDebugLog) Log.d(mDebugTag, msg);
    }

}
