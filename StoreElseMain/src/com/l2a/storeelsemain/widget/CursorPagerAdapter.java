
package com.l2a.storeelsemain.widget;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.l2a.storeelsemain.fragment.MyFragment;

public class CursorPagerAdapter extends FragmentStatePagerAdapter {
    private Cursor mCursor;

    public CursorPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if (null == mCursor) {
            throw new RuntimeException("mCursor is null.");
        }

        mCursor.moveToPosition(position);
        return MyFragment.newinstance(mCursor);
    }

    @Override
    public int getCount() {
        if (null == mCursor) {
            return 0;
        }

        return mCursor.getCount();
    }

    public void swapCursor(Cursor cursor) {
        if (mCursor == cursor) {
            return;
        }

        mCursor = cursor;
        notifyDataSetChanged();
    }
}
