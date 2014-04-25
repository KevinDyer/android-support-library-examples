
package com.l2a.main.action;

import java.io.File;
import java.util.Random;

import android.content.Context;

import com.l2a.main.widget.ActionAdapter.Action;

public class NativeAction implements Action {
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
                addRecord(
                        new Random().nextInt(),
                        "p00", "p01", "p02", "p03", "p04",
                        "p05", "p06", "p07", "p08", "p09",
                        System.currentTimeMillis(),
                        "p10", "p11", "p12", "p13", "p14",
                        "p15", "p16", "p17", "p18", "p19",
                        "p20", "p21", "p22", "p23", "p24",
                        "p25", "p26", "p27", "p28", "p29",
                        "p30", "p31", "p32", "p33", "p34",
                        "p35", "p36", "p37", "p38", "p39"
                );
                write(filename);
            }
        }).start();
    }

    private native int addRecord(
            int num,
            String p00, String p01, String p02, String p03, String p04,
            String p05, String p06, String p07, String p08, String p09,
            long timstamp,
            String p10, String p11, String p12, String p13, String p14,
            String p15, String p16, String p17, String p18, String p19,
            String p20, String p21, String p22, String p23, String p24,
            String p25, String p26, String p27, String p28, String p29,
            String p30, String p31, String p32, String p33, String p34,
            String p35, String p36, String p37, String p38, String p39
            );

    private native void write(String filename);

    static {
        System.loadLibrary("Main");
    }
}
