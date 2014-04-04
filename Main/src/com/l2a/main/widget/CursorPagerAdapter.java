
package com.l2a.main.widget;

import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.l2a.main.fragment.MyFragment;

public class CursorPagerAdapter extends FragmentPagerAdapter {
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
