
package com.l2a.api.common;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

public class Person implements Parcelable {
    private String mName;
    private int mAge;
    private Bundle mCustom = new Bundle();

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(mName);
        out.writeInt(mAge);
        out.writeBundle(mCustom);
    }

    public static final Parcelable.Creator<Person> CREATOR = new Parcelable.Creator<Person>() {
        public Person createFromParcel(Parcel in) {
            return new Person(in);
        }

        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    private Person(Parcel in) {
        mName = in.readString();
        mAge = in.readInt();
        mCustom = in.readBundle();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public int getAge() {
        return mAge;
    }

    public void setAge(int age) {
        mAge = age;
    }

    public Bundle getCustom() {
        return mCustom;
    }

    public void addCustom(String key, String value) {
        mCustom.putString(key, value);
    }
}
