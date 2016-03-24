package io.github.mikovali.balance.android.infrastructure.dagger;

import dagger.Component;
import io.github.mikovali.balance.android.infrastructure.android.Activity;
import io.github.mikovali.balance.android.infrastructure.android.ui.screen.TransactionListScreenView;
import io.github.mikovali.balance.android.infrastructure.android.ui.screen.TransactionUpdateScreenView;
import io.github.mikovali.balance.android.infrastructure.android.ui.view.TransactionListAndroidView;
import io.github.mikovali.balance.android.infrastructure.android.ui.view.TransactionUpdateAndroidView;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(Activity activity);

    void inject(TransactionListScreenView transactionListScreenView);
    void inject(TransactionUpdateScreenView transactionUpdateScreenView);

    void inject(TransactionListAndroidView transactionListAndroidView);
    void inject(TransactionUpdateAndroidView transactionUpdateAndroidView);
}
