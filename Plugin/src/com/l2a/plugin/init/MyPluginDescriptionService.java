
package com.l2a.plugin.init;

import android.app.PendingIntent;
import android.content.Intent;

import com.l2a.api.init.PluginAction;
import com.l2a.api.init.PluginDescription;
import com.l2a.api.init.PluginDescriptionService;
import com.l2a.plugin.MainActivity;

public class MyPluginDescriptionService extends PluginDescriptionService {

    @Override
    protected PluginDescription getPluginDescription() {
        PluginDescription pluginDescription = new PluginDescription();

        PluginAction main = new PluginAction("Main", PendingIntent.getActivity(
                this,
                0,
                new Intent(this, MainActivity.class),
                0));
        pluginDescription.addPluginAction(main);

        return pluginDescription;
    }

}
