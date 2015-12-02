package io.github.mikovali.balance.android.infrastructure.android;

import android.os.StrictMode;

import com.facebook.stetho.Stetho;

import timber.log.Timber;

public class DebugApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        setupStrictMode();
        setupTimber();
        setupStetho();
    }

    // StrictMode

    private void setupStrictMode() {
        StrictMode.enableDefaults();
    }

    // Timber

    private void setupTimber() {
        // TODO Timber - debug vs release
        Timber.plant(new Timber.DebugTree());
    }

    // Stetho

    private void setupStetho() {
        Stetho.initializeWithDefaults(this);
    }
}
