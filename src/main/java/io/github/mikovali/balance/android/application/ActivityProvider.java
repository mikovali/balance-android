package io.github.mikovali.balance.android.application;

import android.app.Activity;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ActivityProvider {

    public interface OnActivityLifecycleListener {
        void onActivityCreated();
        void onActivityDestroyed();
    }

    private final List<OnActivityLifecycleListener> onActivityLifecycleListeners;

    private Activity currentActivity;

    public ActivityProvider() {
        onActivityLifecycleListeners = new ArrayList<>();
    }

    @Nullable
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    public void addOnActivityLifecycleListener(
            OnActivityLifecycleListener onActivityLifecycleListener) {
        onActivityLifecycleListeners.add(onActivityLifecycleListener);
    }

    public void removeOnActivityLifecycleListener(
            OnActivityLifecycleListener onActivityLifecycleListener) {
        onActivityLifecycleListeners.remove(onActivityLifecycleListener);
    }

    public void onActivityCreated(Activity activity) {
        currentActivity = activity;
        for (final OnActivityLifecycleListener listener : onActivityLifecycleListeners) {
            listener.onActivityCreated();
        }
    }

    public void onActivityDestroyed() {
        currentActivity = null;
        for (int i = onActivityLifecycleListeners.size() - 1; i >= 0; i--) {
            onActivityLifecycleListeners.get(i).onActivityDestroyed();
        }
    }
}
