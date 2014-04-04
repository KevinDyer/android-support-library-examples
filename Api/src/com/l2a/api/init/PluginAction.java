
package com.l2a.api.init;

import android.app.PendingIntent;
import android.os.Parcel;
import android.os.Parcelable;

public class PluginAction implements Parcelable {
    private final String mDisplayName;
    private final PendingIntent mIntent;

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mDisplayName);
        out.writeParcelable(mIntent, flags);
    }

    public static final Parcelable.Creator<PluginAction> CREATOR = new Parcelable.Creator<PluginAction>() {
        public PluginAction createFromParcel(Parcel in) {
            return new PluginAction(in);
        }

        public PluginAction[] newArray(int size) {
            return new PluginAction[size];
        }
    };

    private PluginAction(Parcel in) {
        mDisplayName = in.readString();
        mIntent = in.readParcelable(null);
    }

    public PluginAction(String displayName, PendingIntent intent) {
        mDisplayName = displayName;
        mIntent = intent;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public PendingIntent getIntent() {
        return mIntent;
    }
}
