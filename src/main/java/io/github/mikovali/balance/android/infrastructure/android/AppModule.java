package io.github.mikovali.balance.android.infrastructure.android;

import android.app.Application;

import dagger.Module;

@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }
}
