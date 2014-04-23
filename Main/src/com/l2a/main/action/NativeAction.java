package com.l2a.main.action;

import com.l2a.main.widget.ActionAdapter.Action;

public class NativeAction implements Action {

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
        final String filename = "";
        new Thread(new Runnable() {
            @Override
            public void run() {
                create();
                
                addRecord();
                addRecord();
                addRecord();
                
                write(filename);
                
                destroy();
            }
        }).start();
    }

    private native void create();
    private native int addRecord();
    private native void write(String filename);
    private native void destroy();
}
