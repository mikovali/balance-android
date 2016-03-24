package io.github.mikovali.balance.android.infrastructure.dagger;

import dagger.Component;
import io.github.mikovali.balance.android.infrastructure.android.Activity;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListAndroidView;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionListScreenView;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionUpdateAndroidView;
import io.github.mikovali.balance.android.infrastructure.android.view.TransactionUpdateScreenView;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(Activity activity);

    void inject(TransactionListScreenView transactionListScreenView);
    void inject(TransactionUpdateScreenView transactionUpdateScreenView);

    void inject(TransactionListAndroidView transactionListAndroidView);
    void inject(TransactionUpdateAndroidView transactionUpdateAndroidView);
}
