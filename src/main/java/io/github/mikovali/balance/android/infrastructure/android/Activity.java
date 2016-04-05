package io.github.mikovali.balance.android.infrastructure.android;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import io.github.mikovali.android.navigation.NavigationService;
import io.github.mikovali.balance.android.R;
import io.github.mikovali.balance.android.application.ActivityProvider;
import io.github.mikovali.balance.android.application.DeviceService;

public class Activity extends AppCompatActivity {

    @Inject ActivityProvider activityProvider;
    @Inject DeviceService deviceService;
    @Inject NavigationService navigationService;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        navigationService.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);
        App.getAppComponent(this).inject(this);
        activityProvider.onActivityCreated(this);
        navigationService.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        activityProvider.onActivityDestroyed();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if (!deviceService.onBackButtonClick() && !navigationService.goBack()) {
            super.onBackPressed();
        }
    }
}
