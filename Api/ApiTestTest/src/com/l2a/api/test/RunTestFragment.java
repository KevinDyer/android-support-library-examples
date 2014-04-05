
package com.l2a.api.test;

import java.util.ArrayList;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;

public class RunTestFragment extends ListFragment {
    private static final String TAG = RunTestFragment.class.getSimpleName();

    private ArrayAdapter<String> mAdapter;

    public RunTestFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1,
                new ArrayList<String>());
        setListAdapter(mAdapter);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.fragment_test_results, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*
         * Handle action bar item clicks here. The action bar will automatically
         * handle clicks on the Home/Up button, so long as you specify a parent
         * activity in AndroidManifest.xml.
         */
        int id = item.getItemId();
        if (R.id.action_run_tests == id) {
            runAllTests();

        } else {
            return super.onOptionsItemSelected(item);
        }

        return true;
    }

    private void runAllTests() {
        Result result = JUnitCore.runClasses(AllTests.class);

        mAdapter.add(String.format(
                "Run %d Fail %d Ignore %d Time %d",
                result.getRunCount(),
                result.getFailureCount(),
                result.getIgnoreCount(),
                result.getRunTime()));

        for (Failure failure : result.getFailures()) {
            Log.d(TAG, failure.getTestHeader());
            Log.d(TAG, failure.getTrace());
            if (!TextUtils.isEmpty(failure.getMessage())) {
                Log.d(TAG, failure.getMessage());
            }
        }
    }

}
