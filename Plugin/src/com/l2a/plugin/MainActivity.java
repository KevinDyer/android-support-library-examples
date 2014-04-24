
package com.l2a.plugin;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.l2a.api.ApiIntent;
import com.l2a.api.ApiIntentHelper;
import com.l2a.api.common.Person;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MainFragment())
                    .commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here. The action bar will automatically
         * handle clicks on the Home/Up button, so long as you specify a parent
         * activity in AndroidManifest.xml.
         */
        int id = item.getItemId();
        if (R.id.action_settings == id) {
            Person person = new Person();
            person.setName("John Smith");
            person.setAge(28);
            person.addCustom("This is a key", "This is a value");
            person.addCustom("This is a key too", "This is a value too");
            Intent intent = ApiIntentHelper.putPerson(new Intent(), person);
            intent.setAction(ApiIntent.ACTION_SEND_PERSON);
            sendBroadcast(intent);
            
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class MainFragment extends ListFragment {

        public MainFragment() {
        }
    }

}
