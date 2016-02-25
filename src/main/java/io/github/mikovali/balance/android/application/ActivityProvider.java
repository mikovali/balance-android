package io.github.mikovali.balance.android.application;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;

public class ActivityProvider implements Application.ActivityLifecycleCallbacks {

    private Activity currentActivity;

    public ActivityProvider(Application application) {
        application.registerActivityLifecycleCallbacks(this);
    }

    @Nullable
    public Activity getCurrentActivity() {
        return currentActivity;
    }

    // ActivityLifecycleCallbacks

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        currentActivity = activity;
    }

    @Override
    public void onActivityStarted(Activity activity) {}

    @Override
    public void onActivityResumed(Activity activity) {}

    @Override
    public void onActivityPaused(Activity activity) {}

    @Override
    public void onActivityStopped(Activity activity) {}

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {}

    @Override
    public void onActivityDestroyed(Activity activity) {
        currentActivity = null;
    }
}
