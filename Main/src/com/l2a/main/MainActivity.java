
package com.l2a.main;

import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.l2a.api.ApiIntent;
import com.l2a.api.ApiIntentHelper;
import com.l2a.api.common.Person;
import com.l2a.api.init.PluginAction;
import com.l2a.api.init.PluginDescription;
import com.l2a.main.action.GridViewAction;
import com.l2a.main.action.NativeAction;
import com.l2a.main.action.PluginActionCompat;
import com.l2a.main.action.ViewPagerAction;
import com.l2a.main.action.WifiConnectAction;
import com.l2a.main.plugin.PluginManagerService;
import com.l2a.main.widget.ActionAdapter;
import com.l2a.main.widget.ActionAdapter.Action;

public class MainActivity extends ActionBarActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionAdapter mActionAdapter;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitle = mDrawerTitle = getTitle();

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);

        // Set the action adapter for the drawer
        mActionAdapter = new ActionAdapter(this);

        mDrawerList.setAdapter(mActionAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListner());

        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                getSupportActionBar().setTitle(mTitle);
                // creates call to onPrepareOptionsMenu()
                supportInvalidateOptionsMenu();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle(mDrawerTitle);
                // creates call to onPrepareOptionsMenu()
                supportInvalidateOptionsMenu();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        showFragment(new Fragment());

        registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Person person = ApiIntentHelper.getPerson(intent);
                if (null == person) {
                    Log.w(TAG, "No person in intent.");
                    return;
                }

                Log.d(TAG, "Name: " + person.getName());
                Log.d(TAG, "Age: " + Integer.toString(person.getAge()));
                Bundle custom = person.getCustom();
                for (String key : custom.keySet()) {
                    String value = custom.getString(key);
                    Log.d(TAG, String.format("%s: %s", key, value));
                }
            }
        }, new IntentFilter(ApiIntent.ACTION_SEND_PERSON));

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        lbm.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                PluginDescription pluginDescription = ApiIntentHelper.getPluginDescription(intent);
                if (null == pluginDescription) {
                    Log.w(TAG, "No PluginDescription");
                    return;
                }

                handlePluginDescription(pluginDescription);
            }
        }, new IntentFilter(PluginManagerService.ACTION_NEW_PLUGIN_DESCRIPTION));

        PluginManagerService.getPluginDescription(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        /*
         * Sync the toggle state after onRestoreInstanceState has occurred.
         */
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Pass the event to ActionBarDrawerToggle, if it returns true, then it
         * has handled the app icon touch event
         */
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    private void selectItem(int position) {
        final Action action = mActionAdapter.getItem(position);
        action.execute();

        /*
         * Highlight the selected item, update the title, and close the drawer
         */
        mDrawerList.setItemChecked(position, true);
        setTitle(action.getTitle());
        mDrawerLayout.closeDrawer(mDrawerList);
    }

    private void showFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    private void handlePluginDescription(PluginDescription pluginDescription) {
        mActionAdapter.clear();

        if (null == pluginDescription) {
            return;
        }

        mActionAdapter.add(new GridViewAction(getSupportFragmentManager()));
        mActionAdapter.add(new ViewPagerAction(getSupportFragmentManager()));
        mActionAdapter.add(new WifiConnectAction(this));
        mActionAdapter.add(new NativeAction(this));

        List<PluginAction> pluginActions = pluginDescription.getPluginActions();
        for (PluginAction pluginAction : pluginActions) {
            mActionAdapter.add(new PluginActionCompat(pluginAction));
        }
    }

    private class DrawerItemClickListner implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }
}
