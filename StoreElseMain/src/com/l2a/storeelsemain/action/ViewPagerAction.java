
package com.l2a.storeelsemain.action;

import com.l2a.storeelsemain.fragment.ViewPagerFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class ViewPagerAction extends FragmentAction {

    public ViewPagerAction(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public CharSequence getTitle() {
        return "View Pager";
    }

    @Override
    public CharSequence getSummary() {
        return null;
    }

    @Override
    protected Fragment getFragment() {
        return new ViewPagerFragment();
    }

}
