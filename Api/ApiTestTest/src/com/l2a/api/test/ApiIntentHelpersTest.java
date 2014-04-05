
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
    public void putPluginDescription() {
        PluginDescription pluginDescription = new PluginDescription();
        Intent intent = new Intent();

        Intent result = ApiIntentHelper.putPluginDescription(intent, pluginDescription);

        Assert.assertNotNull(result);
        Assert.assertEquals(
                pluginDescription,
                result.getParcelableExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION));
    }

    @Test
    public void putPluginDescription_nullIntent() {
        PluginDescription pluginDescription = new PluginDescription();

        Intent result = ApiIntentHelper.putPluginDescription(null, pluginDescription);

        Assert.assertNull(result);
    }

    @Test
    public void putPluginDescription_nullPluginDescription() {
        Intent intent = new Intent();

        Intent result = ApiIntentHelper.putPluginDescription(intent, null);

        Assert.assertNotNull(result);
        Assert.assertNull(result.getParcelableExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION));
    }

    @Test
    public void getPluginDescription() {
        PluginDescription expected = new PluginDescription();
        Intent intent = new Intent();
        intent.putExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION, expected);

        PluginDescription actual = ApiIntentHelper.getPluginDescription(intent);

        Assert.assertEquals(expected, actual);
    }

    @Test
    public void getPluginDescription_nullIntent() {
        PluginDescription actual = ApiIntentHelper.getPluginDescription(null);

        Assert.assertNull(actual);
    }

    @Test
    public void getPluginDescription_nullPluginDescription() {
        PluginDescription expected = null;
        Intent intent = new Intent();
        intent.putExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION, expected);

        PluginDescription actual = ApiIntentHelper.getPluginDescription(intent);

        Assert.assertNull(actual);
    }
}
