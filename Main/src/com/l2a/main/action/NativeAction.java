
package com.l2a.main.action;

import java.io.File;

import android.content.Context;
import android.os.SystemClock;
import android.util.Log;

import com.l2a.main.widget.ActionAdapter.Action;

public class NativeAction implements Action {
    protected static final String TAG = NativeAction.class.getSimpleName();
    private final Context mContext;

    public NativeAction(Context context) {
        mContext = context;
    }

    @Override
    public CharSequence getTitle() {
        return "Run native code";
    };

    @Override
    public CharSequence getSummary() {
        return "TODO Status";
    }

    @Override
    public void execute() {
        File file = new File(mContext.getFilesDir(), "Text.txt");
        final String filename = file.getAbsolutePath();
        new Thread(new Runnable() {
            @Override
            public void run() {
                long now = System.currentTimeMillis();
                long up = SystemClock.uptimeMillis();
                Log.d(TAG, String.format("addRecord(%016X,%016X)", now, up));
                addRecord(/*
                        "p00",
                        "p01",
                        "p02",
                        "p03",
                        "p04",
                        "p05",
                        "p06",
                        "p07",
                        "p08",
                        "p09",
                        "p10",
                        "p11",
                        "p12",
                        "p13",
                        "p14",
                        "p15",
                        "p16",
                        "p17",
                        "p18",
                        "p19",
                        "p20",
                        "p21",
                        "p22",
                        "p23",
                        "p24",
                        "p25",
                        "p26",
                        "p27",
                        "p28",
                        "p29",*/
                        now,
                        up);

                write(filename);
            }
        }).start();
    }

    private native void addRecord(/*
            String p00,
            String p01,
            String p02,
            String p03,
            String p04,
            String p05,
            String p06,
            String p07,
            String p08,
            String p09,
            String p10,
            String p11,
            String p12,
            String p13,
            String p14,
            String p15,
            String p16,
            String p17,
            String p18,
            String p19,
            String p20,
            String p21,
            String p22,
            String p23,
            String p24,
            String p25,
            String p26,
            String p27,
            String p28,
            String p29,*/
            long l1,
            long l2);

    private native void write(String filename);

    static {
        System.loadLibrary("Main");
    }
}
