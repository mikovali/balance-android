package io.github.mikovali.balance.android.application;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class BackButtonService {

    public interface OnBackButtonClickListener {
        boolean onBackButtonClick();
    }

    private final List<OnBackButtonClickListener> listeners;

    private Activity activity;

    public BackButtonService() {
        listeners = new ArrayList<>();
    }

    public void addOnBackButtonClickListener(OnBackButtonClickListener listener) {
        listeners.add(listener);
    }

    public void removeOnBackButtonClickListener(OnBackButtonClickListener listener) {
        listeners.remove(listener);
    }

    public boolean onBackButtonClick() {
        for (int i = listeners.size() - 1; i >= 0; i--) {
            if (listeners.get(i).onBackButtonClick()) {
                return true;
            }
        }
        return false;
    }

    public void onCreate(Activity activity) {
        this.activity = activity;
    }

    public void onDestroy() {
        activity = null;
    }

    public void triggerBackButtonClick() {
        if (activity != null) {
            activity.onBackPressed();
        }
    }
}
