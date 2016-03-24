package io.github.mikovali.balance.android.application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class DeviceService {

    public interface OnBackButtonClickListener {
        boolean onBackButtonClick();
    }

    private final ActivityProvider activityProvider;

    private final List<OnBackButtonClickListener> onBackButtonClickListeners;

    public DeviceService(ActivityProvider activityProvider) {
        this.activityProvider = activityProvider;
        onBackButtonClickListeners = new ArrayList<>();
    }

    public void addOnBackButtonClickListener(OnBackButtonClickListener listener) {
        onBackButtonClickListeners.add(listener);
    }

    public void removeOnBackButtonClickListener(OnBackButtonClickListener listener) {
        onBackButtonClickListeners.remove(listener);
    }

    /**
     * Used by Activities in {@link Activity#onBackPressed()}.
     *
     * @return True if back button click is handled by listeners, false for the default action.
     */
    public boolean onBackButtonClick() {
        for (int i = onBackButtonClickListeners.size() - 1; i >= 0; i--) {
            if (onBackButtonClickListeners.get(i).onBackButtonClick()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Triggers a back button click.
     *
     * Used by Up navigation in some cases, etc.
     */
    public void triggerBackButtonClick() {
        final Activity activity = activityProvider.getCurrentActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }
}
