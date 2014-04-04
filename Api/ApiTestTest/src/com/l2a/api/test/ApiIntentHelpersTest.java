
package com.l2a.api.test;

import static org.mockito.Mockito.mock;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.MockitoAnnotations;

import android.content.Intent;

import com.l2a.api.ApiIntentHelpers;
import com.l2a.api.init.PluginDescription;

public class ApiIntentHelpersTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPutPluginDescription() {
        PluginDescription pluginDescription = mock(PluginDescription.class);
        Intent intent = new Intent();

        Intent result = ApiIntentHelpers.putPluginDescription(intent, pluginDescription);

        Assert.assertNotNull(result);
        Assert.assertTrue(result.hasExtra(ApiIntentHelpers.EXTRA_PLUGIN_DESCRIPTION));
    }

}
