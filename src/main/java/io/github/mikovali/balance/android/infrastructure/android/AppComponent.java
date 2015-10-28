package io.github.mikovali.balance.android.infrastructure.android;

import javax.inject.Singleton;

import dagger.Component;
import io.github.mikovali.balance.android.infrastructure.android.view.AndroidTransactionListView;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(AndroidTransactionListView androidTransactionListView);
}
