
package com.l2a.main.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

public class MyFragment extends Fragment {
    private static final String ARG_DISPLAY_NAME = "arg_display_name";

    public static Fragment newinstance(Cursor cursor) {
        Fragment fragment = new MyFragment();
        Bundle args = new Bundle();
        args.putString(
                ARG_DISPLAY_NAME,
                cursor.getString(cursor.getColumnIndex(Contacts.DISPLAY_NAME)));
        fragment.setArguments(args);
        return fragment;
    }

    private String mDisplayName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (null != args) {
            mDisplayName = args.getString(ARG_DISPLAY_NAME);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        TextView view = new TextView(getActivity());
        view.setText(mDisplayName);
        view.setLayoutParams(params);
        view.setGravity(Gravity.CENTER);
        return view;
    }
}
