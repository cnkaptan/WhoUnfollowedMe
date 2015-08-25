package com.cihankaptan.android.whounfollowedme.eventbus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by cnkaptan on 25/08/15.
 */
public class EventBusAdapter {
    private static final Bus sBus = new Bus(ThreadEnforcer.ANY);

    public static void register(Object object) {
        sBus.register(object);
    }

    public static void unregister(Object object) {
        sBus.unregister(object);
    }

    public static void post(Object event) {
        sBus.post(event);
    }
}