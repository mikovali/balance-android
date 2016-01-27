package io.github.mikovali.balance.android.infrastructure.dagger;

import dagger.Component;
import io.github.mikovali.balance.android.application.transaction.TransactionListPresenter;
import io.github.mikovali.balance.android.application.transaction.TransactionUpdatePresenter;
import io.github.mikovali.balance.android.infrastructure.android.Activity;

@AppScope
@Component(modules = AppModule.class)
public interface AppComponent {

    void inject(Activity activity);

    void inject(TransactionListPresenter transactionListPresenter);
    void inject(TransactionUpdatePresenter transactionUpdatePresenter);
}
