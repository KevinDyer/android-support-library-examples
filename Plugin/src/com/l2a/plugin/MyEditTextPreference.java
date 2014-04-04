
package com.l2a.plugin;

import com.l2a.plugin.api.SeStorageManager;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.EditTextPreference;
import android.util.AttributeSet;

public class MyEditTextPreference extends EditTextPreference {

    private SharedPreferences mPrefs;

    public MyEditTextPreference(Context context) {
        super(context);

        initialize();
    }

    public MyEditTextPreference(Context context, AttributeSet attrs) {
        super(context, attrs);

        initialize();
    }

    public MyEditTextPreference(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        initialize();
    }

    private void initialize() {
        mPrefs = SeStorageManager.getInstance().getSharedPreferences(0);
    }
    
    @Override
    public SharedPreferences getSharedPreferences() {
        return mPrefs;
    }

    @Override
    protected String getPersistedString(String defaultReturnValue) {
        if (!shouldPersist()) {
            return defaultReturnValue;
        }

        return mPrefs.getString(getKey(), defaultReturnValue);
    }

    @Override
    protected boolean persistString(String value) {
        if (!shouldPersist()) {
            return false;
        }

        if (getPersistedString(value) != null) {
            Editor edit = mPrefs.edit();
            edit.putString(getKey(), value);
            edit.commit();
        }

        return true;
    }
}
