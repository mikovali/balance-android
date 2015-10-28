package io.github.mikovali.balance.android.infrastructure.android;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupTimber();
        setupDagger();
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
