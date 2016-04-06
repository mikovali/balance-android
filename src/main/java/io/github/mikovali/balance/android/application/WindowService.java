package io.github.mikovali.balance.android.application;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;

import io.github.mikovali.balance.android.R;
import rx.Observable;
import rx.Subscriber;

import static butterknife.ButterKnife.findById;

/**
 * Manages window actions such as dialogs and loading.
 */
public class WindowService implements ActivityProvider.OnActivityLifecycleListener,
        DialogInterface.OnClickListener, DialogInterface.OnDismissListener {

    private interface OnDialogLifecycleListener {
        void onDialogDismissed(boolean isPositiveButtonClick);
    }

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

    private OnDialogLifecycleListener onDialogLifecycleListener;

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

        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                onDialogLifecycleListener = new OnDialogLifecycleListener() {
                    @Override
                    public void onDialogDismissed(boolean isPositiveButtonClick) {
                        if (!subscriber.isUnsubscribed()) {
                            subscriber.onNext(isPositiveButtonClick);
                            subscriber.onCompleted();
                            onDialogLifecycleListener = null;
                        }
                    }
                };
            }
        });
    }

    /**
     * TODO This should prevent dismissal and back button.
     */
    public void progress() {
        currentType = TYPE_PROGRESS;
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
                        .setPositiveButton(currentPositiveButtonRes, this)
                        .setNegativeButton(currentNegativeButtonRes, this)
                        .setOnDismissListener(this)
                        .create();
                currentDialog.show();
                break;
            case TYPE_ALERT:
                // TODO show Snackbar
                break;
            case TYPE_PROGRESS:
                final View progressContainer = findById(activity, R.id.progressContainer);
                if (progressContainer != null
                        && progressContainer.getVisibility() != View.VISIBLE) {
                    progressContainer.setVisibility(View.VISIBLE);
                }
                break;
            case TYPE_NAVIGATION:
                final DrawerLayout navigationDrawer = findById(activity, R.id.drawerContainer);
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
        final Activity activity = activityProvider.getCurrentActivity();
        // dismiss dialog to avoid memory leak
        if (currentDialog != null) {
            currentDialog.dismiss();
            currentDialog = null;
        }
        if (currentType == TYPE_PROGRESS && activity != null) {
            final View progressContainer = findById(activity, R.id.progressContainer);
            if (progressContainer != null && progressContainer.getVisibility() != View.GONE) {
                progressContainer.setVisibility(View.GONE);
            }
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
            case TYPE_PROGRESS:
                showCurrent();
                break;
            case TYPE_ALERT:
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

    // DialogInterface.OnClickListener

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (onDialogLifecycleListener != null) {
            onDialogLifecycleListener.onDialogDismissed(which == DialogInterface.BUTTON_POSITIVE);
        }
    }

    // DialogInterface.OnDismissListener

    @Override
    public void onDismiss(DialogInterface dialog) {
        if (onDialogLifecycleListener != null) {
            onDialogLifecycleListener.onDialogDismissed(false);
        }
        clearCurrent();
    }
}
