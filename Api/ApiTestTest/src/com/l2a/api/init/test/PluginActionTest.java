
package com.l2a.api.init.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.l2a.api.init.PluginAction;

public class PluginActionTest {
    @Mock
    Context mContext;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test() {
        String expectedName = "name";
        PendingIntent expectedIntent = PendingIntent.getActivity(mContext, 0, new Intent(), 0);
        PluginAction pluginAction = new PluginAction(expectedName, expectedIntent);

        Assert.assertEquals(expectedName, pluginAction.getDisplayName());
        Assert.assertEquals(expectedIntent, pluginAction.getIntent());
    }

}
