package io.github.mikovali.balance.android.infrastructure.android;

import javax.inject.Singleton;

import dagger.Component;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListAndroidView;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListFragment;

@Component(modules = AppModule.class)
@Singleton
public interface AppComponent {

    void inject(TransactionListAndroidView transactionListAndroidView);

    void inject(TransactionListFragment transactionListFragment);
}
