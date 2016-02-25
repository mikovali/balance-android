package io.github.mikovali.balance.android.application;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;

import rx.Observable;

/**
 * Manages window actions such as dialogs and loading.
 *
 * TODO refactor
 */
public class WindowService implements DialogInterface.OnDismissListener,
        Application.ActivityLifecycleCallbacks {

    private final ActivityProvider activityProvider;

    private Dialog currentDialog;
    private int currentMessage = 0;

    public WindowService(Application application, ActivityProvider activityProvider) {
        application.registerActivityLifecycleCallbacks(this);
        this.activityProvider = activityProvider;
    }

    public Observable<Boolean> confirm(@StringRes int message) {
        final Activity activity = activityProvider.getCurrentActivity();
        if (activity == null) {
            return null;
        }
        this.currentMessage = message;
        currentDialog = new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(android.R.string.yes, null)
                .setNegativeButton(android.R.string.no, null)
                .setOnDismissListener(this)
                .create();
        currentDialog.show();
        return null; // TODO observable
    }

    /**
     * TODO This should prevent dismissal and back button.
     */
    public void progress(@StringRes int message) {
    }

    /**
     * Hides currently showing component if any.
     *
     * TODO This should be called on every screen change.
     *
     */
    public void hide() {
        if (currentDialog != null) {
            currentDialog.dismiss();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        currentDialog = null;
        currentMessage = 0;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        // FIXME This is not called because activity is created before this service.
        if (currentMessage != 0) {
            confirm(currentMessage);
        }
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
        if (currentDialog != null) {
            currentDialog.setOnDismissListener(null);
            currentDialog.dismiss();
            currentDialog.setOnDismissListener(this);
            currentDialog = null;
        }
    }
}
