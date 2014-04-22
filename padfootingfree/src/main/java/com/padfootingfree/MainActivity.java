package com.padfootingfree;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

    private FragmentTabHost mTabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //set defaults
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);

        Context mCtxt = this;
        FragmentManager fragmentManager = getSupportFragmentManager();
        mTabHost = new FragmentTabHost(mCtxt);
        mTabHost.setup(mCtxt, fragmentManager, R.id.fragment_container);

        // mTabHost.addTab(mTabHost.newTabSpec("design brief").setIndicator("design brief"),
        //         DesignBriefFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("design input").setIndicator("design input"),
                DesignInputFragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("design out").setIndicator("design out"),
                DesignOutFragment.class, null);
        setContentView(mTabHost);
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

}
