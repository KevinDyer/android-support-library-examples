
package com.l2a.main.action;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.l2a.main.R;
import com.l2a.main.widget.ActionAdapter.Action;

public abstract class FragmentAction implements Action {
    private final FragmentManager mFragmentManager;

    protected FragmentAction(FragmentManager fragmentManager) {
        mFragmentManager = fragmentManager;
    }

    @Override
    public void execute() {
        Fragment fragment = getFragment();
        showFragment(fragment);
    }

    protected abstract Fragment getFragment();

    private void showFragment(Fragment fragment) {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();
    }

    protected FragmentManager getFragmentManager() {
        return mFragmentManager;
    }
}
