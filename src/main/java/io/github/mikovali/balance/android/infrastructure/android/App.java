package io.github.mikovali.balance.android.infrastructure.android;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;

import io.github.mikovali.balance.android.BuildConfig;
import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupStrictMode();
        setupTimber();
        setupDagger();
    }

    // StrictMode

    private void setupStrictMode() {
        if (BuildConfig.DEBUG) {
            StrictMode.enableDefaults();
        }
    }

    // Timber

    private void setupTimber() {
        // TODO Timber - debug vs release
        Timber.plant(new Timber.DebugTree());
    }

    // Dagger

    private AppComponent appComponent;

    private void setupDagger() {
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    public static AppComponent getAppComponent(Context context) {
        return ((App) context.getApplicationContext()).appComponent;
    }
}
