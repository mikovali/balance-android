package io.github.mikovali.balance.android.application;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;

import io.github.mikovali.balance.android.R;
import rx.Observable;
import timber.log.Timber;

/**
 * Manages window actions such as dialogs and loading.
 */
public class WindowService implements ActivityProvider.OnActivityLifecycleListener,
        DialogInterface.OnDismissListener {

    private final ActivityProvider activityProvider;

    private static final int TYPE_CONFIRM = 0;
    private static final int TYPE_ALERT = 1;
    private static final int TYPE_PROGRESS = 2;
    private static final int TYPE_NAVIGATION = 3;

    private int currentType = -1;
    private int currentMessageRes = -1;
    private int currentPositiveButtonRes = -1;
    private int currentNegativeButtonRes = -1;
    private int currentActionTextRes = -1;
    private Dialog currentDialog;

    public WindowService(ActivityProvider activityProvider) {
        this.activityProvider = activityProvider;
        activityProvider.addOnActivityLifecycleListener(this);
    }

    public Observable<Boolean> confirm(@StringRes int message) {
        currentType = TYPE_CONFIRM;
        currentMessageRes = message;
        currentPositiveButtonRes = android.R.string.yes;
        currentNegativeButtonRes = android.R.string.no;
        showCurrent();

        return null; // TODO observable
    }

    /**
     * TODO This should prevent dismissal and back button.
     */
    public void progress(@StringRes int message) {
        currentType = TYPE_PROGRESS;
        currentMessageRes = message;
        showCurrent();
    }

    public void alert(@StringRes int message, @StringRes int actionText) {
        currentType = TYPE_ALERT;
        currentMessageRes = message;
        currentActionTextRes = actionText;
        showCurrent();
    }

    public void navigation() {
        currentType = TYPE_NAVIGATION;
        showCurrent();
    }

    /**
     * Hides currently showing component if any.
     *
     * TODO This should be called on every screen change.
     *
     */
    public void hide() {
        hideCurrent();
        clearCurrent();
    }

    private void showCurrent() {
        final Activity activity = activityProvider.getCurrentActivity();
        if (activity == null) {
            return;
        }

        switch (currentType) {
            case TYPE_CONFIRM:
                currentDialog = new AlertDialog.Builder(activity)
                        .setMessage(currentMessageRes)
                        .setPositiveButton(currentPositiveButtonRes, null)
                        .setNegativeButton(currentNegativeButtonRes, null)
                        .setOnDismissListener(this)
                        .create();
                currentDialog.show();
                break;
            case TYPE_ALERT:
                // TODO show Snackbar
                break;
            case TYPE_PROGRESS:
                // TODO show Progress View
                break;
            case TYPE_NAVIGATION:
                final DrawerLayout navigationDrawer = (DrawerLayout) activity
                        .findViewById(R.id.drawerContainer);
                if (navigationDrawer != null
                        && !navigationDrawer.isDrawerOpen(GravityCompat.START)) {
                    navigationDrawer.openDrawer(GravityCompat.START);
                }
                break;
            default:
                // ignore
                break;
        }
    }

    private void hideCurrent() {
        Timber.e("hideCurrent: %s", currentType);
        switch (currentType) {
            case TYPE_CONFIRM:
                Timber.e("currentDialog: %s", currentDialog);
                if (currentDialog != null) {
                    currentDialog.dismiss();
                    currentDialog = null;
                }
                break;
            case TYPE_ALERT:
            case TYPE_PROGRESS:
            case TYPE_NAVIGATION:
                // do nothing
                break;
        }
    }

    private void clearCurrent() {
        currentType = -1;
        currentMessageRes = -1;
        currentPositiveButtonRes = -1;
        currentNegativeButtonRes = -1;
        currentActionTextRes = -1;
    }

    // ActivityProvider.OnActivityLifecycleListener

    @Override
    public void onActivityCreated() {
        switch (currentType) {
            case TYPE_CONFIRM:
                showCurrent();
                break;
            case TYPE_ALERT:
            case TYPE_PROGRESS:
            case TYPE_NAVIGATION: // re-opens itself
            default:
                break;
        }
    }

    @Override
    public void onActivityDestroyed() {
        if (currentDialog != null) {
            // remove current OnDismissListener because it will clear current item
            currentDialog.setOnDismissListener(null);
        }
        hideCurrent();
    }

    // DialogInterface.OnDismissListener

    @Override
    public void onDismiss(DialogInterface dialog) {
        clearCurrent();
    }
}
