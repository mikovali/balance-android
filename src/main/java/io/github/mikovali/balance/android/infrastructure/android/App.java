package io.github.mikovali.balance.android.infrastructure.android;

import android.app.Application;
import android.content.Context;

import io.github.mikovali.balance.android.infrastructure.dagger.AppComponent;
import io.github.mikovali.balance.android.infrastructure.dagger.AppModule;
import io.github.mikovali.balance.android.infrastructure.dagger.DaggerAppComponent;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        setupDagger();
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
