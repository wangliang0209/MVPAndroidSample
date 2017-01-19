package com.wl.demo.mvpsample.net;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by wangliang on 16-10-19.
 */

public class SubscriptionManager {
    private static final String TAG = SubscriptionManager.class.getSimpleName();

    private static SubscriptionManager instance;

    private SubscriptionManager() {
    }

    public static SubscriptionManager getInstance() {
        if (instance != null) {
            return instance;
        }
        synchronized (SubscriptionManager.class) {
            if (instance == null) {
                instance = new SubscriptionManager();
            }

            return instance;
        }
    }

    private Map<String, CompositeSubscription> reqMap = new HashMap<>();

    public void putReq(String tag, Subscription subscription) {
        CompositeSubscription reqs = reqMap.get(tag);
        if (reqs != null) {
            reqs.add(subscription);
        } else {
            reqs = new CompositeSubscription();
            reqs.add(subscription);
            reqMap.put(tag, reqs);
        }
        Log.d(TAG, "add req ");
    }

    public void removeReq(String tag, Subscription subscription) {
        CompositeSubscription reqs = reqMap.get(tag);
        if (reqs != null) {
            reqs.remove(subscription);
            Log.d(TAG, "remove req ");
            if (!reqs.hasSubscriptions()) {
                reqMap.remove(tag);
                reqs = null;
            }
        }
    }


    public void cancelPendingRequests(String tag) {
        if (tag != null) {
            CompositeSubscription reqs = reqMap.get(tag);
            if (reqs != null) {
                reqs.clear();
                reqMap.remove(tag);
                reqs = null;
            }
        }
    }

    public void clear() {
        if (reqMap != null) {
            reqMap.clear();
            reqMap = null;
        }
    }
}
