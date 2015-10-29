package io.github.mikovali.balance.android.infrastructure.android;

import javax.inject.Singleton;

import dagger.Component;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListAndroidView;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(TransactionListAndroidView transactionListAndroidView);
}
