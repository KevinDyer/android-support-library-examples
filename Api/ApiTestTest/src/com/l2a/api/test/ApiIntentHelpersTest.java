
package com.l2a.api.test;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import android.content.Intent;

import com.l2a.api.ApiIntent;
import com.l2a.api.ApiIntentHelper;
import com.l2a.api.init.PluginDescription;

@RunWith(MockitoJUnitRunner.class)
public class ApiIntentHelpersTest {

    @Test
    public void testPutPluginDescription() {
        PluginDescription pluginDescription = new PluginDescription();
        Intent intent = new Intent();

        Intent result = ApiIntentHelper.putPluginDescription(intent, pluginDescription);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION));
        Assert.assertEquals(
                pluginDescription,
                result.getParcelableExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION));
    }

}
