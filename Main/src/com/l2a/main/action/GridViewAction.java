
package com.l2a.main.action;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.l2a.main.fragment.GridViewFragment;

public class GridViewAction extends FragmentAction {

    public GridViewAction(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getTitle() {
        return "Grid View";
    }

    @Override
    public CharSequence getSummary() {
        return "Displays the grid view";
    }

    @Override
    protected Fragment getFragment() {
        return new GridViewFragment();
    }

}
