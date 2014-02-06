
package com.l2a.storeelsemain;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.l2a.storeelsemain.fragment.GridViewFragment;
import com.l2a.storeelsemain.fragment.ViewPagerFragment;
import com.l2a.storeelsemain.widget.ActionAdapter;
import com.l2a.storeelsemain.widget.ActionAdapter.Action;

public class MainActivity extends ActionBarActivity {
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

        mActionAdapter = new ActionAdapter(this);
        mActionAdapter.add(new GridViewAction());
        mActionAdapter.add(new ViewPagerAction());

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

        // Set the action adapter for the drawer
        mDrawerList.setAdapter(mActionAdapter);
        mDrawerList.setOnItemClickListener(new DrawerItemClickListner());

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        showFragment(new Fragment());
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

    private class DrawerItemClickListner implements OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private class GridViewAction implements Action {
        @Override
        public CharSequence getTitle() {
            return "Grid View";
        }

        @Override
        public CharSequence getSummary() {
            return "Displays the grid view";
        }

        @Override
        public void execute() {
            showFragment(new GridViewFragment());
        }
    }

    private class ViewPagerAction implements Action {
        @Override
        public CharSequence getTitle() {
            return "View Pager";
        }

        @Override
        public CharSequence getSummary() {
            return null;
        }

        @Override
        public void execute() {
            showFragment(new ViewPagerFragment());
        }
    }
}
