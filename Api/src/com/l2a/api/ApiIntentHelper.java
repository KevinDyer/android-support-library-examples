
package com.l2a.api;

import android.app.PendingIntent;
import android.content.Intent;

import com.l2a.api.common.Person;
import com.l2a.api.init.PluginDescription;

public class ApiIntentHelper {

    public static Intent putPluginDescription(Intent intent, PluginDescription pluginDescription) {
        if (null == intent) {
            return null;
        }
        return intent.putExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION, pluginDescription);
    }

    public static PluginDescription getPluginDescription(Intent intent) {
        if (null == intent) {
            return null;
        }
        return intent.getParcelableExtra(ApiIntent.EXTRA_PLUGIN_DESCRIPTION);
    }

    public static Intent putReplyTo(Intent intent, PendingIntent replyTo) {
        if (null == intent) {
            return null;
        }
        return intent.putExtra(ApiIntent.EXTRA_REPLY_TO, replyTo);
    }

    public static PendingIntent getReplyTo(Intent intent) {
        if (null == intent) {
            return null;
        }
        return intent.getParcelableExtra(ApiIntent.EXTRA_REPLY_TO);
    }

    public static Intent putPerson(Intent intent, Person person) {
        if (null == intent) {
            return null;
        }
        return intent.putExtra(ApiIntent.EXTRA_PERSON, person);
    }

    public static Person getPerson(Intent intent) {
        if (null == intent) {
            return null;
        }
        return intent.getParcelableExtra(ApiIntent.EXTRA_PERSON);
    }
}
