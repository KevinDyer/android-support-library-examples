
package com.l2a.api.init;

import java.util.ArrayList;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class PluginDescription implements Parcelable {
    private List<PluginAction> mPluginActions = new ArrayList<PluginAction>();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeList(mPluginActions);
    }

    public static final Parcelable.Creator<PluginDescription> CREATOR = new Parcelable.Creator<PluginDescription>() {
        public PluginDescription createFromParcel(Parcel in) {
            return new PluginDescription(in);
        }

        public PluginDescription[] newArray(int size) {
            return new PluginDescription[size];
        }
    };

    private PluginDescription(Parcel in) {
        in.readList(mPluginActions, null);
    }

    public PluginDescription() {
    }

    public boolean addPluginAction(PluginAction pluginAction) {
        return mPluginActions.add(pluginAction);
    }

    public List<PluginAction> getPluginActions() {
        return mPluginActions;
    }
}
