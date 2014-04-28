
package com.l2a.api.test;

import com.l2a.api.ApiIntent;
import com.l2a.api.ApiIntentHelper;
import com.l2a.api.init.PluginDescription;

import android.content.Intent;
import android.test.AndroidTestCase;

public class ApiIntentHelperTest extends AndroidTestCase {

    public void testSomething() {
        Intent intent = new Intent();
        PluginDescription expected = new PluginDescription();

        Intent result = ApiIntentHelper.putPluginDescription(intent, expected);

        assertNotNull(result);
        assertEquals(expected, result.getParcelableExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION));
    }

}
